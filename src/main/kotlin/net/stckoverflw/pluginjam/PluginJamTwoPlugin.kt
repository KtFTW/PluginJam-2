package net.stckoverflw.pluginjam

import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigot
import net.stckoverflw.pluginjam.command.StartCommand
import net.stckoverflw.pluginjam.game.handleDeath
import net.stckoverflw.pluginjam.listeners.LobbyListeners
import net.stckoverflw.pluginjam.scoreboard.GameScoreboard
import org.bukkit.Bukkit
import org.bukkit.event.entity.PlayerDeathEvent

class PluginJamTwoPlugin : KSpigot() {

    override fun startup() {
        GameScoreboard.create()
        LobbyListeners.register()
        Bukkit.getPluginCommand("start")?.setExecutor(StartCommand())

        Bukkit.getWorld("world")!!.worldBorder.size = 25.0

        listen<PlayerDeathEvent> {
            it.entity.handleDeath()
        }
    }

}
