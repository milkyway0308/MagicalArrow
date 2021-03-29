package skywolf46.magicalarrow.data

import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import skywolf46.magicalarrow.MagicalArrow
import skywolf46.refnbt.util.item.getTag

class ProjectileCover(val item: ItemStack, val player: Player, entity: Entity) {
    var projectile: Entity = entity
        set(value) {
            projectile.remove()
            field = value
        }

    fun shootWithout(ignore: List<String>): ProjectileCover {
        val arrow = player.launchProjectile(Arrow::class.java, projectile.velocity)
        val cover = ProjectileCover(item.clone(), player, arrow)
        with(item.getTag()) {
            arrow.shooter = player
            arrow.pickupStatus = Arrow.PickupStatus.DISALLOWED
            arrow.setMetadata("MagicalArrow", FixedMetadataValue(MagicalArrow.inst, item.clone()))
            val list = getList("MagicalArrow")!!
            for (x in (0 until list.size())) {
                val data = list.get(x)?.get() as String
                if (data in ignore)
                    continue
                MagicalArrow.get(data)
                    ?.onArrowShoot(cover, player)
            }
        }
        return cover
    }
}