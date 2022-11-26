package net.stckoverflw.pluginjam.game

import com.mojang.datafixers.kinds.Const
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.worlds
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.scoreboard.GameScoreboard
import net.stckoverflw.pluginjam.timer.Timer
import net.stckoverflw.pluginjam.util.Constants
import net.stckoverflw.pluginjam.util.broadcastPointChangeMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.advancement.Advancement
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.time.Duration
import java.util.UUID
import kotlin.math.roundToInt


val gamePlayers: List<Player>
    get() = onlinePlayers.filter { it.gameMode != GameMode.SURVIVAL }
var Player.points: Int
    get() = playerPoints[uniqueId] ?: 0
    set(value) {
        playerPoints[uniqueId] = value
        GameScoreboard.setScore(this, value)

        if(points >= Constants.MAX_POINTS) {
            addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 99999, 1))
            GameData.handleWin()
        } else {
            val milestone = Constants.MAX_POINTS * Constants.MILESTONE_BROADCAST_PERCENTAGE
            if (points % milestone > value % milestone) {
                broadcast("$name ist bei [${location.blockX}, ${location.blockY}, ${location.blockZ}]")
            }
            removePotionEffect(PotionEffectType.GLOWING)
            removePotionEffect(PotionEffectType.SLOW_DIGGING)
            if (value >= Constants.MAX_POINTS * 0.25) {
                addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 99999, 1))
            }
            if (value >= Constants.MAX_POINTS * 0.25) {
                addPotionEffect(
                    PotionEffect(
                        PotionEffectType.SLOW_DIGGING,
                        99999,
                        (value / Constants.MAX_POINTS / 0.1).roundToInt()
                    )
                )
            }
        }
    }

fun Player.handleDeath() {
    if (! Timer.isRunning()) return

    val killer = killer
    if (killer != null) {
        val killerPoints = (points * Constants.KILL_POINT_GAIN_PERCENTAGE).roundToInt()
        killer.points += killerPoints
        killer.playSound(killer.location, Sound.BLOCK_ANVIL_DESTROY, 1f, 1f)
        killer.broadcastPointChangeMessage(
            killerPoints,
            Component.text("Kill"),
            Component.text("Tod von ").append(name())
        )
    }

    val deathPoints = (points * Constants.DEATH_POINT_LOSS_PERCENTAGE).roundToInt()
    val killerComponent = if (killer != null) Component.text("Kill von ").append(killer.name())
    else null

    points -= deathPoints
    broadcastPointChangeMessage(
        - deathPoints,
        Component.text("Tod"), killerComponent
    )
}

private val uniqueMaterials = hashMapOf<Material, ArrayList<UUID>>()
private val uniqueAnimals = hashMapOf<EntityType, ArrayList<UUID>>()
private val uniqueAdvancements = hashMapOf<NamespacedKey, ArrayList<UUID>>()
private val playerPoints = hashMapOf<UUID, Int>()

object GameData {

    fun handleMaterialPickup(player: Player, material: Material) {
        var list = uniqueMaterials[material]
        if (list?.contains(player.uniqueId) == true) return

        val points = getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_MATERIAL, Constants.MATERIAL_POINTS_STEP)
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueMaterials[material] = list
        player.points += points
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
        player.broadcastPointChangeMessage(
            points,
            Component.text("Item"),
            Component.translatable(material.translationKey())
        )
    }

    fun handleAnimalKill(player: Player, entityType: EntityType) {
        var list = uniqueAnimals[entityType]
        if (list?.contains(player.uniqueId) == true) return

        val points = getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_ANIMAL, Constants.ANIMAL_POINTS_STEP)
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueAnimals[entityType] = list
        player.points += points
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
        player.broadcastPointChangeMessage(
            points,
            Component.text("Entity"),
            Component.translatable(entityType.translationKey())
        )
    }

    fun handleAdvancement(player: Player, advancement: Advancement) {
        var list = uniqueAdvancements[advancement.key]
        if (list?.contains(player.uniqueId) == true) return

        val points =
            getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_ADVANCEMENT, Constants.ADVANCEMENT_POINTS_STEP)
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueAdvancements[advancement] = list
        player.points += points
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
        player.broadcastPointChangeMessage(points, Component.text("Advancement"), advancement.display?.title())
    }

    private fun getRewardPoints(step: Int, basePoints: Int, stepPoints: Int): Int {
        val points = basePoints - (step * stepPoints)
        return if (points <= 0) 1 else points
    }

}
