/*
 * Copyright 2020-2022 thunderbiscuit and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the ./LICENSE file.
 */

package com.tb.frontierwallet.utils

data class RequiredInitialWalletData(
    val descriptor: String,
    val changeDescriptor: String
)
