package com.impala.rclsfa.utils

import android.content.Context
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class SocketManager private constructor() {
    private var socket: Socket? = null
    private var onConnectCallback: (() -> Unit)? = null

    init {
        initializeSocket()
    }

    companion object {
        private var instance: SocketManager? = null

        fun getInstance(): SocketManager {
            if (instance == null) {
                instance = SocketManager()
            }
            return instance as SocketManager
        }
    }

    private fun initializeSocket() {
        val options = IO.Options()
        options.reconnection = true

        try {
//            socket = IO.socket("http://192.168.0.102:6044", options)
            socket = IO.socket("http://172.16.16.91:6044", options)
//            socket = IO.socket("http://157.230.195.60:6044", options)
            setupSocketListeners()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupSocketListeners() {
        socket?.on(Socket.EVENT_CONNECT) {
            onConnectCallback?.invoke()
        }
        socket?.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket?.on(Socket.EVENT_CONNECT_ERROR, onError)
    }

    // Set a callback function to be called when the connection is established
    fun setOnConnectCallback(callback: () -> Unit) {
        onConnectCallback = callback
    }

    fun connect() {
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }

    fun sendSocketInfoFromPrefs(context: Context) {
        val socketId = socket?.id()
        val sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val dataToSend = JSONObject()
        dataToSend.put("socket_id", socketId)
        dataToSend.put("user_id", sharedPreferences.getString("id", ""))
        socket?.emit("send_socket_info", dataToSend)
    }

    fun sendLocationViaSocket(latitude: Double, longitude: Double) {
        socket?.emit("coordinates", JSONObject().apply {
            put("latitude", latitude)
            put("longitude", longitude)
        })
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }

    private val onConnect = Emitter.Listener {
        // Handle the connection event
    }

    private val onDisconnect = Emitter.Listener {
        // Handle the disconnection event
    }

    private val onError = Emitter.Listener {
        val error = it[0] as JSONObject
        // Handle the error event
    }

}


