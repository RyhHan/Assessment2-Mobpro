package com.andimuhammadraihansyamsu607062330113.assessment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>
}