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

enum SellCommandMessages {
    NOITEM,
    INVALIDITEM,

    INVALIDAMOUNT,
    BADAMOUNT,

    SUCCESS
}

public class SellCommand implements CommandExecutor {
    private static Component[] commandMessages = {
        Component.text("You are not holding an item."),
        Component.text("That item is not sellable."),

        Component.text("Invalid amount"),
        Component.text("You do not have that much of this item."),
        
        Component.text("You have sold "),
    }; 

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player p = (Player)sender;
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);

        ItemStack item = p.getInventory().getItemInMainHand();
        if(item == null || item.getType() == Material.AIR) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SellCommandMessages.NOITEM.ordinal()]);
            return true;
        }
        else if(!Globals.itemPrices.containsKey(item.getType())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SellCommandMessages.INVALIDITEM.ordinal()]);
            return true;
        }

        int amount = item.getAmount();

        if(args.length > 0) {
            if(!args[0].chars().allMatch(Character::isDigit)) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SellCommandMessages.INVALIDAMOUNT.ordinal()]);
                return true;
            }
            
            amount = Integer.parseInt(args[0]);
        
            if(amount > item.getAmount()) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SellCommandMessages.BADAMOUNT.ordinal()]);
                return true;
            }
        }

        Long itemPrice = Globals.itemPrices.get(item.getType()) * amount;

        player.setMoney(player.getMoney() + itemPrice);

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, 
                                        commandMessages[SellCommandMessages.SUCCESS.ordinal()], 
                                        Component.text(amount + "x "),
                                        item.displayName(),
                                        Component.text(" for $" + itemPrice));
                                        
        if(amount >= item.getAmount()) {
            p.getInventory().setItemInMainHand(null);
        }
        else {
            item.setAmount(item.getAmount() - amount);
            p.getInventory().setItemInMainHand(item);
        }

        return true;
    }

}
