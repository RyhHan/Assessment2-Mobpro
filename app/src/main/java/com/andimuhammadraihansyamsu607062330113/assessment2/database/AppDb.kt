package com.andimuhammadraihansyamsu607062330113.assessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Category
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Ingredient
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe

@Database(
    entities = [Recipe::class, Ingredient::class, Category::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_recipe.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}