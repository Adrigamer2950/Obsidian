package me.devadri.obsidian.nms.reflection.world

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import me.devadri.obsidian.nms.reflection.util.RandomSourceRef

// net.minecraft.server.level.ServerLevel
class ServerLevelRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.server.level.ServerLevel") ?: throw RuntimeException("Could not get ServerLevel class")
        val randomField = ReflectionUtil.remapField(classRef, "random", RandomSourceRef.classRef) ?: throw RuntimeException("Could not get ServerLevel#random field")
    }

    val random: RandomSourceRef = RandomSourceRef.fromNMS(randomField.get(instance))
}