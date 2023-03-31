/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet.ui.wallet

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.tb.frontierwallet.ui.Screen
import com.tb.frontierwallet.ui.settings.AboutScreen
import com.tb.frontierwallet.ui.settings.RecoveryPhraseScreen
import com.tb.frontierwallet.ui.settings.SettingsRootScreen
import com.tb.frontierwallet.ui.frontier.*
import com.tb.frontierwallet.ui.frontier.FrontierRootScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WalletNavigation(
    navControllerWalletNavigation: NavHostController,
    walletViewModel: WalletViewModel,
) {
    val animationDuration = 400

    AnimatedNavHost(
        navController = navControllerWalletNavigation,
        startDestination = Screen.WalletRootScreen.route,
    ) {

        // Wallet
        composable(
            route = Screen.WalletRootScreen.route,
            enterTransition = {
                val route = initialState.destination.route
                if (route == null) {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                } else if (route.contains("receive") || route.contains("send") || route.contains("transaction")) {
                    fadeIn(animationSpec = tween(1000))
                } else {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                }
                // when (initialState.destination.route) {
                //     "receive_screen" -> fadeIn(animationSpec = tween(1000))
                //     "send_screen" -> fadeIn(animationSpec = tween(1000))
                //     "transaction_screen" -> fadeIn(animationSpec = tween(1000))
                //     "wallet_screen" -> null
                //     else -> slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                // }
            },
            popEnterTransition = {
                val route = initialState.destination.route
                if (route == null) {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                } else if (route.contains("receive") || route.contains("send") || route.contains("transaction")) {
                    fadeIn(animationSpec = tween(1000))
                } else {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                }
            },
            exitTransition = {
                val route = targetState.destination.route
                if (route == null) {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                } else if (route.contains("receive") || route.contains("send") || route.contains("transaction")) {
                    fadeOut(animationSpec = tween(300))
                } else {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                }
            },
            popExitTransition = {
                val route = targetState.destination.route
                if (route == null) {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                } else if (route.contains("receive") || route.contains("send") || route.contains("transaction")) {
                    fadeOut(animationSpec = tween(300))
                } else {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                }
            }
        ) { WalletRootScreen(navController = navControllerWalletNavigation, walletViewModel = walletViewModel) }


        // Receive
        composable(
            route = Screen.ReceiveScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            }
        ) { ReceiveScreen(navController = navControllerWalletNavigation, walletViewModel) }


        // Send
        composable(
            route = Screen.SendScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            }
        ) { SendScreen(navController = navControllerWalletNavigation, walletViewModel = walletViewModel) }


        // Transaction screen
        composable(
            route = "${Screen.TransactionScreen.route}/txid={txid}",
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("txid")?.let {
                TransactionScreen(navControllerWalletNavigation, backStackEntry.arguments?.getString("txid"))
            }
        }

        // QR Scanner Screen
        composable(
            route = Screen.QRScanScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            }
        ) { QRScanScreen(navController = navControllerWalletNavigation) }


        // Chapters home
        composable(
            route = Screen.ChaptersRootScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "wallet_screen" -> slideIntoContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                    "settings_root_screen" -> slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                    else -> fadeIn(animationSpec = tween(1000))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "wallet_screen" -> slideIntoContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                    "settings_root_screen" -> slideIntoContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                    else -> fadeIn(animationSpec = tween(1000))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "wallet_screen" -> slideOutOfContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                    "settings_root_screen" -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                    else -> fadeOut(animationSpec = tween(300))
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "wallet_screen" -> slideOutOfContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                    "settings_root_screen" -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                    else -> fadeOut(animationSpec = tween(300))
                }
            }
        ) { FrontierRootScreen(navController = navControllerWalletNavigation) }


        // Specific chapters
        // composable(
        //     route = Screen.ChapterScreen.route + "/{chapterId}",
        //     arguments = listOf(navArgument("chapterId") { type = NavType.IntType }),
        //     enterTransition = {
        //         slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
        //     },
        //     popEnterTransition = {
        //         slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
        //     },
        //     exitTransition = {
        //         slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
        //     },
        //     popExitTransition = {
        //         slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
        //     }
        // ) { backStackEntry ->
        //     backStackEntry.arguments?.getInt("chapterId")?.let {
        //         ChapterScreen(chapterId = it, chaptersViewModel = chaptersViewModel, navController = navControllerWalletNavigation)
        //     }
        // }


        // Settings
        val settingsScreens: List<String> = listOf("about_screen", "recovery_phrase_screen", "send_coins_back_screen")
        composable(
            route = Screen.SettingsRootScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    in settingsScreens -> fadeIn(animationSpec = tween(400))
                    else               -> slideIntoContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    in settingsScreens -> fadeIn(animationSpec = tween(400))
                    else -> slideIntoContainer(AnimatedContentScope.SlideDirection.Start, animationSpec = tween(animationDuration))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    in settingsScreens -> fadeOut(animationSpec = tween(400))
                    else               -> slideOutOfContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    in settingsScreens -> fadeOut(animationSpec = tween(400))
                    else               -> slideOutOfContainer(AnimatedContentScope.SlideDirection.End, animationSpec = tween(animationDuration))
                }
            },
        ) { SettingsRootScreen(navController = navControllerWalletNavigation) }


        // About
        composable(
            route = Screen.AboutScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            }
        ) { AboutScreen(navController = navControllerWalletNavigation) }


        // Recovery phrase
        composable(
            route = Screen.RecoveryPhraseScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(animationDuration))
            }
        ) { RecoveryPhraseScreen(navController = navControllerWalletNavigation) }
    }
}
