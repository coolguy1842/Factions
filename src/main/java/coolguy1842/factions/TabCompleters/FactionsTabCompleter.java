package coolguy1842.factions.TabCompleters;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.SubTabCompleters.Factions.PlayerNoFactionTabCompleter;
import coolguy1842.factions.SubTabCompleters.Factions.PlayerWithFactionTabCompleter;

public class FactionsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p.getUniqueId());

            if(!player.inFaction()) return PlayerNoFactionTabCompleter.onTabComplete(p, player, command, label, args);
            else return PlayerWithFactionTabCompleter.onTabComplete(p, player, command, label, args);
        }

        return null;
    }
    
}
