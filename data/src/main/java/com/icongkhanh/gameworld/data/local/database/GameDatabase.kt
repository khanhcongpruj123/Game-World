package com.icongkhanh.gameworld.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.icongkhanh.gameworld.data.local.dao.GameDAO
import com.icongkhanh.gameworld.data.local.entity.Game

@Database(entities = [Game::class], version = 1, exportSchema = true)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDAO

    companion object {

        private var instance: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            if (instance == null) {
                synchronized(GameDatabase) {
                    if (instance == null) {
                        instance = buildDatabase(context)
                        return instance!!
                    }
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context): GameDatabase {
            return Room.databaseBuilder(
                context,
                GameDatabase::class.java,
                "game.db"
            ).build()
        }
    }
}