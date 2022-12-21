package coolguy1842.factions.Commands.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.SubCommands.Factions.NoFaction.FactionAcceptCommand;
import coolguy1842.factions.SubCommands.Factions.NoFaction.FactionCreateCommand;
import coolguy1842.factions.SubCommands.Factions.WithFaction.FactionBalanceCommand;
import coolguy1842.factions.SubCommands.Factions.WithFaction.FactionDisbandCommand;
import coolguy1842.factions.SubCommands.Factions.WithFaction.FactionInfoCommand;
import coolguy1842.factions.SubCommands.Factions.WithFaction.FactionInviteCommand;
import coolguy1842.factions.SubCommands.Factions.WithFaction.FactionLeaveCommand;
import coolguy1842.factions.SubCommands.Factions.WithFaction.FactionRenameCommand;

public class FactionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);

            if(args.length <= 0) {
                if(player.inFaction()) {
                    FactionInfoCommand.execute(p, player, args);
                    
                    return true;
                }

                return false;
            }

            switch(args[0]) {
            case "create":
                FactionCreateCommand.execute(p, player, args);
                break;
            case "leave":
                FactionLeaveCommand.execute(p, player, args);
                break;
            case "disband":
                FactionDisbandCommand.execute(p, player, args);
                break;
            case "invite":
                FactionInviteCommand.execute(p, player, args);
                break;
            case "accept":
                FactionAcceptCommand.execute(p, player, args);
                break;    
            case "rename":
                FactionRenameCommand.execute(p, player, args);
                break;
            case "bal": case "balance":
                FactionBalanceCommand.execute(p, player, args);
                break;
            default:
                break;
            }

            return true;
        }
        
        return false;
    }
}
