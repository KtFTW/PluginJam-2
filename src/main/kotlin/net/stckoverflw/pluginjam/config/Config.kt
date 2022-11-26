package net.stckoverflw.pluginjam.config

import net.axay.kspigot.main.KSpigotMainInstance

object Config {
    private val configuration = KSpigotMainInstance.config

    var reset: Boolean
        get() {
            return configuration.getBoolean("reset", false)
        }

        set(value) {
            configuration.set("reset", value)
            KSpigotMainInstance.saveConfig()
        }
}
