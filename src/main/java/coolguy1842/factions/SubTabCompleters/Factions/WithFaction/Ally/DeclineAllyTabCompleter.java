package coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Ally;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class DeclineAllyTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        if(!player.isLeader()) return out;

        switch(args.length) {
        case 2:
            for(Faction faction : FactionsManager.getInstance().factionManager.factions.values()) {
                if(!player.getFaction().hasAllyInvite(faction) || faction.getID().equals(player.getFaction().getID())) continue;

                out.add(faction.getDisplayName());
            }

            break;
        default: break;    
        }

        return out;
    }
}
