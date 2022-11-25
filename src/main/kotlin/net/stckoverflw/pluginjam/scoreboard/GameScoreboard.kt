package net.stckoverflw.pluginjam.scoreboard

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

object GameScoreboard {
    private val scoreboard: Scoreboard
        get() = Bukkit.getScoreboardManager().mainScoreboard
    private lateinit var objective: Objective

    fun create() {
        scoreboard.getObjective("game")?.unregister()
        objective = scoreboard.getObjective("game")
            ?: scoreboard.registerNewObjective("game", Criteria.DUMMY, Component.text("PLUGIN JAM").color(NamedTextColor.RED))

        objective.displaySlot = DisplaySlot.SIDEBAR
    }

    fun setScore(player: Player, score: Int) {
        objective.getScore(player).score = score
    }
}
