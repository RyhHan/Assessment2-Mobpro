package com.andimuhammadraihansyamsu607062330113.assessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val nim: String,
    val kelas: String,
    val isDeleted: Boolean = false
)
