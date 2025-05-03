package com.andimuhammadraihansyamsu607062330113.assessment2.database

import androidx.room.Insert
import androidx.room.Query
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Category
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Ingredient
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe

interface RecipeDao {
    @Insert suspend fun insertRecipe(recipe: Recipe): Long
    @Insert suspend fun insertIngredient(ingredient: Ingredient)
    @Insert suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM recipe") suspend fun getAllRecipes(): List<Recipe>
    @Query("SELECT * FROM ingredient WHERE recipeId = :id") suspend fun getIngredientsByRecipeId(id: Int): List<Ingredient>
}