package com.andimuhammadraihansyamsu607062330113.assessment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Ingredient

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients: List<Ingredient>)

    @Query("DELETE FROM ingredient WHERE recipeId = :recipeId")
    suspend fun deleteByRecipe(recipeId: Long)
}