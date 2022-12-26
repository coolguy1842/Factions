package coolguy1842.factions.SubCommands.Factions.All;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

public class FactionsLeaderboardCommand {
    public static void execute(Player p, FactionPlayer player, String[] args) {
        Component start = Component.text("----- Factions Leaderboard -----");
        Component end =   Component.text("------------------------------");

        FactionsMessaging.sendMessage(p, start);
        if(FactionsManager.getInstance().factionManager.factions.size() <= 0) {
            FactionsMessaging.sendMessage(p, Component.text("None\n"), end);

            return;
        }

        List<Faction> topFactions = FactionsManager.getInstance().factionManager.factions.values()
                                            .stream().sorted(new Comparator<Faction>() {
                                                public int compare(Faction f1, Faction f2) {
                                                    return f2.getMoney().compareTo(f1.getMoney());
                                                }
                                            }).collect(Collectors.toList());
        
        for(int i = 0; i < Math.min(topFactions.size(), 10); i++) {
            Faction faction = topFactions.get(i);
            FactionsMessaging.sendMessage(p, faction.getFormattedDisplayName(), Component.text(": $" + faction.getMoney()));
        }

        FactionsMessaging.sendMessage(p, end);
    }    
}
