package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.metadata.FixedMetadataValue
import skywolf46.extrautility.util.schedule
import skywolf46.magicalarrow.MagicalArrow.Companion.inst
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

object FreezingArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        ev: ProjectileHitEvent,
    ) {

    }

    override fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        victim: Entity,
        ev: ProjectileHitEvent,
    ) {
        cover.projectile.remove()
        victim.world.playSound(victim.location, Sound.BLOCK_GLASS_BREAK, 5f, 1.7f)
        val converterd = mutableListOf<Block>()
        for (x in -1..1) {
            for (y in 0..2) {
                for (z in -1..1) {
                    if (x == 0 && y == 1 && z == 0)
                        continue
                    with(victim.location.add(x.toDouble(), y.toDouble(), z.toDouble()).block) {
                        if (!type.isSolid) {
                            converterd.add(this)
                            setMetadata("Last Block", FixedMetadataValue(inst, type))
                            type = Material.PACKED_ICE
                        }
                    }
                }
            }
        }

        schedule(40L) {
            converterd.forEach {
                if (it.hasMetadata("Last Block")) {
                    it.type = it.getMetadata("Last Block")[0].value() as Material
                    it.removeMetadata("Last Block", inst)
                }
            }
        }

    }

    @EventHandler
    fun BlockBreakEvent.onEvent() {
        if (block.hasMetadata("Last Block")) {
            isDropItems = false
            block.removeMetadata("Last Block", inst)
        }
    }

}