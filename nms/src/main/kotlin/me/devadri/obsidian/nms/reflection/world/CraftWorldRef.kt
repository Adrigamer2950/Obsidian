package me.devadri.obsidian.nms.reflection.world

import me.devadri.obsidian.nms.NmsVersions
import me.devadri.obsidian.nms.reflection.ReflectionUtil
import org.bukkit.World

// <=1.20.4 - org.bukkit.craftbukkit.VERSION.CraftWorld
// >=1.20.5 - org.bukkit.craftbukkit.CraftWorld
class CraftWorldRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass(when (NmsVersions.current) {
            NmsVersions.V1_17_R1,
            NmsVersions.V1_18_R1,
            NmsVersions.V1_18_R2,
            NmsVersions.V1_19_R1,
            NmsVersions.V1_19_R2,
            NmsVersions.V1_19_R3,
            NmsVersions.V1_20_R1,
            NmsVersions.V1_20_R2,
            NmsVersions.V1_20_R3 -> "org.bukkit.craftbukkit.${NmsVersions.current.packageFormatted}.CraftWorld"

            else -> "org.bukkit.craftbukkit.CraftWorld"
        }) ?: throw RuntimeException("Could not get CraftWorld class")
        val handleMethod = ReflectionUtil.remapMethod(classRef, "getHandle") ?: throw RuntimeException("Could not get CraftWorld#getHandle method")

        fun fromBukkit(world: World): CraftWorldRef = CraftWorldRef(world)
    }

    val handle: ServerLevelRef = ServerLevelRef(handleMethod(instance))
}