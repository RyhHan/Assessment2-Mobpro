package com.andimuhammadraihansyamsu607062330113.assessment2.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredient",
    foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: String,
    val recipeId: Int
)
