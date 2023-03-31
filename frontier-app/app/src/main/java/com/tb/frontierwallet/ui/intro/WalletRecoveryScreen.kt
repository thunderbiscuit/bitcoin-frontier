/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet.ui.intro

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tb.frontierwallet.R
import com.tb.frontierwallet.WalletCreateType
import com.tb.frontierwallet.theme.*
import com.tb.frontierwallet.ui.standardBorder
import com.tb.frontierwallet.utils.*
import com.tb.frontierwallet.utils.WordCheckResult
import com.tb.frontierwallet.utils.checkWords
import kotlinx.coroutines.launch

private const val TAG = "WalletRecoveryScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletRecoveryScreen(onBuildWalletButtonClicked: (WalletCreateType) -> Unit) {

    val snackbarHostState = remember { SnackbarHostState() }
    // val scope = rememberCoroutineScope()

    Scaffold(
        // snackbarHost = { SnackbarHost(snackbarHostState) }
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(md_theme_dark_warning)
                        .semantics { testTag = "Intro WalletRecoveryScreen Snackbar" },
                    containerColor = md_theme_dark_warning,

                ) {
                    Text(
                        text = data.visuals.message,
                        style = TextStyle(md_theme_dark_onLightBackground)
                    )
                }
            }
        },
    ) { innerPadding ->
        // the screen is broken into 3 parts
        // the app name, the body, and the button
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight(1f)
                .background(Color(0xffffffff))
        ) {

            val (appName, body) = createRefs()

            val emptyRecoveryPhrase: Map<Int, String> = mapOf(
                1 to "", 2 to "", 3 to "", 4 to "", 5 to "", 6 to "",
                7 to "", 8 to "", 9 to "", 10 to "", 11 to "", 12 to ""
            )
            val (recoveryPhraseWordMap, setRecoveryPhraseWordMap) = remember { mutableStateOf(emptyRecoveryPhrase) }


            // the app name
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(innerPadding)
                    .padding(top = 48.dp)
                    .background(Color(0xffffffff))
                    .constrainAs(appName) {
                        top.linkTo(parent.top)
                    }
            ) {
                Text(
                    text = "Recover a wallet",
                    style = PadawanTypography.headlineSmall,
                    color = Color(0xff1f0208),
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 8.dp)
                )
                Text(
                    text = "Enter your 12-word recovery phrase below.",
                    style = PadawanTypography.bodyMedium,
                    color = Color(0xff787878),
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                )
            }


            // the body
            MyList(
                recoveryPhraseWordMap,
                setRecoveryPhraseWordMap,
                onBuildWalletButtonClicked,
                snackbarHostState,
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(appName.bottom)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints

                    },
            )
        }
    }
}

@Composable
fun MyList(
    recoveryPhraseWordMap: Map<Int, String>,
    setRecoveryPhraseWordMap: (Map<Int, String>) -> Unit,
    onBuildWalletButtonClicked: (WalletCreateType) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxWidth(1f)
            .background(Color(0xffffffff))
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val scope = rememberCoroutineScope()
        val focusManager = LocalFocusManager.current
        for (i in 1..12) {
            WordField(wordNumber = i, recoveryPhraseWordMap, setRecoveryPhraseWordMap, focusManager)
        }
        Button(
            onClick = {
                when (val wordCheck = checkWords(recoveryPhraseWordMap)) {
                    is WordCheckResult.SUCCESS -> {
                        Log.i(TAG, "All words passed the first check")
                        Log.i(TAG, "Recovery phrase is \"${wordCheck.recoveryPhrase}\"")
                        onBuildWalletButtonClicked(WalletCreateType.RECOVER(wordCheck.recoveryPhrase))
                    }
                    is WordCheckResult.FAILURE -> {
                        Log.i(TAG, "Not all words are valid")
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = wordCheck.errorMessage,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xfff6cf47)),
            shape = RoundedCornerShape(20.dp),
            border = standardBorder,
            modifier = Modifier
                .padding(top = 12.dp, start = 4.dp, end = 4.dp, bottom = 12.dp)
                .standardShadow(20.dp)
                .height(70.dp)
                .width(240.dp)
                .semantics { testTag = "Intro EnterRecoveryPhrase Button" }
        ) {
            Text(
                stringResource(R.string.recover_wallet),
                textAlign = TextAlign.Center,
            )
        }

    }
}

@Composable
fun WordField(
    wordNumber: Int,
    recoveryWordMap: Map<Int, String>,
    setRecoveryPhraseWordMap: (Map<Int, String>) -> Unit,
    focusManager: FocusManager
) {
    OutlinedTextField(
        value = recoveryWordMap[wordNumber] ?: "elvis is here",
        onValueChange = { newText ->
            val newMap: MutableMap<Int, String> = recoveryWordMap.toMutableMap()
            newMap[wordNumber] = newText

            val updatedMap = newMap.toMap()
            setRecoveryPhraseWordMap(updatedMap)
        },
        label = {
            Text(
                text = "Word $wordNumber",
                color = md_theme_dark_onBackgroundFaded,
            )
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xff2f2f2f),
            unfocusedBorderColor = Color(0xff8a8a8a),
        ),
        modifier = Modifier
            .padding(8.dp),
        keyboardOptions = when (wordNumber) {
            12 -> KeyboardOptions(imeAction = ImeAction.Done)
            else -> KeyboardOptions(imeAction = ImeAction.Next)
        },
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true,
    )
}

// @Preview(device = Devices.PIXEL_4, showBackground = true)
// @Composable
// internal fun PreviewWalletRecoveryScreen() {
//     PadawanTheme {
//         WalletRecoveryScreen({ })
//     }
// }
