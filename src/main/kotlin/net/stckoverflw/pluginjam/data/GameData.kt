package net.stckoverflw.pluginjam.data

import net.axay.kspigot.event.listen
import net.axay.kspigot.event.unregister
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.runnables.task
import net.stckoverflw.pluginjam.scoreboard.GameScoreboard
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.Constants
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.UUID
import kotlin.math.roundToInt

var Player.points: Int
    get() = playerPoints[uniqueId] ?: 0
    set(value) {
        val milestone = Constants.MAX_POINTS * Constants.MILESTONE_BROADCAST_PERCENTAGE
        if (points % milestone > value % milestone) {
            broadcast("$name ist bei [${location.blockX}, ${location.blockY}, ${location.blockZ}]")
        }
        playerPoints[uniqueId] = value
    }

fun Player.handleDeath() {
    if (!Timer.isRunning()) return
    killer?.points = (killer?.points ?: 0).plus(points * Constants.KILL_POINT_GAIN_PERCENTAGE).roundToInt()
    points = points.times(Constants.DEATH_POINT_LOSS_PERCENTAGE).roundToInt()
    val listener = listen<EntityDamageByEntityEvent> {
        if (it.entity == this && it.damager is Player) {
            it.isCancelled = true
        }
    }
    task(
        sync = false,
        delay = 60 * 20,
        howOften = 1
    ) {
        listener.unregister()
    }
}

private val uniqueMaterials = hashMapOf<Material, ArrayList<UUID>>()
private val uniqueAnimals = hashMapOf<EntityType, ArrayList<UUID>>()
private val uniqueAdvancements = hashMapOf<NamespacedKey, ArrayList<UUID>>()
private val playerPoints = hashMapOf<UUID, Int>()

object GameData {

    fun handleMaterialPickup(player: Player, material: Material) {
        var list = uniqueMaterials[material]
        val points = getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_MATERIAL, Constants.MATERIAL_POINTS_STEP)
        if (points <= 0) return
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueMaterials[material] = list
        addPoints(player, points)
    }

    fun handleAnimalKill(player: Player, entityType: EntityType) {
        var list = uniqueAnimals[entityType]
        val points = getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_ANIMAL, Constants.ANIMAL_POINTS_STEP)
        if (points <= 0) return
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueAnimals[entityType] = list
        addPoints(player, points)
    }

    fun handleAdvancement(player: Player, advancement: NamespacedKey) {
        var list = uniqueAdvancements[advancement]
        val points =
            getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_ADVANCEMENT, Constants.ADVANCEMENT_POINTS_STEP)
        if (points <= 0) return
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueAdvancements[advancement] = list
        addPoints(player, points)
    }

    private fun addPoints(player: Player, points: Int) {
        player.points = playerPoints[player.uniqueId] ?: 0 + 2
        player.sendMessage("Du hast $points Punkte erhalten.")
        GameScoreboard.setScore(player, points)
    }

    private fun getRewardPoints(step: Int, basePoints: Int, stepPoints: Int): Int {
        return basePoints - (step * stepPoints)
    }

}
