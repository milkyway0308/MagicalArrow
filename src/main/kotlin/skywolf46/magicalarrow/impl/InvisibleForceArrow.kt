package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

class InvisibleForceArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        ev: ProjectileHitEvent,
    ) {
        cover.projectile.world.getNearbyEntities(collide, 3.0, 3.0, 3.0).forEach {

        }
    }
}