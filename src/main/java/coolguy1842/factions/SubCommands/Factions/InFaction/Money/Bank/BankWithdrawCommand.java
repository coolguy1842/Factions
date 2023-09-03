package coolguy1842.factions.SubCommands.Factions.InFaction.Money.Bank;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum BankWithdrawCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    NOTANUMBER,
    TOOMUCHMONEY,
    SUCCESS
}

public class BankWithdrawCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to withdraw money."),

        Component.text("You must specify an amount to withdraw."),
        Component.text("You must specify a number."),
        
        Component.text("Your faction does not have enough money."),

        Component.text(" withdrew $"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("withdraw")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankWithdrawCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankWithdrawCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!args[2].chars().allMatch(Character::isDigit)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankWithdrawCommandMessages.NOTANUMBER.ordinal()]);
            return;
        }

        Long money = Long.parseLong(args[2]);
        if(money <= 0) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankDepositCommandMessages.NOTANUMBER.ordinal()]);
            return;
        }
        else if(player.getFaction().getMoney() < money) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankWithdrawCommandMessages.TOOMUCHMONEY.ordinal()]);
            return;
        }

        player.getFaction().setMoney(player.getFaction().getMoney() - money);
        player.setMoney(player.getMoney() + money);

        player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                            p.name(),
                                            commandMessages[BankWithdrawCommandMessages.SUCCESS.ordinal()],
                                            Component.text(args[2] + "."));
    } 
}
