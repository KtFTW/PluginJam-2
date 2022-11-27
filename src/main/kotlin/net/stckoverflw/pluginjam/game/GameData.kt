package net.stckoverflw.pluginjam.game

import net.axay.kspigot.chat.literalText
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
import net.stckoverflw.pluginjam.util.WorldUtil
import net.stckoverflw.pluginjam.util.broadcastPointChangeMessage
import net.stckoverflw.pluginjam.util.mini
import org.bukkit.Bukkit
import org.bukkit.GameMode
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
import kotlin.random.Random

val gamePlayers: List<Player>
    get() = onlinePlayers.filter { it.gameMode == GameMode.SURVIVAL }

var Player.points: Int
    get() = playerPoints[uniqueId] ?: 0
    set(value) {
        if (points >= Constants.MAX_POINTS) {
            GameData.handleWin()
        } else {
            //Funktioniert nicht und keine Zeit
  /*          val milestone = Constants.MAX_POINTS * Constants.MILESTONE_BROADCAST_PERCENTAGE
            if ((value % milestone).roundToInt() > (points % milestone).roundToInt()) {
                broadcast(prefix.append(mini("<aqua>$name <green>ist bei <grey>[<yellow>${location.blockX}, ${location.blockY}, ${location.blockZ}<grey>]")))
            }*/
            removePotionEffect(PotionEffectType.GLOWING)
            removePotionEffect(PotionEffectType.SLOW_DIGGING)
            if (value >= Constants.MAX_POINTS * 0.25) {
                addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 99999, 1))
                addPotionEffect(
                    PotionEffect(
                        PotionEffectType.SLOW_DIGGING,
                        99999,
                        (value / Constants.MAX_POINTS / 0.1).roundToInt()
                    )
                )
            }
            if (value >= Constants.MAX_POINTS * 0.5) {
                if (Random.nextInt(5) == 1) {
                    addPotionEffect(
                        PotionEffect(
                            PotionEffectType.LEVITATION,
                            (value / Constants.MAX_POINTS / 0.2).roundToInt(),
                            1
                        )
                    )
                }
            }
        }
        playerPoints[uniqueId] = value
        GameScoreboard.setScore(this, value)
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
private val uniqueLevels = hashMapOf<Int, ArrayList<UUID>>()
val playerPoints = hashMapOf<UUID, Int>()

fun Player.ping() = playSound(location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)

object GameData {

    fun handleLevelChange(player: Player) {
        var list = uniqueLevels[player.level]
        if (list?.contains(player.uniqueId) == true) return
        if (list == null) list = arrayListOf()
        list.add(player.uniqueId)
        uniqueLevels[player.level] = list

        player.points += player.level
        player.ping()
        player.broadcastPointChangeMessage(
            player.level,
            Component.text("XP"),
            null
        )
    }

    fun handleMaterialPickup(player: Player, material: Material) {
        var list = uniqueMaterials[material]
        if (list?.contains(player.uniqueId) == true) return

        val points = getRewardPoints(list?.size ?: 0, Constants.POINTS_PER_MATERIAL, Constants.MATERIAL_POINTS_STEP)
        if (list == null) list = arrayListOf()

        list.add(player.uniqueId)
        uniqueMaterials[material] = list

        player.points += points
        player.ping()
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
        player.ping()
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
        uniqueAdvancements[advancement.key] = list

        player.points += points
        player.ping()
        player.broadcastPointChangeMessage(points, Component.text("Advancement"), advancement.display?.title())
    }

    fun handleWin() {
        if (! Timer.isRunning()) return

        Timer.stop()
        val winner = playerPoints.entries.filter { Bukkit.getPlayer(it.key)?.isOnline == true }.maxByOrNull { it.value }
        val winningPlayer = if (winner != null) Bukkit.getPlayer(winner.key) else null

        if (winningPlayer != null) {
            onlinePlayers.forEach {
                it.title(
                    winningPlayer.name().color(NamedTextColor.GREEN),
                    Component.text("hat das Spiel gewonnen!", NamedTextColor.GREEN), Duration.ofMillis(250),
                    Duration.ofSeconds(5), Duration.ofMillis(250)
                )
            }
            Bukkit.broadcast(mini("<green>${winningPlayer.name} hat das Spiel mit <red>${winner !!.value} <green>Punkten gewonnen"))
        }
        Bukkit.broadcast(mini("<green>Das Spiel wurde beendet."))

        onlinePlayers.forEach {
            it.activePotionEffects.forEach { potionEffect ->
                it.removePotionEffect(potionEffect.type)
            }
            val placement = playerPoints.filter { points -> points.value > it.points }.count() + 1
            it.sendMessage(
                literalText("Dein Platz: $placement") {
                    color = NamedTextColor.GREEN
                }
            )
            it.teleport(worlds[0].spawnLocation)
        }

        winningPlayer?.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 9999, 1))

        task(
            true,
            0,
            15,
            10
        ) {
            worlds[0].spawnEntity(worlds[0].spawnLocation, EntityType.FIREWORK)
        }

        WorldUtil.initializeReset()
    }

    fun getRewardPoints(step: Int, basePoints: Int, stepPoints: Int): Int {
        val points = basePoints - (step * stepPoints)
        return if (points <= 0) 1 else points
    }
}
