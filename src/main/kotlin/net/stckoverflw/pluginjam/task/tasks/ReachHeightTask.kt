package net.stckoverflw.pluginjam.task.tasks

import net.axay.kspigot.runnables.task
import net.stckoverflw.pluginjam.game.gamePlayers
import net.stckoverflw.pluginjam.task.Task
import net.stckoverflw.pluginjam.util.text

class ReachHeightTask(private val height: Int) : Task(text("Stehe auf Höhe $height")) {
    override fun register() {
        addTask(task(sync = true, delay = 0, 20) {
            val player = gamePlayers.firstOrNull { it.location.y.toInt() == height }
            if (player != null) finish(player)
        }!!)
    }
}
