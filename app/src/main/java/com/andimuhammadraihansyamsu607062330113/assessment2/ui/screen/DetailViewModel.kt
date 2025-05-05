package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDao
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: RecipeDao) : ViewModel() {

    fun insert(nama:String,nim: String,kelas: String,langkah : String ,bahan : String) {
        val recipe = Recipe(
            nama = nama,
            deskripsi   = nim,
            kategori = kelas,
            langkah = langkah,
            bahan = bahan,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(recipe)
        }
    }

    suspend fun getRecipeById(id: Long): Recipe? {
        return dao.getRecipeById(id)
    }

    fun update(id: Long,nama:String,nim: String,kelas: String,langkah: String, bahan: String) {
        val recipe = Recipe(
            id      = id,
            nama = nama,
            deskripsi   = nim,
            kategori = kelas,
            langkah = langkah,
            bahan = bahan,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(recipe)
        }
    }

    fun sampah(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.sampahRecipe(id)
        }
    }
}