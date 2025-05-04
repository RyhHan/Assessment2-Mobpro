package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDao
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: RecipeDao) : ViewModel() {

    val data: StateFlow<List<Recipe>> = dao.getRecipe().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val dataSampah: StateFlow<List<Recipe>> = dao.getSampahRecipes().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun deletePermanent(recipe: Recipe) {
        viewModelScope.launch {
            dao.deletePermanent(recipe)
        }
    }

    fun restore(id: Long) {
        viewModelScope.launch {
            dao.restoreRecipe(id)
        }
    }
}
