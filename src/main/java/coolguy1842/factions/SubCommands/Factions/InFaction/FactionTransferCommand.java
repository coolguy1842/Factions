package coolguy1842.factions.SubCommands.Factions.InFaction;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum TransferCommandMessages {
    NOTINFACTION,
    NOTLEADER,
    NOARGS,
    WRONGPLAYER,
    NEWLEADERSUCCESS
}

public class FactionTransferCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You are not the leader of this faction."),
        Component.text("You must specify the player to give ownership to."),
        Component.text("This player is not in this faction."),
        Component.text(" has been transferred to "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[TransferCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[TransferCommandMessages.NOTLEADER.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[TransferCommandMessages.NOARGS.ordinal()]);
            return;
        }
        
        FactionPlayer newOwner;
        Component newOwnerDisplayName;
        if(Bukkit.getPlayerExact(args[1]) != null) {
            Player newOwnerP = Bukkit.getPlayerExact(args[1]);
            
            newOwnerDisplayName = newOwnerP.displayName();
            newOwner = FactionsManager.getInstance().playerManager.getPlayer(newOwnerP.getUniqueId());
        }
        else if(Bukkit.getOfflinePlayer(args[1]) != null) {
            OfflinePlayer newOwnerP = Bukkit.getOfflinePlayer(args[1]);

            newOwnerDisplayName = Component.text(newOwnerP.getName());
            newOwner = FactionsManager.getInstance().playerManager.getPlayer(newOwnerP.getUniqueId());
        }
        else {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[TransferCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }

        if(newOwner == null || !newOwner.inFaction() || !newOwner.getFaction().getID().equals(player.getFaction().getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[TransferCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }
        else if(newOwner.getID().equals(player.getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[TransferCommandMessages.WRONGPLAYER.ordinal()]);
            return;
        }

        
        Component[] message = { Globals.factionsPrefix, player.getFaction().getFormattedDisplayName(), commandMessages[TransferCommandMessages.NEWLEADERSUCCESS.ordinal()], newOwnerDisplayName };
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            if(factionPlayer.getPlayer() == null) continue;

            FactionsMessaging.sendMessage(factionPlayer.getPlayer(), message);
        }

        player.getFaction().setLeader(newOwner.getID());

        return;
    }
}
