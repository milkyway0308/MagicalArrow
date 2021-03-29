package skywolf46.magicalarrow.impl

import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Ocelot
import org.bukkit.entity.Player
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.data.ProjectileCover
import kotlin.random.Random

class NekoArrowEffect : AbstractArrowEffect(){
    override fun onArrowShoot(projectile: ProjectileCover, pl: Player) {
        val vector = projectile.projectile.velocity
        projectile.projectile = projectile.projectile.world.spawnEntity(
            projectile.projectile.location,
            EntityType.OCELOT
        )
        val ocelot = projectile.projectile as Ocelot
        ocelot.catType = Ocelot.Type.values()[Random.nextInt(Ocelot.Type.values().size-1)]
        ocelot.owner = pl
        ocelot.velocity = vector.normalize()

    }
}