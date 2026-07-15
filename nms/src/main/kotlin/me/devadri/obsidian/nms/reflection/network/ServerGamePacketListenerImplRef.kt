package me.devadri.obsidian.nms.reflection.network

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import me.devadri.obsidian.nms.reflection.network.protocol.PacketRef

// net.minecraft.server.network.ServerGamePacketListenerImpl
class ServerGamePacketListenerImplRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.server.network.ServerGamePacketListenerImpl")
            ?: throw RuntimeException("Could not get ServerGamePacketListenerImpl class")
        val sendMethod = ReflectionUtil.remapMethod(classRef, "send", PacketRef.classRef)
            ?: ReflectionUtil.remapMethod(classRef, "sendPacket", PacketRef.classRef)
            ?: throw RuntimeException("Could not get ServerGamePacketListenerImpl#send method")
    }

    fun send(packet: PacketRef) {
        sendMethod(instance, packet.instance)
    }
}