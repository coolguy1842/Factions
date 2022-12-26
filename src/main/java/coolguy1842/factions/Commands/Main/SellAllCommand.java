package coolguy1842.factions.Commands.Main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum SellAllCommandMessages {
    NOITEM,
    INVALIDITEM,

    SUCCESS
}

public class SellAllCommand implements CommandExecutor {
    private static Component[] commandMessages = {
        Component.text("You are not holding an item."),
        Component.text("That item is not sellable."),

        Component.text("You have sold "),
    }; 

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player p = (Player)sender;
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);

        ItemStack item = p.getInventory().getItemInMainHand();
        if(item == null || item.getType() == Material.AIR) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SellAllCommandMessages.NOITEM.ordinal()]);
            return true;
        }
        else if(!Globals.itemPrices.containsKey(item.getType())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SellAllCommandMessages.INVALIDITEM.ordinal()]);
            return true;
        }

        int amount = 0;
        for(ItemStack i : p.getInventory().getContents()) {
            if(i == null) continue;

            if(i.getType() == item.getType()) {
                amount += i.getAmount();
            }
        }

        Long itemPrice = Globals.itemPrices.get(item.getType()) * amount;
        player.setMoney(player.getMoney() + itemPrice);

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, 
                                        commandMessages[SellAllCommandMessages.SUCCESS.ordinal()], 
                                        Component.text(amount + "x "),
                                        item.displayName(),
                                        Component.text(" for $" + itemPrice));
                                        
        
        p.getInventory().remove(item.getType());

        return true;
    }

}
