/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet.ui.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.tb.frontierwallet.R
import com.tb.frontierwallet.WalletCreateType
import com.tb.frontierwallet.theme.*
import com.tb.frontierwallet.ui.Screen

@Composable
internal fun WalletChoiceScreen(
    navController: NavController,
    onBuildWalletButtonClicked: (WalletCreateType) -> Unit
) {
    ConstraintLayout {
        val (create, alreadyHave) = createRefs()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.padawan),
                    color = padawan_theme_text_headline,
                    fontSize = 70.sp,
                    fontFamily = ShareTechMono,
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    stringResource(R.string.elevator_pitch),
                    color = md_theme_dark_onBackground,
                    style = PadawanTypography.headlineMedium,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        Button(
            onClick = { onBuildWalletButtonClicked(WalletCreateType.FROMSCRATCH) },
            colors = ButtonDefaults.buttonColors(md_theme_dark_surfaceLight),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .size(width = 300.dp, height = 170.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .constrainAs(create) {
                    bottom.linkTo(alreadyHave.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                stringResource(R.string.create_new_wallet),
                style = PadawanTypography.labelLarge,
                fontSize = 21.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { navController.navigate(Screen.WalletRecoveryScreen.route) },
            colors = ButtonDefaults.buttonColors(md_theme_dark_surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .size(width = 300.dp, height = 170.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .constrainAs(alreadyHave) {
                    bottom.linkTo(parent.bottom, margin = 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                stringResource(R.string.already_have_a_wallet),
                style = PadawanTypography.labelLarge,
                fontSize = 21.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}


// @Preview(device = Devices.PIXEL_4, showBackground = true)
// @Composable
// internal fun PreviewWalletChoiceScreen() {
//     PadawanTheme {
//         WalletChoiceScreen(rememberNavController(), { } )
//     }
// }
