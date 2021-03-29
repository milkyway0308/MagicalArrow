package skywolf46.magicalarrow.data

import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

class ProjectileCover(val item: ItemStack, entity: Entity) {
    var projectile: Entity = entity
        set(value) {
            projectile.remove()
            field = value
        }

}