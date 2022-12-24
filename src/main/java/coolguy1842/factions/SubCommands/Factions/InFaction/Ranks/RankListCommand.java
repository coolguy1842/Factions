package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

public class RankListCommand {
    public static void execute(Player p, FactionPlayer player, String[] args) {
        Component out = Component.text("----- ").append(player.getFaction().getFormattedDisplayName()).append(Component.text(" Ranks -----\n"));

        if(player.getFaction().ranks.size() <= 0) {
            out = out.append(Component.text("None\n"));
            out = out.append(Component.text(StringUtils.repeat("-", "", player.getFaction().getDisplayName().length() + 16)));

            FactionsMessaging.sendMessage(p, out);    
            
            return;
        }

        for(FactionRank rank : player.getFaction().ranks.values()) {
            out = out.append(Component.text(rank.getDisplayName() + "\n"));
        }

        out = out.append(Component.text(StringUtils.repeat("-", "", player.getFaction().getDisplayName().length() + 16)));
        FactionsMessaging.sendMessage(p, out);
    }
}
