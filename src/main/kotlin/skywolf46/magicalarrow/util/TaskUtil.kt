package skywolf46.magicalarrow.util

import org.bukkit.Bukkit
import skywolf46.extrautility.util.scheduleRepeat
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

fun selfCancelRepeat(delay: Long, task: (cancelled: AtomicBoolean) -> Unit) {
    val cur = AtomicInteger(0)
    val cancelled = AtomicBoolean(false)
    cur.set(scheduleRepeat(0L, delay) {
        task(cancelled)
        if (cancelled.get()) {
            Bukkit.getScheduler().cancelTask(cur.get())
        }
    })
}