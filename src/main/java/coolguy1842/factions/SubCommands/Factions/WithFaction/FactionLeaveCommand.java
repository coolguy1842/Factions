package coolguy1842.factions.SubCommands.Factions.WithFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
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
        Component.text("Faction left successfully!"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.getFaction() == null) {
            p.sendMessage(commandMessages[LeaveCommandMessages.NOTINFACTION.ordinal()]);
            
            return;
        }
        else if(player.getFaction().getLeader().equals(player.getID())) {
            p.sendMessage(commandMessages[LeaveCommandMessages.LEADER.ordinal()]);
            
            return;
        }

        player.setFaction(null);
        player.setRank(null);
        
        p.sendMessage(commandMessages[LeaveCommandMessages.SUCCESS.ordinal()]);
    }
}
