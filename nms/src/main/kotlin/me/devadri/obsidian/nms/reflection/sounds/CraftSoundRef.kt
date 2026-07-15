package me.devadri.obsidian.nms.reflection.sounds

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import org.bukkit.Sound

// >=1.21.3 - org.bukkit.craftbukkit.CraftSound
class CraftSoundRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("org.bukkit.craftbukkit.CraftSound")
            ?: throw RuntimeException("Could not get SoundEvent class")
        fun fromNMS(instance: Any): CraftSoundRef = CraftSoundRef(instance)

        val bukkitToMinecraftMethod = ReflectionUtil.remapMethod(classRef, "bukkitToMinecraft", Sound::class.java)
            ?: throw RuntimeException("Could not find CraftSound#bukkitToMinecraft(Sound) method")

        fun bukkitToMinecraft(sound: Sound): CraftSoundRef? {
            val obj = try {
                bukkitToMinecraftMethod(null, sound)
            } catch (_: Exception) {
                return null
            }

            return fromNMS(obj)
        }
    }
}