package com.root14.hoopoe.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText

/**
 *@sample WebSocketHelper().subscribeWebSocket(object : WebSocketHelper.IWebSocketListener {
 *  override fun observeSocket(data: String) {
 *   println(data)
 *   }
 *  })
 */
class WebSocketHelper {
    private val client = HttpClient {
        install(WebSockets)
    }

    interface IWebSocketListener {
        fun observeSocket(data: String)
    }

    //default listen bitcoin
    suspend fun subscribeWebSocket(
        listener: IWebSocketListener,
        host: String = "ws.coincap.io",
        asset: String = "bitcoin",
        path: String = "/prices?assets=${asset}"
    ): IWebSocketListener {
        client.webSocket(
            method = HttpMethod.Get, host = host, path = path
        ) {
            while (true) {
                val othersMessage = incoming.receive() as? Frame.Text ?: continue
                //println(othersMessage.readText())
                listener.observeSocket(othersMessage.readText())
            }
        }
        return listener
    }

}