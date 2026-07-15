package me.devadri.obsidian

import me.devadri.obsidian.nms.NmsSound
import me.devadri.obsidian.nms.impl.NmsSoundImpl

object Nms {

    val sound: NmsSound = NmsSoundImpl()
}