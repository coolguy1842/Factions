package coolguy1842.factions.SubCommands.Factions.InFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum BalanceCommandMessages {
    NOTINFACTION,
    SUCCESS
}

public class FactionBalanceCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text(" balance - $"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[BalanceCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }   

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, 
                                         player.getFaction().getFormattedDisplayName(), 
                                         commandMessages[BalanceCommandMessages.SUCCESS.ordinal()], 
                                         Component.text(player.getFaction().getMoney()));
    }
}
