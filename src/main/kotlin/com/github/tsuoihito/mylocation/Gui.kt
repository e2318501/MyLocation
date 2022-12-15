package com.github.tsuoihito.mylocation

import com.github.tsuoihito.mylocation.model.Point
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

private const val INVENTORY_SIZE = 54

fun getGui(playerPoints: Map<UUID, List<Point>>, uuid: UUID?): Inventory {
    val gui = Bukkit.createInventory(null, INVENTORY_SIZE, "登録地点")
    playerPoints[uuid]?.let {
        getItems(it).forEachIndexed { i, item -> gui.setItem(i, item) }
    }
    return gui
}

private fun getItems(points: List<Point>): List<ItemStack> {
    return points.take(INVENTORY_SIZE).mapIndexed { index, point ->
        val item = ItemStack(Material.valueOf(point.materialString))
        val meta = item.itemMeta
        meta?.apply {
            setDisplayName(point.name)
            val loc = point.location
            lore = listOf<String>()
                .plus("ワールド: ${loc.world?.name ?: ""}")
                .plus("座標: (${loc.blockX}, ${loc.blockY}, ${loc.blockZ})")
                .plus("番号: ${index + 1}")
            addEnchant(Enchantment.MENDING, 1, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        item.itemMeta = meta
        item
    }
}

fun getIndex(lore: List<String>): Int {
    return lore[2].substringAfter("番号: ").toInt() - 1
}
