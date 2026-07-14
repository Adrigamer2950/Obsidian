package me.devadri.obsidian.nms.reflection.sounds

import me.devadri.obsidian.nms.reflection.ReflectionUtil

// net.minecraft.sounds.SoundEvent
class SoundEventRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.sounds.SoundEvent")
            ?: throw RuntimeException("Could not get SoundEvent class")
    }
}