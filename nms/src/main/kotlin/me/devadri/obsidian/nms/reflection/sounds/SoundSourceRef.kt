package me.devadri.obsidian.nms.reflection.sounds

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import org.bukkit.SoundCategory

// net.minecraft.sounds.SoundSource
class SoundSourceRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.sounds.SoundSource")
            ?: throw RuntimeException("Could not get SoundSource class")
        val getNameMethod = ReflectionUtil.remapMethod(classRef, "getName")
            ?: throw RuntimeException("Could not get SoundSource#getName method")

        fun fromSoundCategory(category: SoundCategory): SoundSourceRef {
            if (!classRef.isEnum) throw IllegalStateException("SoundSource isn't an enum")

            val ssName = when (category.name) {
                "MASTER" -> "master"
                "MUSIC" -> "music"
                "RECORDS" -> "record"
                "WEATHER" -> "weather"
                "BLOCKS" -> "block"
                "HOSTILE" -> "hostile"
                "NEUTRAL" -> "neutral"
                "PLAYERS" -> "player"
                "AMBIENT" -> "ambient"
                "VOICE" -> "voice"
                "UI" -> "ui"
                else -> throw NullPointerException("Unknown SoundCategory: $category")
            }

            val ss = classRef.enumConstants.filterIsInstance<Enum<*>>().firstOrNull { getNameMethod(it) == ssName }
                ?: throw IllegalArgumentException("Unknown SoundSource: $ssName")

            return SoundSourceRef(ss)
        }
    }
}