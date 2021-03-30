package skywolf46.magicalarrow.data

import skywolf46.extrautility.data.AdvancedSchedulerData

data class ProjectileScheduler(val projectile: ProjectileCover, val scheduler: AdvancedSchedulerData) {
    fun stop() {
        scheduler.stop()
    }
}