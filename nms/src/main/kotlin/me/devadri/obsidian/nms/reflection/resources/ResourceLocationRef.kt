package me.devadri.obsidian.nms.reflection.resources

import me.devadri.obsidian.nms.reflection.ReflectionUtil

// net.minecraft.resources.ResourceLocation
class ResourceLocationRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.resources.ResourceLocation") ?: throw RuntimeException("Could not find ResourceLocation field")

        fun fromNMS(instance: Any): ResourceLocationRef = ResourceLocationRef(instance)
    }
}