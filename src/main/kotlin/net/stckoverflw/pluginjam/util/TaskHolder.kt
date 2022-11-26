package net.stckoverflw.pluginjam.util

import net.axay.kspigot.runnables.KSpigotRunnable
interface TaskHolder {
    val tasks: MutableList<KSpigotRunnable>

    fun addTask(task: KSpigotRunnable) {
        tasks.add(task)
    }

    fun removeTask(task: KSpigotRunnable) {
        tasks.remove(task)
    }

    fun removeAllTasks() {
        tasks.forEach { it.cancel() }
    }
}
