package skywolf46.magicalarrow.impl

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.material.Redstone
import skywolf46.extrautility.util.scheduleRepeat
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import java.util.concurrent.atomic.AtomicInteger
import kotlin.experimental.and

class RedstonePowerEffect : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        pl: Player,
        ev: ProjectileHitEvent,
    ) {
        val data = AtomicInteger(0)
        val expireOn = AtomicInteger(100)
        data.set(
            scheduleRepeat(1L) {
                if (!cover.projectile.isValid) {
                    Bukkit.getScheduler().cancelTask(data.get())
                    updateRedstoneNaturally(bl.getRelative(BlockFace.UP))
                    return@scheduleRepeat
                }
                if (expireOn.decrementAndGet() <= 0) {
                    cover.projectile.remove()
                }
                for (i in 0..6)
                    collide.world.spawnParticle(
                        Particle.REDSTONE,
                        cover.projectile.location.clone(), 0,
                        0.0, 0.0, 0.0
                    )
//                    collide.world.spawnParticle(
//                        Particle.REDSTONE,
//                        cover.projectile.location.clone(), 0,
//                        Random.nextDouble() * 0.005 * (if (Random.nextBoolean()) 1 else -1),
//                        0.6 + Random.nextDouble() * 2,
//                        Random.nextDouble() * 0.005 * (if (Random.nextBoolean()) 1 else -1),
//                    )
                val bx = bl.getRelative(BlockFace.UP)
                if (bx.state is Redstone) {
                    updateRedstone(bx)
                }
            }
        )
    }

    fun updateRedstone(bl: Block) {
        val bx = bl.state
        if (bx.data is Redstone) {
            val dat = bx.data
            dat.data = dat.data.and(0x8)
            bx.data = dat
            bx.update(true, false)
        }
        arrayOf(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST,
            BlockFace.NORTH, BlockFace.WEST)
            .forEach {
//                val state = RedstoneWire(Material.REDSTONE, 15)
                val st = bl.getRelative(it).state
                if (st.data is Redstone) {
                    val dat = st.data
                    dat.data = dat.data.and(0x8)
                    st.data = dat
                    st.update(true, false)
                }
            }

    }


    fun updateRedstoneNaturally(bl: Block) {
        bl.state.update(false, true)

        arrayOf(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST,
            BlockFace.NORTH, BlockFace.WEST)
            .forEach {
                bl.getRelative(it).state.update(false, true)
            }

    }
}