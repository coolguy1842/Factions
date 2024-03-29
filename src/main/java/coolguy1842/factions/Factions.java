package coolguy1842.factions;

import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import coolguy1842.factions.Commands.Main.BalanceCommand;
import coolguy1842.factions.Commands.Main.FactionsCommand;
import coolguy1842.factions.Commands.Main.HubCommand;
import coolguy1842.factions.Commands.Main.PayCommand;
import coolguy1842.factions.Commands.Main.RTPCommand;
import coolguy1842.factions.Commands.Main.SellAllCommand;
import coolguy1842.factions.Commands.Main.SellCommand;
import coolguy1842.factions.Commands.Misc.HatCommand;
import coolguy1842.factions.Commands.Misc.PingCommand;
import coolguy1842.factions.Commands.Misc.QueryCommand;
import coolguy1842.factions.Commands.TPA.TPACommand;
import coolguy1842.factions.Commands.TPA.TPAHereCommand;
import coolguy1842.factions.Commands.TPA.TPAcceptCommand;
import coolguy1842.factions.Commands.TPA.TPDenyCommand;
import coolguy1842.factions.Events.Entity.Inventory.InventoryClose;
import coolguy1842.factions.Events.Entity.Inventory.InventoryInteract;
import coolguy1842.factions.Events.Entity.Player.PlayerAdvancementDone;
import coolguy1842.factions.Events.Entity.Player.PlayerAttack;
import coolguy1842.factions.Events.Entity.Player.PlayerBreakBlock;
import coolguy1842.factions.Events.Entity.Player.PlayerChat;
import coolguy1842.factions.Events.Entity.Player.PlayerDeath;
import coolguy1842.factions.Events.Entity.Player.PlayerInteract;
import coolguy1842.factions.Events.Entity.Player.PlayerJoin;
import coolguy1842.factions.Events.Entity.Player.PlayerLeave;
import coolguy1842.factions.Events.Entity.Player.PlayerMove;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.TabCompleters.FactionsTabCompleter;
import coolguy1842.factions.Util.FactionsLogger;

public final class Factions extends JavaPlugin {
    @Override
    public void onEnable() {
        init();

        registerCommands();
        registerCommandTabCompletions();
        registerEvents();
    }

    @Override
    public void onLoad() {
        init();
    }


    void init() {
        Globals.plugin = this;
        Globals.tpaRequests = new HashMap<>();
        Globals.tpaHereRequests = new HashMap<>();

        FactionsLogger.info( "Started");
        FactionsManager.createInstance();
    }

    void destroy() {
        FactionsManager.getInstance().close();
        
        Globals.tpaRequests = null;
        Globals.tpaHereRequests = null;
        Globals.plugin = null;
    }

    void registerCommands() {
        this.getCommand("factions").setExecutor(new FactionsCommand());
        this.getCommand("balance").setExecutor(new BalanceCommand());
        
        this.getCommand("tpa").setExecutor(new TPACommand());
        this.getCommand("tpahere").setExecutor(new TPAHereCommand());
        this.getCommand("tpaccept").setExecutor(new TPAcceptCommand());
        this.getCommand("tpdeny").setExecutor(new TPDenyCommand());
        
        this.getCommand("rtp").setExecutor(new RTPCommand());
        this.getCommand("hub").setExecutor(new HubCommand());
        
        this.getCommand("pay").setExecutor(new PayCommand());
        this.getCommand("sell").setExecutor(new SellCommand());
        this.getCommand("sellall").setExecutor(new SellAllCommand());
        
        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("hat").setExecutor(new HatCommand());

        this.getCommand("query").setExecutor(new QueryCommand());
    }
    
    void registerCommandTabCompletions() {
        this.getCommand("factions").setTabCompleter(new FactionsTabCompleter());   
    }

    void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerMove(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerAttack(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerChat(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerAdvancementDone(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new PlayerBreakBlock(), Globals.plugin);

        getServer().getPluginManager().registerEvents(new InventoryInteract(), Globals.plugin);
        getServer().getPluginManager().registerEvents(new InventoryClose(), Globals.plugin);
    }

    @Override
    public void onDisable() {
        destroy();
    }
}
