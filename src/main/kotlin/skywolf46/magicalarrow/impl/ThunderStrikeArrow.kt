package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.extrautility.util.schedule
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import java.util.concurrent.atomic.AtomicBoolean

class ThunderStrikeArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        pl: Player,
        ev: ProjectileHitEvent,
    ) {
        cover.projectile.remove()
        collide.world.strikeLightning(collide)
    }

    override fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        victim: Entity,
        pl: Player,
        ev: ProjectileHitEvent
    ) {
        cover.projectile.remove()
        collide.world.strikeLightning(collide)
    }
}