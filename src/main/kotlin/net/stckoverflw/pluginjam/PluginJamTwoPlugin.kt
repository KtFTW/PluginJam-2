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
        Timer.invoke()
        ResetCommand()
        ShopCommand()
        StartCommand()
        LobbyListeners.register()

        task(sync = true, delay = 1) {
            GameScoreboard.create()
            Bukkit.getWorld("world") !!.worldBorder.size = 30.0
        }
    }

}
