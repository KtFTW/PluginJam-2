package net.stckoverflw.pluginjam.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player


val Player.nameComponent: Component
    get() = name().color(NamedTextColor.GREEN)

//[TYPE] -i/+i (ELEMENT)
fun Player.broadcastPointChangeMessage(points: Int, type: Component, element: Component?) {
    val pointsComponent = if (points > 0 ) Component.text("-").append(Component.text(points)).color(NamedTextColor.GREEN)
            else Component.text("-").append(Component.text(points)).color(NamedTextColor.RED)

    var component = Component.text("[", NamedTextColor.GREEN)
        .append(type.color(NamedTextColor.YELLOW))
        .append(Component.text("]", NamedTextColor.GREEN))
        .append(Component.space())
        .append(pointsComponent)

    if (element != null) {
        component = component
            .append(Component.space())
            .append(Component.text("(", NamedTextColor.GREEN))
            .append(element.color(NamedTextColor.YELLOW))
            .append(Component.text(")", NamedTextColor.GREEN))
    }

    Bukkit.broadcast(component)
}
