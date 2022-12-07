package com.github.tsuoihito.mylocation

import com.github.tsuoihito.mylocation.command.MainCommand
import com.github.tsuoihito.mylocation.listener.GuiListener
import com.github.tsuoihito.mylocation.listener.OnlineListener
import com.github.tsuoihito.mylocation.model.Point
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class MyLocation : JavaPlugin() {
    var playerPoints: Map<UUID, List<Point>> = HashMap()
    var openingInventories: Map<UUID, Inventory> = HashMap()

    override fun onEnable() {
        val pm = server.pluginManager
        pm.registerEvents(GuiListener(this), this)
        pm.registerEvents(OnlineListener(this), this)

        getCommand("mylocation")?.setExecutor(MainCommand(this))

        ConfigurationSerialization.registerClass(Point::class.java)

        loadOnlinePlayerPoints()
    }

    override fun onDisable() {
    }

    private fun loadOnlinePlayerPoints() {
        server.onlinePlayers.forEach {
            loadPoints(dataFolder, it.uniqueId)?.let { pt ->
                playerPoints = playerPoints.plus(Pair(it.uniqueId, pt))
            }
        }
    }
}
