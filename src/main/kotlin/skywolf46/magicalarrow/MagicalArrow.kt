package skywolf46.magicalarrow

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.commandannotation.CommandAnnotation
import skywolf46.extrautility.util.log
import skywolf46.magicalarrow.abstraction.AbstractArrowEffect
import skywolf46.magicalarrow.impl.*
import skywolf46.magicalarrow.listener.ProjectileListener

class MagicalArrow : JavaPlugin() {
    companion object {
        private val map = HashMap<String, AbstractArrowEffect>()
        lateinit var inst: MagicalArrow
            private set

        fun register(str: String, eff: AbstractArrowEffect) {
            map[str] = eff
        }

        fun get(str: String) = map[str]

        fun getList() : List<String> = ArrayList<String>(map.keys)
    }

    override fun onEnable() {
        inst = this
        CommandAnnotation.init(this)
        log("Initializing MagicalArrow...")
        register("redstone", RedstonePowerEffect())
        register("nekoarrow", NekoArrowEffect())
        register("explosive", ExplosiveArrowEffect())
        register("mine", MineArrow())
        register("swap", LocationSwapArrow())
        Bukkit.getPluginManager().registerEvents(ProjectileListener(), this)
    }
}