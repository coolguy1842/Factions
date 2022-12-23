package coolguy1842.factions.Managers;

import coolguy1842.factions.Classes.Database;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadDatabase;
import coolguy1842.factions.Managers.FactionsManagers.ClaimManager;
import coolguy1842.factions.Managers.FactionsManagers.FactionManager;
import coolguy1842.factions.Managers.FactionsManagers.InviteManager;
import coolguy1842.factions.Managers.FactionsManagers.PlayerManager;
import coolguy1842.factions.Managers.FactionsManagers.RankManager;
import coolguy1842.factions.Managers.FactionsManagers.VaultManager;

public class FactionsManager {
    public FactionManager factionManager;
    public RankManager rankManager;
    public VaultManager vaultManager;
    public PlayerManager playerManager;
    public InviteManager inviteManager;
    public ClaimManager claimManager;

    public Database database;

    private static FactionsManager instance = null;

    private FactionsManager() {
        instance = this;

        loadDatabase();

        factionManager = new FactionManager();
        rankManager = new RankManager();
        vaultManager = new VaultManager();
        playerManager = new PlayerManager();
        inviteManager = new InviteManager();
        claimManager = new ClaimManager();

        factionManager.loadFactions();
        rankManager.loadRanks();
        vaultManager.loadVaults();
        playerManager.loadPlayers();
        inviteManager.loadInvites();
        claimManager.loadClaims();
    }

    public static void createInstance() { 
        instance = new FactionsManager();
    }

    public static FactionsManager getInstance() { 
        if(instance == null) instance = new FactionsManager();
        return instance;
    }
    
    public void close() {
        claimManager.close();
        inviteManager.close();
        playerManager.close();
        vaultManager.close();
        rankManager.close();
        factionManager.close();

        database.disconnect();

        claimManager = null;
        inviteManager = null;
        playerManager = null;
        rankManager = null;
        factionManager = null;
        vaultManager = null;
        
        database = null;

        instance = null;
    }

    private void loadDatabase() { LoadDatabase.load(instance); }
}