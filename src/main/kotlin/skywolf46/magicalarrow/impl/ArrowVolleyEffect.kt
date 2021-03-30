package skywolf46.magicalarrow.impl

import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover

class ArrowVolleyEffect : AbstractArrowEffect() {
    override fun onArrowTick(projectile: ProjectileCover) {
        if (projectile.projectile.velocity.y < 0) {
            // Arrow volley!
        }
    }
}