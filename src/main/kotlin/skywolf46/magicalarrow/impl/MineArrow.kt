package skywolf46.magicalarrow.impl

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Damageable
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import skywolf46.extrautility.util.schedule
import skywolf46.extrautility.util.scheduleRepeat
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class MineArrow : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        bl: Block,
        ev: ProjectileHitEvent,
    ) {
        val data = AtomicInteger(0)
        val expireOn = AtomicInteger(100)
        data.set(
            scheduleRepeat(5L) {
                if (!cover.projectile.isValid) {
                    Bukkit.getScheduler().cancelTask(data.get())
                    return@scheduleRepeat
                }

                for (i in 0..6)
                    collide.world.spawnParticle(
                        Particle.SWEEP_ATTACK,
                        cover.projectile.location.clone(), 0,
                        Random.nextDouble() * 0.005 * (if (Random.nextBoolean()) 1 else -1),
                        0.6 + Random.nextDouble() * 2,
                        Random.nextDouble() * 0.005 * (if (Random.nextBoolean()) 1 else -1),
                    )
                for (el in cover.projectile.getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (el is Damageable) {
                        cover.projectile.world.playSound(cover.projectile.location,
                            Sound.BLOCK_IRON_TRAPDOOR_OPEN,
                            0.7f,
                            1.0f)
                        schedule(15L) {
                            cover.projectile.remove()
                            cover.projectile.world.createExplosion(cover.projectile.location, 2.5f, true)
                        }
                        Bukkit.getScheduler().cancelTask(data.get())
                        return@scheduleRepeat
                    }
                }

                if (expireOn.decrementAndGet() <= 0) {
                    cover.projectile.remove()
                }
            }
        )
    }
}