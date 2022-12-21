package coolguy1842.factions.SubCommands.Factions.WithFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import net.kyori.adventure.text.Component;

enum DisbandCommandMessages {
    NOTINFACTION,
    NOTLEADER,
    SUCCESS
}

public class FactionDisbandCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You are not the leader; you can leave instead."),
        Component.text("Faction disbanded successfully!"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.getFaction() == null) {
            p.sendMessage(commandMessages[DisbandCommandMessages.NOTINFACTION.ordinal()]);
            
            return;
        }
        else if(!player.getFaction().getLeader().equals(player.getID())) {
            p.sendMessage(commandMessages[DisbandCommandMessages.NOTLEADER.ordinal()]);
            
            return;
        }

        FactionsManager.getInstance().factionManager.deleteFaction(player.getFaction());
        
        p.sendMessage(commandMessages[DisbandCommandMessages.SUCCESS.ordinal()]);
    }
}
