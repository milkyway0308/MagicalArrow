package skywolf46.magicalarrow.abstraction

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.magicalarrow.data.ProjectileCover

abstract class AbstractArrowEffect {
    open fun onArrowCollideBlock(cover: ProjectileCover, collide: Location, bl: Block, pl: Player, ev: ProjectileHitEvent) {

    }


    open fun onArrowCollideEntity(cover: ProjectileCover, collide: Location, victim: Entity, pl: Player, ev: ProjectileHitEvent) {

    }

    open fun onArrowTick(projectile: ProjectileCover, pl: Player) {

    }


    open fun onArrowShoot(projectile: ProjectileCover, pl: Player) {

    }
}