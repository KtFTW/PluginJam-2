package net.stckoverflw.pluginjam.util

import net.axay.kspigot.extensions.bukkit.dispatchCommand
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.server
import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.KSpigotRunnable
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.config.Config
import org.bukkit.Bukkit
import kotlin.io.path.div

object WorldUtil {

    fun initializeReset() {
        var seconds = 10

        task(
            true,
            0,
            20,
            howOften = 11
        ) {
            if (seconds == 0) {
                Config.reset = true

                onlinePlayers.forEach {
                    it.kick(prefix.append(Component.text("Die Welten werden zurückgesetzt!").color(NamedTextColor.RED)))
                }

                server.consoleSender.dispatchCommand("restart")

                return@task
            }

            Bukkit.broadcast(prefix.append(mini("<red>Der Server wird in $seconds Sekunden neugestartet!")))

            seconds --
        }
    }

    fun resetWorlds() {
        Config.reset = false
        val paths = listOf(
            Bukkit.getWorldContainer().toPath() / "world",
            Bukkit.getWorldContainer().toPath() / "world_nether",
            Bukkit.getWorldContainer().toPath() / "world_the_end"
        )
        paths.forEach { it.toFile().deleteRecursively() }

        val requiredFolders = listOf("data", "datapacks", "playerdata", "poi", "region")
        requiredFolders.forEach { folder ->
            paths.forEach {
                (it / folder).toFile().mkdirs()
            }
        }
    }
}
