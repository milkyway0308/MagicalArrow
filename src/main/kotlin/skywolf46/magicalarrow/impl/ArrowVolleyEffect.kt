package skywolf46.magicalarrow.impl

import org.bukkit.util.Vector
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import kotlin.random.Random

class ArrowVolleyEffect : AbstractArrowEffect() {

    override fun onArrowShoot(projectile: ProjectileCover) {
        projectile.bindTaskBeforeLand(1L) {
            // Volley!
            if (projectile.projectile.velocity.y < 0) {
                stop()
                projectile.removeAbility(this@ArrowVolleyEffect)
                for (i in 0..15)
                    projectile.spawnWithout(arrayListOf(), projectile.projectile.location,
                        Vector(Random.nextDouble(-0.15, 0.15),
                            projectile.projectile.velocity.y,
                            Random.nextDouble(-0.15, 0.15)),
                        Vector(1, 1, 1))
//                projectile.projectile.remove()
            }
        }
    }
}