package coolguy1842.factions.Commands.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.SubCommands.Factions.NoFaction.FactionAcceptCommand;
import coolguy1842.factions.SubCommands.Factions.NoFaction.FactionCreateCommand;
import coolguy1842.factions.SubCommands.Factions.NoFaction.FactionDenyCommand;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import coolguy1842.factions.SubCommands.Factions.All.FactionsLeaderboardCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.FactionInfoCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.FactionLeaveCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Claiming.FactionAutoClaimCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Claiming.FactionClaimCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Claiming.FactionUnClaimCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Homes.FactionDeleteHomeCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Homes.FactionHomeCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Homes.FactionSetHomeCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Money.FactionBalanceCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Money.FactionBankCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.FactionDisbandCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.FactionInviteCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.FactionKickCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.FactionRenameCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.FactionSetCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.FactionTransferCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally.FactionAllyCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally.FactionDeclineAllyCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally.FactionUnAllyCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.FactionRankCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.RankListCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Vault.FactionVaultCommand;

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

            // massive list (dont know any real alternatives)
            switch(args[0]) {
            case "info": FactionInfoCommand.execute(p, player, args); break;
            case "menu": break;
            case "create": FactionCreateCommand.execute(p, player, args); break;
            case "leave": FactionLeaveCommand.execute(p, player, args); break;
            case "kick": FactionKickCommand.execute(p, player, args); break;
            case "claim": FactionClaimCommand.execute(p, player, args); break;
            case "autoclaim": FactionAutoClaimCommand.execute(p, player, args); break;
            case "unclaim": FactionUnClaimCommand.execute(p, player, args); break;
            case "disband": FactionDisbandCommand.execute(p, player, args); break;
            case "transfer": FactionTransferCommand.execute(p, player, args); break;
            case "invite": FactionInviteCommand.execute(p, player, args); break;
            case "vault": FactionVaultCommand.execute(p, player, args); break;
            case "rank": FactionRankCommand.execute(p, player, args); break;
            case "set": FactionSetCommand.execute(p, player, args); break;
            case "ranks": RankListCommand.execute(p, player, args); break;
            case "accept": FactionAcceptCommand.execute(p, player, args); break;    
            case "deny": FactionDenyCommand.execute(p, player, args); break;   
            case "rename": FactionRenameCommand.execute(p, player, args); break;
            case "bank": FactionBankCommand.execute(p, player, args); break;
            case "bal": case "balance": FactionBalanceCommand.execute(p, player, args); break;
            case "home": FactionHomeCommand.execute(p, player, args); break;
            case "sethome": FactionSetHomeCommand.execute(p, player, args); break;
            case "delhome": case "deletehome": FactionDeleteHomeCommand.execute(p, player, args); break;
            case "leaderboard": FactionsLeaderboardCommand.execute(p, player, args); break;
            case "ally": FactionAllyCommand.execute(p, player, args); break;
            case "unally": FactionUnAllyCommand.execute(p, player, args); break;
            case "declineally": FactionDeclineAllyCommand.execute(p, player, args); break;
            default:
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Invalid command."));
                break;
            }

            return true;
        }
        
        return false;
    }
}
