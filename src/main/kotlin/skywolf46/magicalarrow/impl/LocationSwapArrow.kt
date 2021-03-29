package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.extrautility.util.schedule
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

class LocationSwapArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        pl: Player,
        ev: ProjectileHitEvent,
    ) {
        val loc = cover.projectile.location
        cover.projectile.teleport(pl.location)
        pl.teleport(loc)
        pl.playSound(pl.location, Sound.ENTITY_ENDERMEN_TELEPORT, 0.8f, 1.4f)
        if (cover.projectile is Arrow)
            schedule(40) {
                cover.projectile.remove()
            }
    }

    override fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        victim: Entity,
        pl: Player,
        ev: ProjectileHitEvent
    ) {
        val loc = cover.projectile.location
        cover.projectile.teleport(pl.location)
        pl.teleport(loc)
        pl.playSound(pl.location, Sound.ENTITY_ENDERMEN_TELEPORT, 0.8f, 1.4f)
        if (cover.projectile is Arrow)
            schedule(40) {
                cover.projectile.remove()
            }
    }

}