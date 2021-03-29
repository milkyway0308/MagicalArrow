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
                        arrow.setMetadata("MagicalArrow", FixedMetadataValue(MagicalArrow.inst, arrowItem.clone()))
                        val list = getList("MagicalArrow")!!
                        for (x in (0 until list.size())) {
                            MagicalArrow.get(list.get(x)?.get() as String)
                                ?.onArrowShoot(ProjectileCover(arrowItem.clone(), entity as Player, arrow), entity as Player)
                            if (arrowItem.amount > 1)
                                arrowItem.amount -= 1
                            else
                                arrowItem.type = Material.AIR
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun ProjectileHitEvent.onEvent() {
        if (entity.hasMetadata("MagicalArrow")) {
            val item = entity.getMetadata("MagicalArrow")[0].value() as ItemStack
            with(item.getTag()) {
                val list = getList("MagicalArrow")!!
                if (hitBlock != null) {
                    for (x in (0 until list.size())) {
                        MagicalArrow.get(list.get(x)?.get() as String)
                            ?.onArrowCollideBlock(ProjectileCover(item.clone(), entity.shooter as Player,entity),
                                entity.location, hitBlock!!,
                                entity.shooter as Player,
                                this@onEvent)
                    }
                } else if (hitEntity != null) {
                    for (x in (0 until list.size())) {
                        MagicalArrow.get(list.get(x)?.get() as String)
                            ?.onArrowCollideEntity(ProjectileCover(item.clone(), entity.shooter as Player,entity),
                                entity.location, hitEntity!!,
                                entity.shooter as Player,
                                this@onEvent)
                    }
                }
            }
        }
    }
}