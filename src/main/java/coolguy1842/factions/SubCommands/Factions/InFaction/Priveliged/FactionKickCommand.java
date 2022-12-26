package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;

enum KickCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    NOARGS,
    WRONGPLAYER,
    SUCCESS,
    SUCCESSTOKICKED
}

public class FactionKickCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions."),
        Component.text("You must specify the player to kick."),
        Component.text("You cannot kick this player."),
        Component.text(" has been kicked from "),
        Component.text("You have been kicked from "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("kick")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.NOARGS.ordinal()]);
            return;
        }

        FactionPlayer kickee = FactionsManager.getInstance().playerManager.getPlayer(PlayerUtil.getPlayerUUID(args[1]));
        if(kickee == null) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }
        else if(!player.isLeader() && kickee.hasPermission("kick")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }
        else if(kickee.isOnline()) {
            FactionsMessaging.sendMessage(kickee.getPlayer(), Globals.factionsPrefix, 
                                                    commandMessages[KickCommandMessages.SUCCESSTOKICKED.ordinal()],
                                                    player.getFaction().getFormattedDisplayName(),
                                                    Component.text("."));
        }
        
        kickee.setRank(null);
        kickee.setFaction(null);
    
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            Player fPlayer = factionPlayer.getPlayer();
            
            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, 
                                                    kickee.getDisplayName(), 
                                                    commandMessages[KickCommandMessages.SUCCESS.ordinal()],
                                                    player.getFaction().getFormattedDisplayName(),
                                                    Component.text("."));
        }
    }
}
