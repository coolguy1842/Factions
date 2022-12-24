package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum InviteCommandMessages {
    NOTINFACTION,
    NOPERMISSION,
    NOARGS,
    WRONGPLAYER,
    PLAYERINFACTION,
    HASINVITE,
    SUCCESS,
    INVITEDSUCCESSMESSAGE
}

public class FactionInviteCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to invite players."),
        Component.text("You must specify a player to invite."),
        Component.text("Cannot find the specified player."),
        Component.text("This player is already in a faction."),
        Component.text("This player already has an invite from this faction."),
        Component.text(" has been invited to "),
        Component.text("You have been sent an invite to "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InviteCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("invite")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InviteCommandMessages.NOPERMISSION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InviteCommandMessages.NOARGS.ordinal()]);
            return;
        }
        
        Player invited = Bukkit.getPlayerExact(args[1]);
        if(invited == null || invited.getUniqueId().equals(p.getUniqueId())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InviteCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }
        else if(FactionsManager.getInstance().playerManager.getPlayer(invited).inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InviteCommandMessages.PLAYERINFACTION.ordinal()]);
            return;
        }
        else if(FactionsManager.getInstance().inviteManager.playerHasInvite(invited, player.getFaction().getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InviteCommandMessages.HASINVITE.ordinal()]);
            return;
        }
        
        FactionsManager.getInstance().inviteManager.createFactionInvite(invited, player.getFaction().getID());
        
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            Player fPlayer = factionPlayer.getPlayer();
            
            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, 
                                                        invited.displayName(), 
                                                        commandMessages[InviteCommandMessages.SUCCESS.ordinal()],
                                                        player.getFaction().getFormattedDisplayName(),
                                                        Component.text("."));
        }

        FactionsMessaging.sendMessage(invited, Globals.factionsPrefix, commandMessages[InviteCommandMessages.INVITEDSUCCESSMESSAGE.ordinal()], player.getFaction().getFormattedDisplayName());
    }
}
