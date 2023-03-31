package com.tb.frontierwallet.utils

import com.tb.frontierwallet.R
import com.tb.frontierwallet.ui.Screen

sealed class NavigationItem(val route: String, val icon_filled: Int, val icon_outline: Int, val title: String) {
    object Home : NavigationItem(route = Screen.WalletRootScreen.route, icon_filled = R.drawable.ic_hicon_wallet, icon_outline = R.drawable.ic_hicon_wallet, title = "wallet")
    object Chapters : NavigationItem(route = Screen.ChaptersRootScreen.route, icon_filled = R.drawable.ic_hicon_add_square, icon_outline = R.drawable.ic_hicon_add_square, title = "frontier")
    object Settings : NavigationItem(route = Screen.SettingsRootScreen.route, icon_filled = R.drawable.ic_hicon_menu, icon_outline = R.drawable.ic_hicon_menu, title = "settings")
}
