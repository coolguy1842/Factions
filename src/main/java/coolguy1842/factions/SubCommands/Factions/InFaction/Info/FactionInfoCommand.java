package coolguy1842.factions.SubCommands.Factions.InFaction.Info;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum InfoCommandMessages {
    NOTINFACTION,
}

public class FactionInfoCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction.")
    }; 

    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[InfoCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }

        Faction faction = player.getFaction();

        Component out = Component.text("----- ").append(faction.getFormattedDisplayName()).append(Component.text(" Info -----\n"))
                        .append(Component.text(String.format("""
Leader - %s
Balance - $%d
%s""", 
Bukkit.getOfflinePlayer(faction.getLeader()).getName(), 
faction.getMoney(), 
StringUtils.repeat("-", "", faction.getDisplayName().length() + 15))));


        FactionsMessaging.sendMessage(p, out);
    }
}
