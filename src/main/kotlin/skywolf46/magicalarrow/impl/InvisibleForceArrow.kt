package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.extrautility.util.schedule
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

class InvisibleForceArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        ev: ProjectileHitEvent,
    ) {
        schedule(4L) {
            cover.projectile.world.getNearbyEntities(collide, 3.0, 3.0, 3.0).forEach {
                it.velocity = it.location.toVector().subtract(
                    cover.projectile.location.toVector()
                )
            }
        }
    }
}