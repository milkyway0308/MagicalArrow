package skywolf46.magicalarrow.abstraction

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.commandannotation.data.command.CommandArgument
import skywolf46.magicalarrow.data.ProjectileCover
import skywolf46.refnbt.impl.collections.CompoundNBTField

abstract class AbstractArrowEffect {
    open fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        ev: ProjectileHitEvent,
    ) {

    }


    open fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        victim: Entity,
        ev: ProjectileHitEvent,
    ) {

    }

    open fun onArrowTick(projectile: ProjectileCover) {

    }


    open fun onArrowShoot(projectile: ProjectileCover) {

    }

    open fun Player.doCommand(ref: CompoundNBTField, arg: CommandArgument){

    }
    open fun requireDestroy() = false

    open fun priority(): Int {
        return 0
    }

}