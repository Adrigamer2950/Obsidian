package me.devadri.obsidian.nms.reflection.player

import me.devadri.obsidian.nms.NmsComparisonResult
import me.devadri.obsidian.nms.NmsVersions
import me.devadri.obsidian.nms.reflection.ReflectionUtil
import org.bukkit.entity.Player

// <=1.20.4 - org.bukkit.craftbukkit.VERSION.entity.CraftPlayer
// >=1.20.5 - org.bukkit.craftbukkit.entity.CraftPlayer
class CraftPlayerRef(val instance: Any) {

    companion object {

        val classRef = ReflectionUtil.remapClass(
            when (NmsVersions.compareCurrent(NmsVersions.V1_20_R3)) {
                NmsComparisonResult.EQUAL,
                NmsComparisonResult.OLDER -> "org.bukkit.craftbukkit.${NmsVersions.current.packageFormatted}.entity.CraftPlayer"

                else -> "org.bukkit.craftbukkit.entity.CraftPlayer"
            }
        ) ?: throw RuntimeException("Could not get CraftPlayer class")

        val handleMethod = ReflectionUtil.remapMethod(classRef, "getHandle")
            ?: throw RuntimeException("Could not get CraftPlayer#getHandle method")

        fun fromBukkit(player: Player): CraftPlayerRef = CraftPlayerRef(player)
    }

    val handle: ServerPlayerRef = ServerPlayerRef(handleMethod(instance))
}