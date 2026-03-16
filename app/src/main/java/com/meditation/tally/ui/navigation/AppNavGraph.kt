package com.meditation.tally.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meditation.tally.di.AppContainer
import com.meditation.tally.ui.history.HistoryScreen
import com.meditation.tally.ui.history.HistoryViewModel
import com.meditation.tally.ui.history.HistoryViewModelFactory
import com.meditation.tally.ui.home.HomeScreenRoute
import com.meditation.tally.ui.home.HomeViewModel
import com.meditation.tally.ui.home.HomeViewModelFactory
import com.meditation.tally.ui.settings.SettingsScreen
import com.meditation.tally.ui.settings.SettingsViewModel
import com.meditation.tally.ui.settings.SettingsViewModelFactory

@Composable
fun AppNavGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Home.route
    ) {
        composable(AppRoute.Home.route) {
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(
                    repository = appContainer.dailyTallyRepository,
                    preferencesRepository = appContainer.preferencesRepository,
                    undoSessionManager = appContainer.undoSessionManager,
                    dateProvider = appContainer.dateProvider
                )
            )
            HomeScreenRoute(
                viewModel = viewModel,
                onHistoryClick = { navController.navigate(AppRoute.History.route) },
                onSettingsClick = { navController.navigate(AppRoute.Settings.route) }
            )
        }

        composable(AppRoute.History.route) {
            val viewModel: HistoryViewModel = viewModel(
                factory = HistoryViewModelFactory(
                    repository = appContainer.dailyTallyRepository,
                    preferencesRepository = appContainer.preferencesRepository,
                    undoSessionManager = appContainer.undoSessionManager
                )
            )
            HistoryScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoute.Settings.route) {
            val viewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(
                    preferencesRepository = appContainer.preferencesRepository,
                    dailyTallyRepository = appContainer.dailyTallyRepository,
                    undoSessionManager = appContainer.undoSessionManager
                )
            )
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
