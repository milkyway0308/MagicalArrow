package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.extrautility.util.schedule
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

class TImeStopArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        ev: ProjectileHitEvent,
    ) {
        schedule(0L) {
            collide.world.playSound(collide, Sound.BLOCK_TRIPWIRE_CLICK_OFF, 1.0f, 2.0f)
        }
    }
}