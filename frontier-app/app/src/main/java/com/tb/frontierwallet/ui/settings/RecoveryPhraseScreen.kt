/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tb.frontierwallet.data.WalletRepository
import com.tb.frontierwallet.theme.padawan_theme_background_secondary
import com.tb.frontierwallet.ui.PadawanAppBar
import com.tb.frontierwallet.ui.standardBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecoveryPhraseScreen(
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    val seedPhrase: String = WalletRepository.getMnemonic()
    val wordList: List<String> = seedPhrase.split(" ")

    Column (
        modifier = Modifier
            .background(padawan_theme_background_secondary)
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ){
        PadawanAppBar(navController = navController, title = "Your wallet recovery phrase")
        wordList.forEachIndexed { index, item ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
            ) {
                Text(text = "${index + 1}: ")
                Card(
                    border = standardBorder,
                ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}
