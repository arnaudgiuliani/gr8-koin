package io.kotzilla.sample.sdk

import android.content.Context
//import io.kotzilla.coroutines.*
import kotlinx.coroutines.*
import java.util.UUID
import kotlin.coroutines.CoroutineContext

fun generateId() : String = UUID.randomUUID().toString()

const val SDK_VERSION = "1.0-0.13.7-Beta1"

private object SDKService {

    private var job : Job? = null
    private var running = false

    fun start(coroutineScope: CoroutineScope) {
        running = true

        job = coroutineScope.launch {
            delay(1000L)
            while(running){
                val id = generateId()
                println("Generated: $id")
                delay(5000L)
            }
        }
    }

    fun stop(){
        running = false
        job?.cancel()
    }
}

object LibrarySDK : CoroutineScope  {
    const val TAG : String = "[SAMPLE-SDK]"
    private val _supervisorJob = SupervisorJob()

    private val error = CoroutineExceptionHandler { _, exception ->
        System.err.println("$TAG Error - $exception")
    }

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val _dispatcher = newSingleThreadContext(TAG) // Dispatchers.IO
    override val coroutineContext: CoroutineContext = _supervisorJob + _dispatcher + error

    fun start(context: Context, ){
        println("$TAG $SDK_VERSION - start")
//        setup(context)

        launch {
            SDKService.start(this)
        }
    }

    fun stop(){
        println("$TAG $SDK_VERSION - stop")
        SDKService.stop()
    }

}