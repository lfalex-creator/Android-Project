package com.example.androidapp.ui.data.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidapp.ui.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDAO {
    //in Java: singura posib de "a instantia" o interfata este printr-o instanta anonima
    @Insert(onConflict = OnConflictStrategy.REPLACE) //implica si un update fortat
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")//:id = parametru din functie
    fun getById(id: Long): UserEntity?
}