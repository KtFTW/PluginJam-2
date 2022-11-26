package net.stckoverflw.pluginjam.listeners

import com.destroystokyo.paper.MaterialTags
import net.axay.kspigot.event.listen
import net.stckoverflw.pluginjam.game.GameData
import net.stckoverflw.pluginjam.game.handleDeath
import net.stckoverflw.pluginjam.game.points
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.Constants
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import kotlin.random.Random

object GameListeners {
    fun register() {
        listen<PlayerDeathEvent> {
            it.drops.clear()
            it.itemsToKeep.minusAssign(
                it.itemsToKeep
                    .filter { item -> ! MaterialTags.ARMOR.isTagged(item.type) }
                    .filter { _ -> Random.nextInt(Constants.MAX_POINTS * 2) < it.entity.points }.toSet()
            )

            it.entity.handleDeath()
        }

        listen<EntityPickupItemEvent> {
            if (! Timer.isRunning()) return@listen
            if (it.entityType != EntityType.PLAYER) return@listen
            if (MaterialTags.ARMOR.isTagged(it.item.itemStack.type)) {
                if (it.item.itemStack.itemMeta.persistentDataContainer.has(Constants.ARMOR_KEY))
                    return@listen
            }

            GameData.handleMaterialPickup(it.entity as Player, it.item.itemStack.type)
        }

        listen<EntityDeathEvent> {
            if (! Timer.isRunning()) return@listen
            if (it.entity.type == EntityType.PLAYER) return@listen

            val killer = it.entity.killer ?: return@listen

            GameData.handleAnimalKill(killer, it.entity.type)
        }

        listen<PlayerAdvancementDoneEvent> {
            it.message(null)

            if (! Timer.isRunning()) return@listen
            if (it.advancement.key.key.split("/")[0] == "recipes") return@listen

            GameData.handleAdvancement(it.player, it.advancement)
        }

        listen<InventoryClickEvent>(EventPriority.HIGH) {
            if (! Timer.isRunning()) return@listen
            if (it.isCancelled) return@listen
            if (it.currentItem?.let { it1 -> MaterialTags.ARMOR.isTagged(it1.type) } == true) {
                if (it.currentItem?.itemMeta?.persistentDataContainer?.has(Constants.ARMOR_KEY) == true)
                    return@listen
            }
            if (it.whoClicked !is Player || it.currentItem == null) return@listen
            if (it.currentItem !!.type == Material.AIR) return@listen
            GameData.handleMaterialPickup(it.whoClicked as Player, it.currentItem !!.type)
        }

        listen<EntityDamageEvent> {
            if (it.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                || it.cause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
            ) return@listen
            if (it.entity is Player) {
                it.damage *= 1 + (((it.entity as Player).points.toDouble() / Constants.MAX_POINTS) * 3)
            }
        }

        listen<EntityDamageByEntityEvent> {
            if (it.entity is Player && it.damager is Player) {
                if ((it.entity as Player).getStatistic(Statistic.TIME_SINCE_DEATH) <= 60 * 20) {
                    it.isCancelled = true
                }
            }
            if (it.damager is Player && (it.damager as Player).getStatistic(Statistic.TIME_SINCE_DEATH) <= 60 * 20) {
                (it.damager as Player).setStatistic(Statistic.TIME_SINCE_DEATH, 61 * 20)
            }

            if (it.entity is Player) {
                if (it.damager is Player) {
                    it.damage *= 1 + (((it.entity as Player).points.toDouble() / Constants.MAX_POINTS) * 6)
                    return@listen
                } else {
                    it.damage *= 1 + (((it.entity as Player).points.toDouble() / Constants.MAX_POINTS) * 3)
                }
            }
            if (it.damager is Player) {
                it.damage *= 1 - (((it.damager as Player).points.toDouble() / Constants.MAX_POINTS) / 3)
            }
        }
    }
}
