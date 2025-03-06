package io.kotzilla.sample.sdk

import kotlin.test.Test

class RunSDKTest {

    @Test
    fun runSDK(){
        LibrarySDK.start()
        LibrarySDK.stop()
    }
}