package net.stckoverflw.pluginjam.scoreboard

import net.axay.kspigot.extensions.onlinePlayers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.game.playerPoints
import net.stckoverflw.pluginjam.game.points
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

object GameScoreboard {
    private val scoreboard: Scoreboard
        get() = Bukkit.getScoreboardManager().mainScoreboard
    private lateinit var objective: Objective

    fun create() {
        scoreboard.getObjective("game")?.unregister()
        objective = scoreboard.getObjective("game")
            ?: scoreboard.registerNewObjective("game", Criteria.DUMMY, Component.text("ALLES ODER NICHTS").color(NamedTextColor.RED))

        objective.displaySlot = DisplaySlot.SIDEBAR
    }

    fun setScore(player: Player, score: Int) {
        objective.getScore(player).score = score
        onlinePlayers.sortedByDescending { it.points }.forEachIndexed { index, it ->
            scoreboard.getPlayerTeam(it)?.removePlayer(it)
            getTeam(index + 1).addPlayer(it)
        }
    }

    fun updateTeams() {
        onlinePlayers.sortedByDescending { it.points }.forEachIndexed { index, it ->
            scoreboard.getPlayerTeam(it)?.removePlayer(it)
            getTeam(index + 1).addPlayer(it)
        }
    }

    private fun getTeam(placement: Int): Team {
        var team = scoreboard.getTeam("t$placement")
        if (team != null) return team
        val color = when(placement) {
            1 -> NamedTextColor.GOLD
            2 -> NamedTextColor.AQUA
            3 -> NamedTextColor.DARK_RED
            else -> NamedTextColor.GREEN
        }
        team = scoreboard.registerNewTeam("t$placement")
        team.prefix(Component.text(placement, color).append(Component.text(" | ", NamedTextColor.DARK_GRAY)))
        team.color(color)
        return team
    }
}
