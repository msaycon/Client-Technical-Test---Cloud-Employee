package com.cloudemployee.baseproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cloudemployee.baseproject.presentation.screen.userdetails.UserDetailsScreen
import com.cloudemployee.baseproject.presentation.screen.users.UsersScreen

@Composable
fun BaseNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Users.route,
    ) {
        composable(Routes.Users.route) {
            UsersScreen(
                onUserClick = { accountId ->
                    navController.navigate(Routes.UserDetails.createRoute(accountId))
                },
            )
        }
        composable(
            route = Routes.UserDetails.route,
            arguments = listOf(
                navArgument(Routes.UserDetails.accountIdArg) {
                    type = NavType.LongType
                },
            ),
        ) {
            UserDetailsScreen(
                onBackClick = navController::popBackStack,
            )
        }
    }
}

sealed class Routes(val route: String) {
    data object Users : Routes("users")
    data object UserDetails : Routes("user_details/{accountId}") {
        const val accountIdArg = "accountId"

        fun createRoute(accountId: Long): String = "user_details/$accountId"
    }
}
