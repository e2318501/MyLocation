package com.github.tsuoihito.mylocation.command

import com.github.tsuoihito.mylocation.MyLocation
import com.github.tsuoihito.mylocation.getGui
import com.github.tsuoihito.mylocation.model.Point
import com.github.tsuoihito.mylocation.savePoints
import com.github.tsuoihito.mylocation.util.*
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class MainCommand(private val plugin: MyLocation) : TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) return true

        if (args.isEmpty()) {
            val gui = getGui(plugin.playerPoints, sender.uniqueId)
            plugin.openingInventories = plugin.openingInventories
                .plus(Pair(sender.uniqueId, gui))
            sender.openInventory(gui)
            return true
        }

        val points = plugin.playerPoints[sender.uniqueId]
        val subCommand = args[0]

        if (subCommand == "add") {
            if (args.size != 2) {
                sender.sendMessage(getInvalidUsageMessage())
                return false
            }
            val name = args[1]

            val item = sender.inventory.itemInMainHand
            val material = if (item.type == Material.AIR) Material.ENDER_EYE else item.type
            val newPoints = (points ?: emptyList())
                .plus(Point(name, sender.location, material.toString()))
            plugin.playerPoints = plugin.playerPoints
                .plus(Pair(sender.uniqueId, newPoints))

            savePoints(plugin.dataFolder, sender.uniqueId, newPoints)

            sender.sendMessage(getPointAddedMessage(name))
            return true
        }

        if (subCommand == "remove") {
            if (args.size != 2) {
                sender.sendMessage(getInvalidUsageMessage())
                return false
            }

            val index: Int
            try {
                index = args[1].toInt() - 1
            } catch (e: NumberFormatException) {
                sender.sendMessage(getInvalidUsageMessage())
                return false
            }

            if (points == null || index < 0 || points.size < index + 1) {
                sender.sendMessage(getPointNotFoundMessage())
                return true
            }

            val removedPoint = points[index]
            val newPoints = points.minus(removedPoint)
            plugin.playerPoints = plugin.playerPoints
                .plus(Pair(sender.uniqueId, newPoints))

            savePoints(plugin.dataFolder, sender.uniqueId, newPoints)

            sender.sendMessage(getPointRemovedMessage(removedPoint.name))
            return true
        }

        sender.sendMessage(getNoSuchCommandExistsMessage())
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): List<String> {
        if (sender !is Player) return emptyList()
        if (args.size == 1) {
            return listOf("add", "remove").filter { it.startsWith(args[0]) }
        }
        if (args.size == 2) {
            if (args[0] == "add") {
                return listOf("新規地点").filter { it.startsWith(args[1]) }
            }
            if (args[0] == "remove") {
                val points = plugin.playerPoints[sender.uniqueId]
                    ?: return emptyList()
                return (0..points.size)
                    .minus(0)
                    .map { it.toString() }
                    .filter { it.startsWith(args[1]) }
            }
        }
        return emptyList()
    }
}
