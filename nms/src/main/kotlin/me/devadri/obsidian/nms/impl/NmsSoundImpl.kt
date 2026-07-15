@file:Suppress("unused")

package me.devadri.obsidian.nms.impl

import me.devadri.obsidian.nms.NmsSound
import me.devadri.obsidian.nms.NmsVersions
import me.devadri.obsidian.nms.reflection.entity.EntityRef
import me.devadri.obsidian.nms.reflection.holder.HolderRef
import me.devadri.obsidian.nms.reflection.network.protocol.ClientboundSoundEntityPacketRef
import me.devadri.obsidian.nms.reflection.player.CraftPlayerRef
import me.devadri.obsidian.nms.reflection.registry.BuiltInRegistriesRef
import me.devadri.obsidian.nms.reflection.resources.CraftNamespacedKeyRef
import me.devadri.obsidian.nms.reflection.sounds.CraftSoundRef
import me.devadri.obsidian.nms.reflection.sounds.SoundEventRef
import me.devadri.obsidian.nms.reflection.sounds.SoundSourceRef
import me.devadri.obsidian.nms.reflection.util.BukkitRegistryRef
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

class NmsSoundImpl : NmsSound {

    override fun playToPlayer(player: Player, category: SoundCategory, sound: Sound, volume: Float, pitch: Float) {
        @Suppress("DEPRECATION") val soundKey = when (NmsVersions.current) {
            NmsVersions.V26 -> BukkitRegistryRef.SOUNDS.getKey(sound) ?: throw NullPointerException("Invalid sound name")
            else -> sound.key
        }

        val nmsSound = when (NmsVersions.current) {
            NmsVersions.V1_17_R1,
            NmsVersions.V1_18_R1,
            NmsVersions.V1_18_R2,
            NmsVersions.V1_19_R1,
            NmsVersions.V1_19_R2,
            NmsVersions.V1_19_R3,
            NmsVersions.V1_20_R1,
            NmsVersions.V1_20_R2,
            NmsVersions.V1_20_R3,
            NmsVersions.V1_20_R4,
            NmsVersions.V1_21_R1 ->
                BuiltInRegistriesRef.SOUND_EVENT.get<SoundEventRef>(CraftNamespacedKeyRef.toMinecraft(soundKey))?.instance

            else -> CraftSoundRef.bukkitToMinecraft(sound)?.instance
        } ?: throw IllegalArgumentException("Sound ${soundKey.key} not found")

        val nmsCategory = SoundSourceRef.fromSoundCategory(category)

        val craftPlayer = CraftPlayerRef.fromBukkit(player)

        val packet = when (NmsVersions.current) {
            NmsVersions.V1_17_R1,
            NmsVersions.V1_18_R1,
            NmsVersions.V1_18_R2,
            NmsVersions.V1_19_R1 -> ClientboundSoundEntityPacketRef.createPre1_19_3(
                nmsSound,
                nmsCategory,
                EntityRef.fromNMS(craftPlayer.handle.instance),
                volume,
                pitch
            )

            else -> {
                ClientboundSoundEntityPacketRef.createPost1_19_3(
                    HolderRef.directSound(nmsSound),
                    nmsCategory,
                    EntityRef.fromNMS(craftPlayer.handle.instance),
                    volume,
                    pitch,
                    player.world.seed
                )
            }
        }

        craftPlayer.handle.connection.send(packet)
    }
}