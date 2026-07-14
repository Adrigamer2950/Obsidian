package me.devadri.obsidian.nms.reflection.util

import me.devadri.obsidian.nms.reflection.ReflectionUtil

// net.minecraft.util.RandomSource
class RandomSourceRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.util.RandomSource") ?: throw RuntimeException("Could not find RandomSource class")
        fun fromNMS(instance: Any): RandomSourceRef = RandomSourceRef(instance)

        val nextLongMethod = ReflectionUtil.remapMethod(classRef, "nextLong")
            ?: throw RuntimeException("Could not find Registry#get(ResourceLocation) method")
    }

    fun nextLong(): Long {
        return nextLongMethod(instance) as Long
    }
}