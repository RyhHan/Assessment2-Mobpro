package com.andimuhammadraihansyamsu607062330113.assessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val deskripsi: String,
    val kategori: String,
    val bahan : String,
    val isDeleted: Boolean = false
)
