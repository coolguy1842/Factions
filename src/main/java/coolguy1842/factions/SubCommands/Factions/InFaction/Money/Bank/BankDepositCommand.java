package coolguy1842.factions.SubCommands.Factions.InFaction.Money.Bank;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum BankDepositCommandMessages {
    NOARGS,
    NOTANUMBER,
    TOOMUCHMONEY,
    SUCCESS
}

public class BankDepositCommand {
    private static Component[] commandMessages = {
        Component.text("You must specify an amount to deposit."),
        Component.text("You must specify a number."),
        
        Component.text("You do not have that much money."),

        Component.text(" deposited $"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankDepositCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!args[2].chars().allMatch(Character::isDigit)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankDepositCommandMessages.NOTANUMBER.ordinal()]);
            return;
        }

        Long money = Long.parseLong(args[2]);
        if(money <= 0) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankDepositCommandMessages.NOTANUMBER.ordinal()]);
            return;
        }
        else if(player.getMoney() < money) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankDepositCommandMessages.TOOMUCHMONEY.ordinal()]);
            return;
        }

        player.getFaction().setMoney(player.getFaction().getMoney() + money);
        player.setMoney(player.getMoney() - money);

        player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                            p.name(),
                                            commandMessages[BankDepositCommandMessages.SUCCESS.ordinal()],
                                            Component.text(args[2] + "."));
    } 
}
