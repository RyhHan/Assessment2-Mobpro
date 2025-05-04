package com.andimuhammadraihansyamsu607062330113.assessment2.navigation

import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.KEY_ID_MAHASISWA

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object FormBaru: Screen("detailScreen")
    data object RecycleScreen: Screen("recycleScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_MAHASISWA}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}