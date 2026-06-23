package com.example.androidapp.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidapp.ui.data.entities.UserEntity
import com.example.androidapp.ui.data.DAO.UsersDAO
import com.example.androidapp.ui.data.entities.GameEntity
import com.example.androidapp.ui.data.entities.UserGameEntity

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        UserGameEntity::class],
    version = 1)
abstract class AppDataBase : RoomDatabase()
{
    abstract fun userDao(): UsersDAO
    //in Kotlin NU exista static, dar exista companion object
    companion object{
        @Volatile
        private var instance: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase{
            return instance ?: synchronized(this){ //synchronized = exitare dubla instantiere
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,//contextul
                    AppDataBase::class.java,//clasa
                    "app_database"//numele fisierului din memoria device-ului
                ).fallbackToDestructiveMigration(true).build()
                instance = newInstance
                newInstance//returneaza newInstance
            }
        }
    }
}