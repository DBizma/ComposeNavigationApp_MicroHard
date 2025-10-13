package com.example.composenavigationapp_microhard.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.composenavigationapp_microhard.ui.screens.*
import kotlinx.coroutines.launch

// Bottom Navigation Items
sealed class BottomItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    data object Home : BottomItem(Routes.HOME, "Home", Icons.Filled.Home)
    data object Profile : BottomItem(Routes.PROFILE, "Profile", Icons.Filled.Person)
    data object Settings : BottomItem(Routes.SETTINGS, "Settings", Icons.Filled.Settings)
}

private val bottomItems = listOf(BottomItem.Home, BottomItem.Profile, BottomItem.Settings)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch { drawerState.close() }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentRoute(navController) ?: "Compose App") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Home, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = { BottomNavBar(navController) },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(Routes.ADD) }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {
                MainNavHost(navController)
            }
        }
    }
}

@Composable
private fun BottomNavBar(navController: NavHostController) {
    val backStack by navController.currentBackStackEntryAsState()
    val dest = backStack?.destination

    NavigationBar {
        bottomItems.forEach { item ->
            val selected = isTopLevelDestination(dest, item.route)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

private fun isTopLevelDestination(dest: NavDestination?, route: String): Boolean =
    dest?.hierarchy?.any { it.route == route } == true

private fun currentRoute(navController: NavHostController): String? =
    navController.currentBackStackEntry?.destination?.route

@Composable
private fun AppDrawer(onNavigate: (String) -> Unit) {
    ModalDrawerSheet {
        Text("Menu", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = false,
            onClick = { onNavigate(Routes.HOME) }
        )
        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = { onNavigate(Routes.PROFILE) }
        )
        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = false,
            onClick = { onNavigate(Routes.SETTINGS) }
        )

        NavigationDrawerItem(label = { Text("About") },    selected = false, onClick = { onNavigate(Routes.ABOUT) })
    }
}


@Composable
private fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable("${Routes.DETAIL}/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")
            DetailScreen(navController, id)
        }
        composable(Routes.PROFILE) { ProfileScreen() }
        composable(Routes.SETTINGS) { SettingsScreen() }
        composable(Routes.ADD) { AddScreen(navController) }
        composable(Routes.ABOUT) { AboutScreen() }
    }
}




