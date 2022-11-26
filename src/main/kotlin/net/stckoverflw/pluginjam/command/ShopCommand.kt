package net.stckoverflw.pluginjam.command

import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.gui.openGUI
import net.stckoverflw.pluginjam.gui.armorGui
import net.stckoverflw.pluginjam.util.WorldUtil

class ShopCommand {

    val command = command("shop") {
        runs {
            player.openGUI(armorGui())
        }
    }
}
