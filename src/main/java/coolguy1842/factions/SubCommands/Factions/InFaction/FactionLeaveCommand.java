package coolguy1842.factions.SubCommands.Factions.InFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum LeaveCommandMessages {
    NOTINFACTION,
    LEADER,
    SUCCESS
}

public class FactionLeaveCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You are the leader; you can disband or transfer ownership instead."),
        Component.text("Left the faction."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[LeaveCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[LeaveCommandMessages.LEADER.ordinal()]);
            return;
        }

        player.setFaction(null);
        player.setRank(null);
    
        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[LeaveCommandMessages.SUCCESS.ordinal()]);
    }
}
