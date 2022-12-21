package coolguy1842.factions.SubCommands.Factions.WithFaction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
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
        Component.text("This player is not online."),
        Component.text("This player is already in a faction."),
        Component.text("This player already has an invite from this faction."),
        Component.text("Invited player successfully!"),
        Component.text("You have been sent an invite to "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.getFaction() == null) {
            p.sendMessage(commandMessages[InviteCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.getFaction().getLeader().equals(player.getID())) {
            if(player.getRank() == null) {
                p.sendMessage(commandMessages[InviteCommandMessages.NOPERMISSION.ordinal()]);
                return;
            }
            else if(!player.getRank().hasPermission("invite")) {
                p.sendMessage(commandMessages[InviteCommandMessages.NOPERMISSION.ordinal()]);
                return;
            }
        }
        else if(args.length <= 1) {
            p.sendMessage(commandMessages[InviteCommandMessages.NOARGS.ordinal()]);
            return;
        }
        
        Player invited = Bukkit.getPlayerExact(args[1]);
        if(invited == null) {
            p.sendMessage(commandMessages[InviteCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }
        
        if(invited.getUniqueId().equals(p.getUniqueId())) {
            p.sendMessage(commandMessages[InviteCommandMessages.WRONGPLAYER.ordinal()]);     
            return;
        }
        else if(FactionsManager.getInstance().playerManager.getPlayer(invited).getFaction() != null) {
            p.sendMessage(commandMessages[InviteCommandMessages.PLAYERINFACTION.ordinal()]);
            
            return;
        }
        else if(FactionsManager.getInstance().inviteManager.playerHasInvite(invited, player.getFaction().getID())) {
            p.sendMessage(commandMessages[InviteCommandMessages.HASINVITE.ordinal()]);
            
            return;
        }
        
        FactionsManager.getInstance().inviteManager.createFactionInvite(invited, player.getFaction().getID());
        p.sendMessage(commandMessages[InviteCommandMessages.SUCCESS.ordinal()]);
        invited.sendMessage(commandMessages[InviteCommandMessages.INVITEDSUCCESSMESSAGE.ordinal()].append(Component.text(player.getFaction().getDisplayName() + "!")));
    }
}
