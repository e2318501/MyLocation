package com.github.tsuoihito.mylocation.util

import org.bukkit.ChatColor.*

private val prefix = "${GRAY}[${GREEN}MyLocation${GRAY}]${RESET}"

fun getPointAddedMessage(name: String): String {
    return "$prefix ${AQUA}地点「${GREEN}${name}${AQUA}」を追加しました"
}

fun getPointRemovedMessage(name: String): String {
    return "$prefix ${AQUA}地点「${GREEN}${name}${AQUA}」を削除しました"
}

fun getInvalidUsageMessage(): String {
    return "$prefix ${RED}コマンドの使用方法が正しくありません"
}

fun getPointNotFoundMessage(): String {
    return "$prefix ${RED}地点が見つかりませんでした"
}

fun getNoSuchCommandExistsMessage(): String {
    return "$prefix ${RED}そのようなコマンドはありません"
}
