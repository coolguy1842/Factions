package coolguy1842.factions.SubCommands.Factions.InFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Enums.MenuType;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum MenuCommandMessages {
    NOTINFACTION
}

public class FactionMenuCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[MenuCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }

        player.getMenu().setMenu(MenuType.BASE);
    }
}
