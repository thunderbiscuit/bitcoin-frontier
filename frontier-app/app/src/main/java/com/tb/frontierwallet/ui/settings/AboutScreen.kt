/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tb.frontierwallet.R
import com.tb.frontierwallet.theme.PadawanTypography
import com.tb.frontierwallet.theme.padawan_theme_background_secondary
import com.tb.frontierwallet.theme.padawan_theme_button_primary
import com.tb.frontierwallet.theme.padawan_theme_text_faded_secondary
import com.tb.frontierwallet.ui.PadawanAppBar

@Composable
internal fun AboutScreen(
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(state = scrollState)
            .background(padawan_theme_background_secondary)
            .padding(bottom = 12.dp)
    ) {
        PadawanAppBar(navController = navController, title = "About Frontier")
        Text(
            text = stringResource(R.string.about_text),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            style = PadawanTypography.bodyMedium,
            color = padawan_theme_text_faded_secondary
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.privacyText),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            style = PadawanTypography.bodyMedium,
            color = padawan_theme_text_faded_secondary
        )
        Spacer(Modifier.height(24.dp))
        val mUriHandler = LocalUriHandler.current
        val gitHubLink = stringResource(id = R.string.privacyLink)
        Text(
            text = gitHubLink,
            modifier = Modifier
                .clickable { mUriHandler.openUri(gitHubLink) }
                .padding(start = 24.dp, end = 24.dp),
            style = PadawanTypography.bodyMedium,
            color = padawan_theme_button_primary,
            textDecoration = TextDecoration.Underline
        )
    }
}
