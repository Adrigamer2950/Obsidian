package me.devadri.obsidian.nms.reflection.resources

import me.devadri.obsidian.nms.NmsComparisonResult
import me.devadri.obsidian.nms.NmsVersions
import me.devadri.obsidian.nms.reflection.ReflectionUtil
import org.bukkit.NamespacedKey

// <=1.20.4 - org.bukkit.craftbukkit.VERSION.util.CraftNamespacedKey
// >=1.20.5 - org.bukkit.craftbukkit.util.CraftNamespacedKey
class CraftNamespacedKeyRef(val instance: Any) {

    companion object {

        val classRef =
            ReflectionUtil.remapClass(
                when (NmsVersions.compareCurrent(NmsVersions.V1_20_R3)) {
                    NmsComparisonResult.EQUAL,
                    NmsComparisonResult.OLDER -> "org.bukkit.craftbukkit.${NmsVersions.current.packageFormatted}.util.CraftNamespacedKey"

                    else -> "org.bukkit.craftbukkit.util.CraftNamespacedKey"
                }
            )
                ?: throw RuntimeException("Could not find CraftNamespacedKey class")
        val toMinecraftMethod =
            ReflectionUtil.remapMethod(classRef, "toMinecraft", NamespacedKey::class.java)
                ?: throw RuntimeException("Could not find CraftNamespacedKey#toMinecraft method")

        fun toMinecraft(key: NamespacedKey): ResourceLocationRef =
            ResourceLocationRef.fromNMS(toMinecraftMethod(null, key))
    }
}