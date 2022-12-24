package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankInfoCommandMessages {
    NORANK
}

public class RankInfoCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have a rank."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasRank()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankInfoCommandMessages.NORANK.ordinal()]);
            return;
        }

        String permissionsStr = String.join(", ", player.getRank().getPermissions());
        if(permissionsStr.length() <= 0) permissionsStr = "None";
 
        Component message = Component.text("----- Rank \"" + player.getRank().getDisplayName() + "\" Info -----\n")
        .append(Component.text(String.format("""
defaultRank - %b
permissions - %s
%s""", 
player.getRank().getIsDefault(),
permissionsStr, 
StringUtils.repeat("-", "", player.getRank().getDisplayName().length() + 21))));

        FactionsMessaging.sendMessage(p, message);
    }
}
