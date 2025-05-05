package com.andimuhammadraihansyamsu607062330113.assessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe

@Database(entities = [Recipe::class],version = 2,exportSchema = false)
abstract class RecipeDb : RoomDatabase() {

    abstract val dao: RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDb? = null
        @Suppress("DEPRECATION")
        fun getInstance(context: Context): RecipeDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDb::class.java,
                        "recipes.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }

    }
}