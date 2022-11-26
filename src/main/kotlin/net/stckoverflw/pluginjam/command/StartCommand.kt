package net.stckoverflw.pluginjam.command

import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.extensions.broadcast
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.Constants
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartCommand {

    val command = command("start") {
        require(!Timer.isRunning())
        requiresPermission("ktftw.start")
        runs {
            Timer.start()
            GameListeners.register()
            server.worlds.firstOrNull { it.name == "world" }?.worldBorder?.size = Constants.WORLDBORDER_SIZE
            broadcast(Component.text("Das Spiel wurde gestartet!").color(NamedTextColor.GREEN))
        }
    }
}
