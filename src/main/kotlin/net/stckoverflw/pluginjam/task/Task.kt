package net.stckoverflw.pluginjam.task

import net.axay.kspigot.runnables.KSpigotRunnable
import net.kyori.adventure.text.Component
import net.stckoverflw.pluginjam.util.ListenerHolder
import net.stckoverflw.pluginjam.util.TaskHolder
import org.bukkit.entity.Player
import org.bukkit.event.Listener

abstract class Task(name: Component): ListenerHolder, TaskHolder {
    override val listeners: MutableList<Listener> = mutableListOf()
    override val tasks: MutableList<KSpigotRunnable> = mutableListOf()

    abstract fun register()
    fun unregister() {
        unregisterAllListeners()
        removeAllTasks()
    }

    fun finish(player: Player) {

    }
}
