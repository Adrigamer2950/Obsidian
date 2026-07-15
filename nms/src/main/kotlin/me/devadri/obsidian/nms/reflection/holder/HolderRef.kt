package me.devadri.obsidian.nms.reflection.holder

import me.devadri.obsidian.nms.reflection.ReflectionUtil
import java.lang.reflect.Modifier

// <1.19.3   - N/A
// >= 1.19.3 - net.minecraft.core.Holder
class HolderRef(val instance: Any) {

    companion object {

        fun fromNMS(instance: Any): HolderRef = HolderRef(instance)

        val classRef = ReflectionUtil.remapClass("net.minecraft.core.Holder")
            ?: throw RuntimeException("Could not find Holder class")
        val directMethod =
            ReflectionUtil.remapMethod(classRef, "direct")
                ?.takeIf {
                    Modifier.isStatic(it.modifiers) &&
                            it.parameterCount == 1 &&
                            it.returnType == classRef
                }
                ?: classRef.declaredMethods.firstOrNull {
                    Modifier.isStatic(it.modifiers) &&
                            it.parameterCount == 1 &&
                            it.returnType == classRef
                }
                ?: throw RuntimeException("Could not find Holder.direct")

        fun directSound(sound: Any): HolderRef {
            return fromNMS(
                directMethod.invoke(null, sound)
            )
        }
    }
}