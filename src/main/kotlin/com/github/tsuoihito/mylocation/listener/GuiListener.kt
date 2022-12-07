package com.github.tsuoihito.mylocation.listener

import com.github.tsuoihito.mylocation.MyLocation
import com.github.tsuoihito.mylocation.getIndex
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiListener(private val plugin: MyLocation) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val clicker = event.whoClicked
        val gui = plugin.openingInventories[clicker.uniqueId]
        if (
            gui == null ||
            gui !== event.view.topInventory ||
            event.clickedInventory !== event.view.topInventory
        ) return

        event.isCancelled = true
        val lore = event.currentItem?.itemMeta?.lore ?: return
        val location = plugin.playerPoints[clicker.uniqueId]
            ?.get(getIndex(lore))?.location
        location?.let { clicker.teleport(it) }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        plugin.openingInventories = plugin.openingInventories
            .filterKeys { it != event.player.uniqueId }
    }
}
