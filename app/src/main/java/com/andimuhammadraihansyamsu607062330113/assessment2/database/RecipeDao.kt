package com.andimuhammadraihansyamsu607062330113.assessment2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Delete
    suspend fun deletePermanent(recipe: Recipe)

    @Query("SELECT * FROM recipes where isDeleted = 0 ORDER BY kategori ASC")
    fun getRecipe(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isDeleted = 1")
    fun getSampahRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Long): Recipe?

    @Query("UPDATE recipes SET isDeleted = 1 WHERE id = :id")
    suspend fun sampahRecipe(id: Long)

    @Query("UPDATE recipes SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreRecipe(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteById(id: Long)
}