package net.stckoverflw.pluginjam.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.Constants
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartCommand : CommandExecutor{

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (Timer.isRunning()) {
            sender.sendMessage(Component.text("Das Spiel wurde bereits gestartet.").color(NamedTextColor.RED))
            return true
        }

        Timer.start()
        Bukkit.getWorld("world")!!.worldBorder.size = Constants.WORLDBORDER_SIZE
        Bukkit.broadcast(Component.text("Das Spiel wurde gestartet!").color(NamedTextColor.GREEN))
        return true
    }
}
