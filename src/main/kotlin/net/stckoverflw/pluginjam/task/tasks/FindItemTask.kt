package net.stckoverflw.pluginjam.task.tasks

import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import net.stckoverflw.pluginjam.task.Task
import net.stckoverflw.pluginjam.util.text
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent



class FindItemTask(private val materialToFind: Material)
    : Task(text("Finde ").append(Component.translatable(materialToFind.translationKey()))) {

    companion object {
        val ITEMS = listOf(Material.IRON_INGOT, Material.ENDER_PEARL, Material.CAKE)
    }
    override fun register() {

        addListener(listen<EntityPickupItemEvent> {
            if (it.entityType != EntityType.PLAYER) return@listen
        })

        addListener(listen<InventoryClickEvent>(EventPriority.LOW) {
            if(it.isCancelled) return@listen
            if (it.whoClicked !is Player || it.currentItem == null) return@listen
                if (it.currentItem !!.type == Material.AIR) return@listen
                handle(it.currentItem!!.type, it.whoClicked as Player)
            })
    }

    fun handle(material: Material, player: Player) {
        if(material != materialToFind) return
        finish(player)
    }
}
