package com.kalavidara.balaga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kalavidara.balaga.ui.screens.*

// Route constants — safer than plain strings
object Routes {
    const val SPLASH  = "splash"
    const val HOME    = "home"
    const val SEARCH  = "search"
    const val PROFILE = "profile/{troupeId}"
    const val BOOKING = "booking/{troupeId}"
    const val IMPACT  = "impact"

    fun profile(id: Int) = "profile/$id"
    fun booking(id: Int) = "booking/$id"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onNavigateHome = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }

        composable(Routes.SEARCH) {
            SearchScreen(navController = navController)
        }

        composable(
            route = Routes.PROFILE,
            arguments = listOf(navArgument("troupeId") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("troupeId") ?: 0
            ProfileScreen(troupeId = id, navController = navController)
        }

        composable(
            route = Routes.BOOKING,
            arguments = listOf(navArgument("troupeId") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("troupeId") ?: 0
            BookingScreen(troupeId = id, navController = navController)
        }

        composable(Routes.IMPACT) {
            ImpactScreen(navController = navController)
        }
    }
}