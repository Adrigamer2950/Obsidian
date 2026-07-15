package me.devadri.obsidian.nms.reflection.network.protocol

import me.devadri.obsidian.nms.NmsComparisonResult
import me.devadri.obsidian.nms.NmsVersions
import me.devadri.obsidian.nms.reflection.ReflectionUtil
import me.devadri.obsidian.nms.reflection.entity.EntityRef
import me.devadri.obsidian.nms.reflection.holder.HolderRef
import me.devadri.obsidian.nms.reflection.sounds.SoundEventRef
import me.devadri.obsidian.nms.reflection.sounds.SoundSourceRef
import java.lang.reflect.Constructor

// net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
class ClientboundSoundEntityPacketRef(instance: Any) : PacketRef(instance) {

    companion object {

        val classRef = ReflectionUtil.remapClass("net.minecraft.network.protocol.game.ClientboundSoundEntityPacket")
            ?: throw RuntimeException("Could not get ClientboundSoundEntityPacket class")

        val constructor: Constructor<out Any> = try {
            when (NmsVersions.compareCurrent(NmsVersions.V1_19_R1)) {
                NmsComparisonResult.EQUAL,
                NmsComparisonResult.OLDER -> classRef.getDeclaredConstructor(
                    SoundEventRef.classRef,
                    SoundSourceRef.classRef,
                    EntityRef.classRef,
                    Float::class.java,
                    Float::class.java
                )

                else -> classRef.getDeclaredConstructor(
                    HolderRef.classRef,
                    SoundSourceRef.classRef,
                    EntityRef.classRef,
                    Float::class.java,
                    Float::class.java,
                    Long::class.java
                )
            }
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Couldn't find ClientboundSoundEntityPacket constructor", e)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        fun createPre1_19_3(
            event: Any,
            source: SoundSourceRef,
            entity: EntityRef,
            volume: Float,
            pitch: Float
        ): ClientboundSoundEntityPacketRef = ClientboundSoundEntityPacketRef(
            constructor.newInstance(
                event,
                source.instance,
                entity.instance,
                volume,
                pitch
            )
        )

        fun createPost1_19_3(
            holder: HolderRef,
            source: SoundSourceRef,
            entity: EntityRef,
            volume: Float,
            pitch: Float,
            seed: Long,
        ): ClientboundSoundEntityPacketRef = ClientboundSoundEntityPacketRef(
            constructor.newInstance(
                holder.instance,
                source.instance,
                entity.instance,
                volume,
                pitch,
                seed
            )
        )
    }
}