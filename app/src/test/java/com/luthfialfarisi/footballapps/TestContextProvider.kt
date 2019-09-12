package com.luthfialfarisi.footballapps

import com.luthfialfarisi.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Unconfined
}