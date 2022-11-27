package net.stckoverflw.pluginjam.task

import net.axay.kspigot.runnables.KSpigotRunnable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.stckoverflw.pluginjam.game.GameData
import net.stckoverflw.pluginjam.game.points
import net.stckoverflw.pluginjam.util.Constants
import net.stckoverflw.pluginjam.util.ListenerHolder
import net.stckoverflw.pluginjam.util.TaskHolder
import net.stckoverflw.pluginjam.util.broadcastPointChangeMessage
import net.stckoverflw.pluginjam.util.text
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.UUID

abstract class Task(private val name: Component) : ListenerHolder, TaskHolder {
    override val listeners: MutableList<Listener> = mutableListOf()
    override val tasks: MutableList<KSpigotRunnable> = mutableListOf()
    private val finished = mutableListOf<UUID>()
    abstract fun register()
    fun unregister() {
        unregisterAllListeners()
        removeAllTasks()
    }

    fun finish(player: Player) {
        if (finished.contains(player.uniqueId)) return
        val points = GameData.getRewardPoints(finished.size, Constants.POINTS_PER_TASK, Constants.TASK_POINTS_STEP)
        if (points == 1) return
        player.points += points
        player.broadcastPointChangeMessage(points, Component.text("Aufgabe"), name)
    }

    fun makeComponent(player: Player): Component {
        val points = GameData.getRewardPoints(finished.size, Constants.POINTS_PER_TASK, Constants.TASK_POINTS_STEP)
        if (finished.contains(player.uniqueId) || points == 1) return name.decorate(TextDecoration.STRIKETHROUGH)
            .color(NamedTextColor.RED)
        return name.append(text(" (${points}p)")).color(NamedTextColor.GREEN)
    }
}
