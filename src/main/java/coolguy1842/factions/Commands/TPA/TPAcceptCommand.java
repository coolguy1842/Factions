package coolguy1842.factions.Commands.TPA;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.TPARequest;
import coolguy1842.factions.Managers.TPAManager;
import net.kyori.adventure.text.Component;

public class TPAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            TPARequest request = TPAManager.getInstance().getPlayerRequest(p);

            if(request == null) {
                p.sendMessage(Component.text("You do not have any TPA requests."));
                
                return true;
            }

            TPAManager.getInstance().acceptPlayerRequest(request);

            return true;
        }

        return false;
    }
}
