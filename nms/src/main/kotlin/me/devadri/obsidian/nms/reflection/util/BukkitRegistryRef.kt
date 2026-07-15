package me.devadri.obsidian.nms.reflection.util

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.Registry

class BukkitRegistryRef<T : Keyed>(val instance: Registry<T>) {

    companion object {
        val classRef = Registry::class.java

        val getKeyMethod = classRef.methods.firstOrNull {
            it.name == "getKey" && it.returnType == NamespacedKey::class.java
        }

        val SOUNDS = BukkitRegistryRef(Registry.SOUNDS)
    }

    fun getKey(obj: T): NamespacedKey? {
        if (getKeyMethod == null) return null

        return getKeyMethod(instance, obj) as NamespacedKey?
    }
}