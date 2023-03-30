package com.tb.bitcoinfrontier

import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FrontierTest {
    @Test
    fun welcomeToTheFrontier() {
        Assert.assertEquals(welcome(), "Welcome to the frontier")
    }
}
