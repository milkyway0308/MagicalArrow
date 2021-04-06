package skywolf46.magicalarrow.impl

import org.bukkit.util.Vector
import skywolf46.magicalarrow.MagicalArrow
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import kotlin.random.Random

class ScatteringArrowEffect : AbstractArrowEffect() {
    override fun onArrowShoot(projectile: ProjectileCover) {
        projectile.removeAbility(this)
        // Scattering arrow
        for (x in 0..8) {
            projectile.shootWithout(arrayListOf(MagicalArrow[this]!!),
                Vector(Random.nextDouble(-0.5, 0.5), Random.nextDouble(-0.5, 0.5), Random.nextDouble(-0.5, 0.5)),
                Vector(1, 1, 1))
        }

    }
}