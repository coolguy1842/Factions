package coolguy1842.factions.SubTabCompleters.Factions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class PlayerNoFactionTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        switch(args.length) {
        case 1:
            out.add("help");
            out.add("create");
            out.add("accept");
            out.add("deny");
            out.add("leaderboard");
            break;
        case 2:
            if(args[0].equals("accept") || args[0].equals("deny")) {
                for(UUID id : FactionsManager.getInstance().inviteManager.getPlayerInvites(p)) {
                    Faction faction = FactionsManager.getInstance().factionManager.getFaction(id);

                    out.add(faction.getDisplayName());
                }
            }
        default:
            break;
        }



        return out;
    }
}
