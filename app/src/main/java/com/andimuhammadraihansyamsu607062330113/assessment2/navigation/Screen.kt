package com.andimuhammadraihansyamsu607062330113.assessment2.navigation

import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.KEY_ID_RECIPE

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object FormBaru: Screen("addEditScreen")
    data object RecycleScreen: Screen("recycleScreen")
    data object FormUbah: Screen("addeditrecipe/{$KEY_ID_RECIPE}") {
        fun withId(id: Long) = "addeditrecipe/$id"
    }
    data object Detail : Screen("detail/{$KEY_ID_RECIPE}") {
        fun withId(id: Long) = "detail/$id"
    }
}