package net.stckoverflw.pluginjam.command

import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.extensions.broadcast
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.listeners.GameListeners
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.Constants
import net.stckoverflw.pluginjam.util.WorldUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ResetCommand {

    val command = command("reset") {
        requiresPermission("ktftw.reset")
        runs {
            WorldUtil.initializeReset()
        }
    }
}
