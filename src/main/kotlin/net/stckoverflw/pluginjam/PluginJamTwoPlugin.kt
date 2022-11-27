package net.stckoverflw.pluginjam

import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigot
import net.axay.kspigot.runnables.sync
import net.axay.kspigot.runnables.task
import net.stckoverflw.pluginjam.command.ResetCommand
import net.stckoverflw.pluginjam.command.ShopCommand
import net.stckoverflw.pluginjam.command.StartCommand
import net.stckoverflw.pluginjam.config.Config
import net.stckoverflw.pluginjam.game.handleDeath
import net.stckoverflw.pluginjam.listeners.LobbyListeners
import net.stckoverflw.pluginjam.scoreboard.GameScoreboard
import net.stckoverflw.pluginjam.task.TaskManager
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.WorldUtil
import org.bukkit.Bukkit
import org.bukkit.event.entity.PlayerDeathEvent

class PluginJamTwoPlugin : KSpigot() {

    override fun load() {
        saveDefaultConfig()

        if (Config.reset) {
            WorldUtil.resetWorlds()
        }
    }

    override fun startup() {
        Timer.invoke()
        TaskManager.invoke()
        ResetCommand()
        ShopCommand()
        StartCommand()
        LobbyListeners.register()

        task(sync = true, delay = 1) {
            GameScoreboard.create()
            Bukkit.getWorld("world") !!.worldBorder.size = 30.0
        }
    }

    override fun shutdown() {
        Config.reset = true
    }
}
