package coolguy1842.factions.SubTabCompleters.Factions.WithFaction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;

public class BankTabCompleter {
    public static List<String> onTabComplete(Player p, FactionPlayer player, Command command, String label, String[] args) {
        ArrayList<String> out = new ArrayList<>();

        switch(args.length) {
        case 2:
            out.add("deposit");
            out.add("bal");
            out.add("balance");
            if(player.hasPermission("withdraw")) out.add("withdraw");
            break;
        default: break;    
        }

        return out;
    }
}
