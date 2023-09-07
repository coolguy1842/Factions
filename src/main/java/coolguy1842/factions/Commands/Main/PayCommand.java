package coolguy1842.factions.Commands.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum PayCommandMessages {
    INVALIDPLAYER,
    INVALIDAMOUNT,

    SUCCESS,
    SUCCESSTORECEIVER
}


public class PayCommand implements CommandExecutor {
    private static Component[] commandMessages = {
        Component.text("Invalid player to pay."),
        Component.text("Invalid amount to pay."),

        Component.text("You have sent $"),
        Component.text("You have been sent $"),
    }; 

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            Player toPay = Bukkit.getPlayerExact(args[0]);

            if(toPay == null || toPay.getUniqueId() == p.getUniqueId()) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[PayCommandMessages.INVALIDPLAYER.ordinal()]);
                return false;
            }
            else if(!args[1].chars().allMatch(Character::isDigit)) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[PayCommandMessages.INVALIDAMOUNT.ordinal()]);
                return false;
            }

            FactionPlayer fP = FactionsManager.getInstance().playerManager.getPlayer(p);
            FactionPlayer fToPay = FactionsManager.getInstance().playerManager.getPlayer(toPay);
            

            Long amount = Long.parseLong(args[1]);
            if(amount < 0 || amount > fP.getMoney()) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[PayCommandMessages.INVALIDAMOUNT.ordinal()]);
                return false;
            }

            fP.setMoney(fP.getMoney() - amount);
            fToPay.setMoney(fToPay.getMoney() + amount);

            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[PayCommandMessages.SUCCESS.ordinal()], Component.text(amount + " to "), toPay.displayName());
            FactionsMessaging.sendMessage(toPay, Globals.factionsPrefix, commandMessages[PayCommandMessages.SUCCESSTORECEIVER.ordinal()], Component.text(amount + " from "), p.displayName());

            return true;
        }

        return false;
    }
}
