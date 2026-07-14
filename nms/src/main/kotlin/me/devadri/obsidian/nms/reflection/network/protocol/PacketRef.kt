package me.devadri.obsidian.nms.reflection.network.protocol

import me.devadri.obsidian.nms.reflection.ReflectionUtil

// net.minecraft.network.protocol.Packet
open class PacketRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.network.protocol.Packet")
            ?: throw RuntimeException("Could not get Packet class")
    }
}