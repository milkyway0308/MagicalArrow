package skywolf46.magicalarrow.listener

import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import skywolf46.extrautility.util.setValue
import skywolf46.magicalarrow.MagicalArrow
import skywolf46.magicalarrow.data.ProjectileCover
import skywolf46.refnbt.util.item.getTag

class ProjectileListener : Listener {
    @EventHandler
    fun EntityShootBowEvent.onEvent() {
        if (entity is Player) {
            val arrowItem: ItemStack? = (entity as Player).inventory.find {
                return@find it != null && it.type == Material.ARROW
            }
            arrowItem?.let {
                with(arrowItem.getTag()) {
                    if (has("MagicalArrow")) {
                        isCancelled = true
                        val arrow = entity.launchProjectile(Arrow::class.java, projectile.velocity)
                        projectile.remove()
                        arrow.shooter = entity
                        arrow.pickupStatus = Arrow.PickupStatus.DISALLOWED
                        val cover = ProjectileCover(arrowItem.clone(), entity as Player, arrow)
                        arrow.setValue("MagicalArrow", cover)
                        val list = getList("MagicalArrow")!!
                        for (x in (0 until list.size())) {
                            MagicalArrow.get(list.get(x)?.get() as String)
                                ?.onArrowShoot(cover)
                        }
                        if (arrowItem.amount > 1)
                            arrowItem.amount -= 1
                        else
                            arrowItem.type = Material.AIR
                    }
                }
            }
        }
    }

    @EventHandler
    fun ProjectileHitEvent.onEvent() {
        if (entity.hasMetadata("MagicalArrow")) {
            val item = entity.getMetadata("MagicalArrow")[0].value() as ProjectileCover
            with(item.item.getTag()) {
                val list = getList("MagicalArrow")!!
                if (hitBlock != null) {
                    for (x in (0 until list.size())) {
                        if(x >= list.size())
                            break
                        MagicalArrow[list.get(x)?.get() as String]
                            ?.onArrowCollideBlock(item,
                                entity.location, hitBlock!!,
                                this@onEvent)
                    }
                } else if (hitEntity != null) {
                    for (x in (0 until list.size())) {
                        MagicalArrow.get(list.get(x)?.get() as String)
                            ?.onArrowCollideEntity(item,
                                entity.location, hitEntity!!,
                                this@onEvent)
                    }
                }
            }
        }
    }
}