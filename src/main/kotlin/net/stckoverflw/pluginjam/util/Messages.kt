package net.stckoverflw.pluginjam.util

import net.axay.kspigot.chat.literalText
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minecraft.core.HolderSet.Named
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlin.math.absoluteValue

fun mini(string: String) = MiniMessage.miniMessage().deserialize(string)
fun text(string: String) = Component.text(string)
fun text(string: String, color: TextColor) = Component.text(string, color)

//[TYPE] PLAYER gewann/verlor i (durch )
fun Player.broadcastPointChangeMessage(points: Int, type: Component, element: Component?) {
    val pointsComponent = if (points >= 0) Component.text("gewann $points Punkte", NamedTextColor.GREEN)
    else Component.text("verlor ${points.absoluteValue} Punkte", NamedTextColor.RED)

    val component = literalText {
        text("[") {
            color = NamedTextColor.GRAY
        }
        component(type) {
            color = NamedTextColor.YELLOW
        }
        text("] ") {
            color = NamedTextColor.GRAY
        }
        text("$name ") {
            color = NamedTextColor.AQUA
        }
        component(pointsComponent)
        if (element != null) {
            text(" (") {
                color = NamedTextColor.GRAY
            }
            text("durch ") {
                color = NamedTextColor.GREEN
            }
            component(element) {
                color = NamedTextColor.YELLOW
            }
            text(")") {
                color = NamedTextColor.GRAY
            }
        }
    }

    Bukkit.broadcast(component)
}
