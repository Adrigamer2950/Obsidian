package me.devadri.obsidian.nms.reflection.registry

import me.devadri.obsidian.nms.NmsComparisonResult
import me.devadri.obsidian.nms.NmsVersions
import me.devadri.obsidian.nms.reflection.ReflectionUtil

// <1.19.3  - net.minecraft.core.Registry
// >=1.19.3 - net.minecraft.core.registries.BuiltInRegistries
class BuiltInRegistriesRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass(
            when (NmsVersions.compareCurrent(NmsVersions.V1_19_R1)) {
                NmsComparisonResult.EQUAL,
                NmsComparisonResult.OLDER -> "net.minecraft.core.Registry"

                else -> "net.minecraft.core.registries.BuiltInRegistries"
            }
        ) ?: throw RuntimeException("Could not find Registry class")
        val soundEventField = ReflectionUtil.remapField(classRef, "SOUND_EVENT")
            ?: throw RuntimeException("Could not find Registry/BuiltInRegistries.SOUND_EVENT field")

        @JvmField
        val SOUND_EVENT: RegistryRef = RegistryRef.fromNMS(
            runCatching { soundEventField.get(null) }.run {
                getOrNull() ?: throw RuntimeException(
                    "Could not get Registry/BuiltInRegistries.SOUND_EVENT",
                    exceptionOrNull()
                )
            }
        )
    }
}