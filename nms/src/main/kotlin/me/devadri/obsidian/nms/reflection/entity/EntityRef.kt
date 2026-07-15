package me.devadri.obsidian.nms.reflection.entity

import me.devadri.obsidian.nms.reflection.ReflectionUtil

// net.minecraft.world.entity.Entity
class EntityRef(val instance: Any) {

    companion object {

        @JvmField
        val classRef = ReflectionUtil.remapClass("net.minecraft.world.entity.Entity")
            ?: throw RuntimeException("Could not get Entity class")

        fun fromNMS(instance: Any): EntityRef = EntityRef(instance)
    }
}