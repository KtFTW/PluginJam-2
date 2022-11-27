package net.stckoverflw.pluginjam.command

import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.gui.openGUI
import net.stckoverflw.pluginjam.gui.armorGui
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.WorldUtil
import net.stckoverflw.pluginjam.util.mini
import net.stckoverflw.pluginjam.util.prefix

class ShopCommand {

    val command = command("shop") {
        runs {
            if(! Timer.isRunning()) {
                player.sendMessage(prefix.append(mini("<red>Der Shop ist nur während der Runde verfügbar!")))
                return@runs
            }

            player.openGUI(armorGui())
        }
    }
}
