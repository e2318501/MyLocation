package com.github.tsuoihito.mylocation

import com.github.tsuoihito.mylocation.model.Point
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.*

fun loadPoints(pluginFolder: File, uuid: UUID): List<Point>? {
    val playerFile = getPlayerFile(getPlayersFolder(pluginFolder), uuid)
    return YamlConfiguration.loadConfiguration(playerFile)
        .getList("points")?.map { it as Point }
}

fun savePoints(pluginFolder: File, uuid: UUID, points: List<Point>) {
    try {
        pluginFolder.mkdir()
        val playersFolder = getPlayersFolder(pluginFolder)
        playersFolder.mkdir()
        val playerFile = getPlayerFile(playersFolder, uuid)
        playerFile.createNewFile()

        val yaml = YamlConfiguration()
        yaml.set("points", points)
        yaml.save(playerFile)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun getPlayerFile(playersFolder: File, uuid: UUID): File {
    return File(playersFolder, "${uuid}.yml")
}

private fun getPlayersFolder(pluginFolder: File): File {
    return File(pluginFolder, "players")
}
