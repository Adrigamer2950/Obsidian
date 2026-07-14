package me.devadri.obsidian.nms.reflection.registry

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import me.devadri.obsidian.nms.reflection.resources.ResourceLocationRef
import me.devadri.obsidian.nms.reflection.sounds.SoundEventRef
import java.util.Optional

// <1.19.3 -  net.minecraft.core.Registry
// >=1.19.3 - net.minecraft.core.registries.BuiltInRegistries
class RegistryRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.core.Registry") ?: throw RuntimeException("Could not find Registry class")
        fun fromNMS(instance: Any): RegistryRef = RegistryRef(instance)

        val getMethod = ReflectionUtil.remapMethod(classRef, "get", ResourceLocationRef.classRef)
            ?: throw RuntimeException("Could not find Registry#get(ResourceLocation) method")
    }

    inline fun <reified T> get(key: ResourceLocationRef): T? {
        val t = getMethod(instance, key.instance)

        val value = if (t is Optional<*>) {
            t.get()
        } else t

        if (T::class.java.isAssignableFrom(SoundEventRef::class.java)) {
            return SoundEventRef(value) as T?
        } else {
            throw RuntimeException("Trying to get values from unsupported registry")
        }
    }
}