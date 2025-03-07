package io.kotzilla.sample.sdk

import org.koin.dsl.koinApplication
import org.koin.dsl.module
import java.util.UUID

const val SDK_VERSION = "1.0"

class IdGenerator(){
    fun getId() : String = UUID.randomUUID().toString()
}

object LibrarySDK {

    private fun sdkModules() = module {
        factory { IdGenerator() }
    }
    private val koin = koinApplication { modules(sdkModules()) }.koin
    private val idGen by lazy { koin.get<IdGenerator>() }

    const val TAG : String = "[SAMPLE-SDK]"

    fun start(){
        println("$TAG $SDK_VERSION - start")
        println("$TAG $SDK_VERSION - id: ${idGen.getId()}")
    }

    fun stop(){
        println("$TAG $SDK_VERSION - stop")
    }
}