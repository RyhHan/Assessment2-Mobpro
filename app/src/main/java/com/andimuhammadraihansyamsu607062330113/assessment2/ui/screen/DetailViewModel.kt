package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDao
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: RecipeDao) : ViewModel() {

    fun insert(nama:String,nim: String,kelas: String) {
        val recipe = Recipe(
            nama = nama,
            nim   = nim,
            kelas = kelas
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(recipe)
        }
    }

    suspend fun getMahasiswa(id: Long): Recipe? {
        return dao.getMahasiswaById(id)
    }

    fun update(id: Long,nama:String,nim: String,kelas: String) {
        val recipe = Recipe(
            id      = id,
            nama = nama,
            nim   = nim,
            kelas = kelas
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(recipe)
        }
    }

    fun RecycleBin(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun sampah(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.sampahMahasiswa(id)
        }
    }
}