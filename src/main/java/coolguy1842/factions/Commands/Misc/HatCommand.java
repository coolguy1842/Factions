package coolguy1842.factions.Commands.Misc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class HatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;

            PlayerInventory inventory = p.getInventory();

            ItemStack helmet = inventory.getHelmet();
            ItemStack hand = inventory.getItemInMainHand();

            inventory.setHelmet(hand);
            inventory.setItemInMainHand(helmet);

            return true;
        }

        return false;
    }
}
