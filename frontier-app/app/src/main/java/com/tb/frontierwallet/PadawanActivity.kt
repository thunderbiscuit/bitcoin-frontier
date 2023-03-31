/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.tb.frontierwallet.data.WalletRepository
import com.tb.frontierwallet.data.Wallet
import com.tb.frontierwallet.ui.intro.IntroNavigation
import com.tb.frontierwallet.theme.PadawanTheme
import com.tb.frontierwallet.ui.HomeNavigation
import com.tb.frontierwallet.utils.SnackbarLevel
import com.tb.frontierwallet.utils.fireSnackbar

private const val TAG = "PadawanActivity"

class PadawanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ask the repository if a wallet already exists
        // if so, load it and launch into wallet activity, otherwise go to intro
        if (WalletRepository.doesWalletExist()) {
            Wallet.loadExistingWallet()
            Log.i(TAG, "Wallet already exists!")

            setContent {
                PadawanTheme {
                   HomeNavigation()
                }
            }
        } else {
            // this function is used in composables that navigate to the wallet activity
            // to ensure we destroy the intro activity once the wallet starts
            val onBuildWalletButtonClicked : (WalletCreateType) -> Unit = { walletCreateType ->
                try {
                    // load up a wallet either from scratch or using a BIP39 recovery phrase
                    when (walletCreateType) {
                        // if we create a wallet from scratch we don't need a recovery phrase
                        is WalletCreateType.FROMSCRATCH -> Wallet.createWallet()
                        is WalletCreateType.RECOVER -> Wallet.recoverWallet(walletCreateType.recoveryPhrase)
                    }

                    setContent {
                        PadawanTheme {
                            HomeNavigation()
                        }
                    }
                } catch (e: Throwable) {
                    Log.i(TAG, "Could not build wallet: $e")
                    fireSnackbar(
                        view = findViewById(R.id.content),
                        level = SnackbarLevel.ERROR,
                        message = "Error: $e"
                    )
                }
            }

            setContent {
                PadawanTheme {
                    IntroNavigation(onBuildWalletButtonClicked)
                }
            }
        }
    }
}

sealed class WalletCreateType {
    object FROMSCRATCH : WalletCreateType()
    class RECOVER(val recoveryPhrase: String) : WalletCreateType()
}
