package net.stckoverflw.pluginjam.timer

import net.axay.kspigot.chat.literalText
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.game.GameData
import net.stckoverflw.pluginjam.util.mini
import kotlin.time.Duration.Companion.seconds

object Timer {

    private var color = NamedTextColor.GOLD
    private var initialized = false
    private var running = false
    private var time: Long = 0

    operator fun invoke() {
        require(!initialized) { "Timer has been initialized already" }
        initialized = true

        task(
            sync = false,
            delay = 0,
            period = 20
        ) {
            if (running) {
                time++
            }

            if(time >= 600) {
                task(sync = true) {
                    GameData.handleWin()
                }
                broadcastTimer(mini("<red><bold>Das Spiel ist beendet."))
                return@task
            }
            broadcastTimer(formatTime())
        }
    }

    fun isRunning() = running

    fun start() {
        running = true
    }

    fun stop() {
        running = false
    }

    fun reset() {
        time = 0
    }

    private fun broadcastTimer(component: Component) {
        onlinePlayers.forEach {
            it.sendActionBar(
                literalText {
                    component(component)
                    color = this@Timer.color
                    bold = true
                }
            )
        }
    }

    private fun formatTime(): Component {
        time.seconds.toComponents { days, hours, minutes, seconds, _ ->
            return literalText {
                if (days > 0) {
                    text("${days}d ")
                }
                if (hours > 0) {
                    text("${hours}h ")
                }
                if (minutes > 0) {
                    text("${minutes}m ")
                }
                if (seconds > 0) {
                    text("${seconds}s")
                }
            }
        }
    }
}
