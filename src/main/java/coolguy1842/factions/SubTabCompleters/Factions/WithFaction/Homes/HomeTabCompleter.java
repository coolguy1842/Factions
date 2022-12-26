package coolguy1842.factions.SubTabCompleters.Factions.WithFaction.Homes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Classes.FactionPlayer;

public class HomeTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        for(FactionHome home : player.getFaction().homes.values()) {
            out.add(home.getDisplayName());
        }

        return out;
    }
}
