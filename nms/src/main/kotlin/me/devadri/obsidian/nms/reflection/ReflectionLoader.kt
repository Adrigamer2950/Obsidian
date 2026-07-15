package me.devadri.obsidian.nms.reflection

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ConfigurationBuilder

object ReflectionLoader {

    fun initalizeAllReflections() {
        val reflections = Reflections(
            ConfigurationBuilder()
                .forPackages(this::class.java.packageName)
                .setScanners(Scanners.SubTypes.filterResultsBy { true })
                .disableLogging()
        )

        reflections.getSubTypesOf(Any::class.java).forEach {
            if (!it.name.startsWith(this::class.java.packageName)) return@forEach

            try {
                Class.forName(it.name, true, this::class.java.classLoader)
            } catch (_: NoClassDefFoundError) {
            } catch (_: ClassNotFoundException) {
            } catch (_: ExceptionInInitializerError) {
            }
        }
    }
}