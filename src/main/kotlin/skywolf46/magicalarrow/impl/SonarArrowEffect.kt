package skywolf46.magicalarrow.impl

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import skywolf46.magicalarrow.util.selfCancelRepeat
import java.util.concurrent.atomic.AtomicInteger

class SonarArrowEffect : AbstractArrowEffect() {
    override fun onArrowCollideBlock(
        cover: ProjectileCover,
        collide: Location,
        block: Block,
        pl: Player,
        ev: ProjectileHitEvent,
    ) {
        val expire = AtomicInteger(12)
        selfCancelRepeat(20L) {
            if (!cover.projectile.isValid) {
                it.set(true)
                return@selfCancelRepeat
            }
            for (entity in cover.projectile.getNearbyEntities(5.0, 5.0, 5.0)) {
                if (entity is LivingEntity) {
                    val le = entity as LivingEntity
                    if (!le.hasPotionEffect(PotionEffectType.GLOWING) || le.getPotionEffect(PotionEffectType.GLOWING).duration < 20) {
                        le.removePotionEffect(PotionEffectType.GLOWING)
                        le.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 30, 1))
                    }
                }
            }
            if (expire.decrementAndGet() <= 0) {
                cover.projectile.remove()
                it.set(true)
            } else {
                cover.projectile.world.playSound(
                    cover.projectile.location, Sound.AMBIENT_CAVE, 0.1f, 7.0f
                )
            }

        }
    }

    override fun onArrowCollideEntity(
        cover: ProjectileCover,
        collide: Location,
        entity: Entity,
        pl: Player,
        ev: ProjectileHitEvent,
    ) {
        val expire = AtomicInteger(24)
        selfCancelRepeat(10L) {
            if (!entity.isValid) {
                it.set(true)
                return@selfCancelRepeat
            }
            for (en in cover.projectile.getNearbyEntities(5.0, 5.0, 5.0)) {
                if (en is LivingEntity) {
                    if (!en.hasPotionEffect(PotionEffectType.GLOWING) || en.getPotionEffect(PotionEffectType.GLOWING).duration < 20) {
                        en.removePotionEffect(PotionEffectType.GLOWING)
                        en.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 30, 1))
                    }
                }
            }
            if (expire.decrementAndGet() <= 0) {
                it.set(true)
            } else {
                cover.projectile.world.playSound(
                    cover.projectile.location, Sound.AMBIENT_CAVE, 0.1f, 7.0f
                )
            }

        }
    }
}