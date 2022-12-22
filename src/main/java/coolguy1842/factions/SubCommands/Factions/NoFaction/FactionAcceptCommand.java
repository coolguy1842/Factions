package coolguy1842.factions.SubCommands.Factions.NoFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum AcceptCommandMessages {
    INFACTION,
    NOARGS,
    NOINVITE,
    SUCCESS
}
public class FactionAcceptCommand {
    
    private static Component[] commandMessages = {
        Component.text("You are already in a faction."),
        Component.text("You must specify the name of the faction."),
        Component.text("You do not have an invite to this faction."),
        Component.text(" has joined "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AcceptCommandMessages.INFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AcceptCommandMessages.NOARGS.ordinal()]);
            return;
        }
        
        Faction faction = FactionsManager.getInstance().factionManager.getFaction(args[1]);
        if(faction == null || !player.hasInvite(faction.getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AcceptCommandMessages.NOINVITE.ordinal()]);
            return;
        }

        player.removeAllInvites();
        
        player.setFaction(faction.getID());
        if(faction.hasDefaultRank()) player.setRank(faction.getDefaultRank().getID());
        else player.setRank(null);
        
        for(FactionPlayer factionPlayer : faction.players.values()) {
            Player fPlayer = factionPlayer.getPlayer();
            
            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, 
                                                        p.displayName(), 
                                                        commandMessages[AcceptCommandMessages.SUCCESS.ordinal()],
                                                        player.getFaction().getFormattedDisplayName(),
                                                        Component.text("."));
        }
    }
}
