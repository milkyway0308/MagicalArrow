package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

class ExplosiveArrowEffect : AbstractArrowEffect(){
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        pl: Player,
        ev: ProjectileHitEvent
    ) {
        cover.projectile.remove()
        collide.world.createExplosion(
            collide, 0.5f, false
        )
    }

    override fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        victim: Entity,
        pl: Player,
        ev: ProjectileHitEvent
    ) {
        cover.projectile.remove()
        collide.world.createExplosion(
            collide, 0.5f, false
        )
    }
}