package com.andimuhammadraihansyamsu607062330113.assessment2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Category
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Ingredient
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import com.andimuhammadraihansyamsu607062330113.assessment2.model.relation.RecipeWithIngredientsAndCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE isDeleted = 0")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeWithDetail(id: Long): RecipeWithIngredientsAndCategory?
}