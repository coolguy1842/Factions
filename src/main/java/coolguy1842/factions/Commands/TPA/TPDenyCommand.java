package coolguy1842.factions.Commands.TPA;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.TPARequest;
import coolguy1842.factions.Managers.TPAManager;
import net.kyori.adventure.text.Component;

public class TPDenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            TPARequest request = TPAManager.getInstance().getPlayerRequested(p);

            if(request == null) {
                p.sendMessage(Component.text("You do not have any TPA requests."));
                
                return true;
            }

            TPAManager.getInstance().removePlayerRequest(request);
            
            return true;
        }

        return false;
    }
}
