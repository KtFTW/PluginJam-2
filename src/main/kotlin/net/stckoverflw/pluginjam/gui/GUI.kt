package net.stckoverflw.pluginjam.gui

import net.axay.kspigot.gui.GUI
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.stckoverflw.pluginjam.game.points
import net.stckoverflw.pluginjam.util.Constants
import net.stckoverflw.pluginjam.util.mini
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

/*
Helmet, Boots: x1
Chestplate, Leggings: x2

Leather: 25p
Gold: 50p
Iron: 100p
Dia: 200p
Nether: 250p
*/

fun armorGui(): GUI<*> {
    return kSpigotGUI(GUIType.FOUR_BY_NINE) {
        page(1) {
            /*
            * Leather
            * */
            button(
                Slots.RowFourSlotThree,
                itemStack(Material.LEATHER_HELMET) {
                    meta {
                        displayName(mini("<red>Leder-Helm"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>25 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 25)
            }

            button(
                Slots.RowThreeSlotThree,
                itemStack(Material.LEATHER_CHESTPLATE) {
                    meta {
                        displayName(mini("<red>Leder-Brustplatte"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>50 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 50)
            }

            button(
                Slots.RowTwoSlotThree,
                itemStack(Material.LEATHER_LEGGINGS) {
                    meta {
                        displayName(mini("<red>Leder-Hose"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>50 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 50)
            }

            button(
                Slots.RowOneSlotThree,
                itemStack(Material.LEATHER_BOOTS) {
                    meta {
                        displayName(mini("<red>Leder-Schuhe"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>25 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 25)
            }


            /*
            * Gold
            * */
            button(
                Slots.RowFourSlotFour,
                itemStack(Material.GOLDEN_HELMET) {
                    meta {
                        displayName(mini("<gold>Gold-Helm"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>50 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 50)
            }

            button(
                Slots.RowThreeSlotFour,
                itemStack(Material.GOLDEN_CHESTPLATE) {
                    meta {
                        displayName(mini("<gold>Gold-Brustplatte"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>100 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 100)
            }

            button(
                Slots.RowTwoSlotFour,
                itemStack(Material.GOLDEN_LEGGINGS) {
                    meta {
                        displayName(mini("<gold>Gold-Hose"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>100 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 100)
            }

            button(
                Slots.RowOneSlotFour,
                itemStack(Material.GOLDEN_BOOTS) {
                    meta {
                        displayName(mini("<gold>Gold-Schuhe"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>50 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 50)
            }


            /*
            * Iron
            * */
            button(
                Slots.RowFourSlotFive,
                itemStack(Material.IRON_HELMET) {
                    meta {
                        displayName(mini("<grey>Eisen-Helm"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>100 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 100)
            }

            button(
                Slots.RowThreeSlotFive,
                itemStack(Material.IRON_CHESTPLATE) {
                    meta {
                        displayName(mini("<grey>Eisen-Brustplatte"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>200 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 200)
            }

            button(
                Slots.RowTwoSlotFive,
                itemStack(Material.IRON_LEGGINGS) {
                    meta {
                        displayName(mini("<grey>Eisen-Hose"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>200 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 200)
            }

            button(
                Slots.RowOneSlotFive,
                itemStack(Material.IRON_BOOTS) {
                    meta {
                        displayName(mini("<grey>Eisen-Schuhe"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>100 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 100)
            }


            /*
            * Diamond
            * */
            button(
                Slots.RowFourSlotSix,
                itemStack(Material.DIAMOND_HELMET) {
                    meta {
                        displayName(mini("<aqua>Diamant-Helm"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>200 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 200)
            }

            button(
                Slots.RowThreeSlotSix,
                itemStack(Material.DIAMOND_CHESTPLATE) {
                    meta {
                        displayName(mini("<aqua>Diamant-Brustplatte"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>400 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 400)
            }

            button(
                Slots.RowTwoSlotSix,
                itemStack(Material.DIAMOND_LEGGINGS) {
                    meta {
                        displayName(mini("<aqua>Diamant-Hose"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>400 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 400)
            }

            button(
                Slots.RowOneSlotSix,
                itemStack(Material.DIAMOND_BOOTS) {
                    meta {
                        displayName(mini("<aqua>Diamant-Schuhe"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>200 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 200)
            }

            /*
            * Netherite
            * */
            button(
                Slots.RowFourSlotSeven,
                itemStack(Material.NETHERITE_HELMET) {
                    meta {
                        displayName(mini("<dark_grey>Netherite-Helm"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>250 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 250)
            }

            button(
                Slots.RowThreeSlotSeven,
                itemStack(Material.NETHERITE_CHESTPLATE) {
                    meta {
                        displayName(mini("<dark_grey>Netherite-Brustplatte"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>500 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 500)
            }

            button(
                Slots.RowTwoSlotSeven,
                itemStack(Material.NETHERITE_LEGGINGS) {
                    meta {
                        displayName(mini("<dark_grey>Netherite-Hose"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>500 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 500)
            }

            button(
                Slots.RowOneSlotSeven,
                itemStack(Material.NETHERITE_BOOTS) {
                    meta {
                        displayName(mini("<dark_grey>Netherite-Schuhe"))

                        setLore {
                            +mini("<green>Kaufe dieses Item durch einen Klick.")
                            +mini("<gold>Preis: <bold>250 Punkte")
                        }
                    }
                }
            ) {
                it.player.sell(it.bukkitEvent.currentItem?.type, 250)
            }
        }
    }
}

private fun Player.sell(material: Material?, price: Int) {
    if(material == null) return

    if(points < price) {
        sendMessage(mini("<red>Dafür hast du nicht genug Punkte!"))
        return
    }

    points -= price

    val item = itemStack(material) {
        meta {
            persistentDataContainer.set(Constants.ARMOR_KEY, PersistentDataType.BYTE, 1.toByte())
        }
    }

    if(inventory.addItem(item).isNotEmpty()) {
        world.dropItemNaturally(location, item)
    }
}
