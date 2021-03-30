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
        ev: ProjectileHitEvent,
    ) {
        val loc = cover.projectile.location
        cover.projectile.teleport(cover.player.location)
        cover.player.teleport(loc)
        cover.player.playSound(cover.player.location, Sound.ENTITY_ENDERMEN_TELEPORT, 0.8f, 1.4f)
        cover.removeAbility(this)
    }

    override fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        victim: Entity,
        ev: ProjectileHitEvent
    ) {
        val loc = cover.projectile.location
        cover.projectile.teleport(cover.player.location)
        cover.player.teleport(loc)
        cover.player.playSound(cover.player.location, Sound.ENTITY_ENDERMEN_TELEPORT, 0.8f, 1.4f)
        cover.removeAbility(this)

    }

}