package skywolf46.magicalarrow.command

import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import skywolf46.commandannotation.annotations.autocomplete.AutoComplete
import skywolf46.commandannotation.annotations.autocomplete.AutoCompleteProvider
import skywolf46.commandannotation.annotations.legacy.MinecraftCommand
import skywolf46.commandannotation.annotations.minecraft.PermissionHandler
import skywolf46.commandannotation.data.command.CommandArgument
import skywolf46.commandannotation.util.AutoCompleteUtil
import skywolf46.magicalarrow.MagicalArrow
import skywolf46.refnbt.util.item.getTag

object ArrowCommand {
    @MinecraftCommand("/magicalarrow")
    @AutoComplete("arrowList")
    @PermissionHandler(value = "magicalarrow.admin", message = "§cNot enough permission.")
    @JvmStatic
    fun Player.onArrowAttach(arg: CommandArgument) {
        if (arg.length() < 1) {
            sendMessage("§c/magicalarrow <arrowAbilityName>")
            sendMessage("§7Apply arrow ability to holding arrow.")
            sendMessage("§7If you don't know arrow name, use §eTab Complete§7.")
            return
        }
        if (MagicalArrow[arg[0]] == null) {
            sendMessage("§cUnknown arrow ability.")
            return
        }
        val item = itemInHand
        if (item == null || item.type != Material.ARROW) {
            sendMessage("§cYou must have to hold an arrow.")
            return
        }
        val data = item.getTag()
        val lst = data.getOrCreateList("MagicalArrow")
        for (x in 0 until lst.size()) {
            if(lst.get(x)?.get()?.equals(arg[0]) == true){
                sendMessage("§cArrow already has this ability.")
                return
            }
        }
        lst.add(arg[0])
        sendMessage("§aAbility §e" + arg[0] + " §aadded to arrow.")
    }

    @AutoCompleteProvider("arrowList")
    @JvmStatic
    fun CommandSender.onComplete(arg: MutableList<String>, args: CommandArgument) {
        arg.clear()
        arg.addAll(AutoCompleteUtil.fetchStarting(MagicalArrow.getList(), args.getOrNull(0)))
    }
}