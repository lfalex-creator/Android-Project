package com.example.androidapp.ui.data.DAO
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidapp.ui.data.entities.GamesDataEntity
import com.example.androidapp.ui.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Long): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getByEmail(email: String): List<UserEntity>

    @Query("SELECT games.name,games.id as gameId,users_games.score FROM users_games JOIN games on users_games.gameId=games.id WHERE users_games.userId = :id")
    fun getGamesOfUser(id: Long): Flow<List<GamesDataEntity>>
}