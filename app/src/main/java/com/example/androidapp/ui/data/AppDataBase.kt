package com.example.androidapp.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.ui.data.DAO.UsersDAO
import com.example.androidapp.ui.data.DAO.UsersGamesDAO
import com.example.androidapp.ui.data.entities.GameEntity
import com.example.androidapp.ui.data.entities.UserGameEntity

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        UserGameEntity::class],
    version = 5)
abstract class AppDataBase : RoomDatabase()
{
    abstract fun userDao(): UsersDAO
    abstract fun usersGamesDao(): UsersGamesDAO
    companion object{
        @Volatile
        private var instance: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase{

            return instance ?: synchronized(this){
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration(false).build()
                instance = newInstance
                newInstance
            }
        }
    }
}