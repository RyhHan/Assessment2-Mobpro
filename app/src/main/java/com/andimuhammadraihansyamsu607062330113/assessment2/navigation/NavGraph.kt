package com.andimuhammadraihansyamsu607062330113.assessment2.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.AddEditScreen
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.DetailScreen
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.KEY_ID_RECIPE
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.MainScreen
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen.RecycleScreen


@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            AddEditScreen(navController)
        }
        composable(route = Screen.RecycleScreen.route) {
            RecycleScreen(navController)
        }
        composable (
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument (KEY_ID_RECIPE) { type = NavType.LongType }
            )
        ){ navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_RECIPE)
            AddEditScreen(navController, id)
        }
        composable (
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument (KEY_ID_RECIPE) { type = NavType.LongType }
            )
        ){ navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_RECIPE)
            DetailScreen(navController, id)
        }
    }
}