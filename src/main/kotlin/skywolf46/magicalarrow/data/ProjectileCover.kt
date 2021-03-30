package skywolf46.magicalarrow.data

import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import skywolf46.extrautility.util.cancellableScheduleRepeat
import skywolf46.extrautility.util.setValue
import skywolf46.magicalarrow.MagicalArrow
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.refnbt.util.item.getTag

class ProjectileCover(val item: ItemStack, val player: Player, entity: Entity) {
    internal var crashed: Boolean = false
    var projectile: Entity = entity
        set(value) {
            projectile.remove()
            value.setValue("MagicalArrow", this)
            field = value
        }

    fun shootWithout(ignore: List<String>): ProjectileCover {
        val arrow = player.launchProjectile(Arrow::class.java, projectile.velocity)
        val cover = ProjectileCover(item.clone(), player, arrow)
        arrow.shooter = player
        arrow.pickupStatus = Arrow.PickupStatus.DISALLOWED
        arrow.setValue("MagicalArrow", this)
        with(cover.item.getTag()) {
            val list = getList("MagicalArrow")!!
            for (x in ignore) {
                list.remove(x)
            }
            for (x in (0 until list.size())) {
                MagicalArrow.get(list.get(x)?.get() as String)
                    ?.onArrowShoot(cover)
            }
        }
        return cover
    }

    fun removeAbility(eff: AbstractArrowEffect){
        MagicalArrow[eff]?.let { removeAbility(it) }
    }
    fun removeAbility(str: String) {
        with(item.getTag()) {
            val list = getList("MagicalArrow")!!
            list.remove(str)
        }
    }

    fun bindTaskBeforeLand(delay: Long, task: ProjectileScheduler.() -> Unit) {
        bindTaskBeforeLand(delay, delay, task)
    }

    fun bindTaskBeforeLand(startDelay: Long, delay: Long, task: ProjectileScheduler.() -> Unit) {
        cancellableScheduleRepeat(startDelay, delay) {
            if (!projectile.isValid || crashed || projectile.isOnGround) {
                stop()
                return@cancellableScheduleRepeat
            }
            task(ProjectileScheduler(this@ProjectileCover, this))
        }
    }
}