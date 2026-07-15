package me.devadri.obsidian.nms.reflection.player

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import me.devadri.obsidian.nms.reflection.network.ServerGamePacketListenerImplRef

// net.minecraft.server.level.ServerPlayer
class ServerPlayerRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.server.level.ServerPlayer") ?: throw RuntimeException("Could not get ServerPlayer class")
        val connectionField = ReflectionUtil.remapField(classRef, "connection") ?: throw RuntimeException("Could not get ServerPlayer#connection field")
    }

    val connection: ServerGamePacketListenerImplRef = ServerGamePacketListenerImplRef(connectionField.get(instance))
}