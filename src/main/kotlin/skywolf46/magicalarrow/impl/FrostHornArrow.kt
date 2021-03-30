package skywolf46.magicalarrow.impl

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import skywolf46.extrautility.util.schedule
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import java.lang.Long.max

object FrostHornArrow : AbstractArrowEffect() {
    override fun onArrowShoot(projectile: ProjectileCover) {
        projectile.bindTaskBeforeLand(1L) {
            val block = projectile.projectile.location.block
            if (block.type == Material.WATER || block.type == Material.STATIONARY_WATER) {
                stop()
                projectile.removeAbility(this@FrostHornArrow)
                refreeze(block, 10, 5)
            }
        }
    }

    private fun refreeze(bl: Block, limit: Int, delay: Long) {

        arrayOf(BlockFace.WEST, BlockFace.EAST, BlockFace.SOUTH, BlockFace.NORTH).also {
            it.shuffle()
        }.forEachIndexed { index, blockFace ->
            schedule(delay * index) {
                val block = bl.getRelative(blockFace)
                if (block.type != Material.AIR && !block.type.isSolid) {
                    block.type = Material.ICE
                    block.world.playSound(block.location, Sound.BLOCK_GLASS_BREAK, 1f, 1.7f)
                    if (limit > 0) {
                        schedule(max(1, delay - 1)) {
                            refreeze(block, limit - 1, max(1, delay - 1))
                        }
                    }
                }
            }
        }
    }
}