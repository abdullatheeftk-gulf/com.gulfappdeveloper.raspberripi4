package com.gulfappdeveloper

import com.gulfappdeveloper.dao.DatabaseFactory
import com.gulfappdeveloper.di.mainModule
import io.ktor.server.application.*
import com.gulfappdeveloper.plugins.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init(environment.config)
    install(Koin) {
        modules(mainModule)
    }
    configureSerialization()
    configureRouting()
    configureTemplating()
    configureHTTP()

}
