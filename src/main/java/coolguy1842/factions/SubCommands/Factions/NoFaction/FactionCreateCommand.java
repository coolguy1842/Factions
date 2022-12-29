package coolguy1842.factions.SubCommands.Factions.NoFaction;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.StringUtil;
import net.kyori.adventure.text.Component;

enum CreateCommandMessages {
    NOARGS,
    INFACTION,
    EXISTS,
    SUCCESS
}

public class FactionCreateCommand {
    private static Component[] commandMessages = {
        Component.text("You must specify the name of the faction."),
        Component.text("You are already in a faction."),
        Component.text("That name is already taken."),
        Component.text(" created faction \""),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[CreateCommandMessages.INFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[CreateCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[CreateCommandMessages.EXISTS.ordinal()]);
            return;
        }

        FactionsMessaging.broadcastMessage(null, Globals.factionsPrefix, p.displayName(), commandMessages[CreateCommandMessages.SUCCESS.ordinal()], Component.text(args[1] + "\"."));
        
        String avatar = "https://crafatar.com/avatars/" + p.getUniqueId();
        FactionsMessaging.sendToDiscord(StringUtil.componentsToString(
                                                                    p.displayName(), 
                                                                    commandMessages[CreateCommandMessages.SUCCESS.ordinal()], 
                                                                    Component.text(args[1] + "\".")), 
                                                                    "[Factions]", avatar);

        FactionsManager.getInstance().factionManager.createFaction(UUID.randomUUID(), args[1], p, 0L);
    }
}
