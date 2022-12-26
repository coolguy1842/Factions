package coolguy1842.factions.SubCommands.Factions.InFaction.Money;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.SubCommands.Factions.InFaction.Money.Bank.BankDepositCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Money.Bank.BankWithdrawCommand;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum BankCommandMessages {
    NOTINFACTION,
    WRONGARG
}

public class FactionBankCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("Invalid option."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }

        if(args.length <= 1) {
            return;
        }

        switch(args[1]) {
        case "withdraw":
            BankWithdrawCommand.execute(p, player, args);
            break;
        case "deposit":
            BankDepositCommand.execute(p, player, args);
            break;
        case "bal": case "balance":
            FactionBalanceCommand.execute(p, player, args);
            break;
        default: 
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BankCommandMessages.WRONGARG.ordinal()]);
            break;
        }
    }
}
