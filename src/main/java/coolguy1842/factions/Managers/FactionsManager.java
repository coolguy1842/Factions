package coolguy1842.factions.Managers;

import coolguy1842.factions.Classes.Database;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadDatabase;
import coolguy1842.factions.Managers.FactionsManagers.FactionsFactionManager;
import coolguy1842.factions.Managers.FactionsManagers.FactionsInviteManager;
import coolguy1842.factions.Managers.FactionsManagers.FactionsPlayerManager;
import coolguy1842.factions.Managers.FactionsManagers.FactionsRankManager;

public class FactionsManager {

    
    public FactionsFactionManager factionManager;
    public FactionsRankManager rankManager;
    public FactionsPlayerManager playerManager;
    public FactionsInviteManager inviteManager;
    public Database database;

    private static FactionsManager instance = null;

    private FactionsManager() {
        instance = this;

        loadDatabase();

        factionManager = new FactionsFactionManager();
        rankManager = new FactionsRankManager();
        playerManager = new FactionsPlayerManager();
        inviteManager = new FactionsInviteManager();

        factionManager.loadFactions();
        rankManager.loadRanks();
        playerManager.loadPlayers();
        inviteManager.loadInvites();
    }

    public static void createInstance() { 
        instance = new FactionsManager();
    }

    public static FactionsManager getInstance() { 
        if(instance == null) instance = new FactionsManager();
        return instance;
    }
    
    public void close() {
        database.disconnect();

        factionManager.close();
        rankManager.close();
        playerManager.close();
        inviteManager.close();

        database = null;

        factionManager = null;
        rankManager = null;
        playerManager = null;
        inviteManager = null;

        instance = null;
    }

    private void loadDatabase() { LoadDatabase.load(instance); }
}