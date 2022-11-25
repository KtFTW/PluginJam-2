package net.stckoverflw.pluginjam.listeners

import net.axay.kspigot.event.listen
import net.stckoverflw.pluginjam.scoreboard.GameScoreboard
import net.stckoverflw.pluginjam.timer.Timer
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerJoinEvent

object LobbyListeners {

    fun register() {
        listen<EntityDamageEvent> {
            it.isCancelled = !Timer.isRunning()
        }

        listen<FoodLevelChangeEvent> {
            it.isCancelled = !Timer.isRunning()
        }

        listen<PlayerJoinEvent> {
           if(Timer.isRunning()) return@listen
            GameScoreboard.setScore(it.player, 0)
            it.player.inventory.clear()
            it.player.health = 20.0
            it.player.foodLevel = 20
        }
    }
}
