package com.github.tsuoihito.mylocation.listener

import com.github.tsuoihito.mylocation.MyLocation
import com.github.tsuoihito.mylocation.loadPoints
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class OnlineListener(private val plugin: MyLocation) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        loadPoints(plugin.dataFolder, uuid)?.let {
            plugin.playerPoints = plugin.playerPoints.plus(Pair(uuid, it))
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        plugin.playerPoints = plugin.playerPoints
            .filterKeys { it != event.player.uniqueId }
    }
}
