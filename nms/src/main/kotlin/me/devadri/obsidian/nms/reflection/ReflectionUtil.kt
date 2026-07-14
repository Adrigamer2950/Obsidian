package me.devadri.obsidian.nms.reflection

import me.devadri.obsidian.util.ClassUtil
import xyz.jpenilla.reflectionremapper.ReflectionRemapper
import java.lang.reflect.Field
import java.lang.reflect.Method

object ReflectionUtil {

    val remapper: ReflectionRemapper = ReflectionRemapper.forReobfMappingsInPaperJar()

    fun remapClassName(className: String): String = remapper.remapClassName(className)

    fun remapClass(className: String): Class<*>? = ClassUtil.searchForClass(remapClassName(className))

    fun remapField(classRef: Class<*>, fieldName: String, fieldClass: Class<*>? = null): Field? =
        runCatching {
            classRef.getField(remapper.remapFieldName(classRef, fieldName))
        }.run {
            getOrNull() ?: classRef.fields.firstOrNull { f ->
                f.name == fieldName || f.type == fieldClass
            }
        }

    fun remapMethod(classRef: Class<*>, methodName: String, vararg parameters: Class<*> = emptyArray()): Method? {
        return runCatching {
            classRef.getMethod(remapper.remapMethodName(classRef, methodName), *parameters)
        }.run {
            getOrNull() ?: classRef.methods.firstOrNull { m ->
                // I know, this is a trashy and unstable way of finding obfuscated methods, but whatever
                // As long as it works, I'm happy
                m.parameters.map { p -> p.type }.toTypedArray().contentEquals(parameters)
            }
        }
    }
}