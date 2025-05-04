package com.andimuhammadraihansyamsu607062330113.assessment2.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Category
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Ingredient
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe

data class RecipeWithIngredientsAndCategory(
    @Embedded val recipe: Recipe,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<Ingredient>,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category
)