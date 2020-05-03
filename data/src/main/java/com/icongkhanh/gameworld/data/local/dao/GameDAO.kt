package com.icongkhanh.gameworld.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.icongkhanh.gameworld.data.local.entity.Game


@Dao
interface GameDAO {

    @Query("SELECT * FROM _game")
    suspend fun getAllBookmarkGame(): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkGame(vararg game: Game)

    @Query("SELECT * FROM _game WHERE _game._id = :id")
    suspend fun getGameBookmark(id: Long): Game?

    @Query("DELETE FROM _game WHERE _id = :id")
    suspend fun removeBookmarkGame(id: Long)

}