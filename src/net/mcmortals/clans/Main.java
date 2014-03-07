package net.mcmortals.clans;

import net.mcmortals.clans.commands.ClanCommand;
import net.mcmortals.clans.commands.ClansCommand;
import net.mcmortals.clans.commands.cCommand;
import net.mcmortals.clans.mysql.MySQL;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class Main extends Plugin{

    MySQL sql;
    public Connection c;
    public ArrayList<Clan> clans = new ArrayList<Clan>();

    @Override
    public void onEnable(){
        sql = new MySQL(this);
        c = sql.openConnection();
        getProxy().getPluginManager().registerCommand(this, new ClanCommand(this));
        getProxy().getPluginManager().registerCommand(this, new cCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ClansCommand(this));
        try{
            Statement statement = c.createStatement();
            statement.executeQuery("CREATE TABLE IF NOT EXISTS 'clanlist' ('id' int, 'Name' varchat(20), 'Coins' int);");
            statement.executeQuery("CREATE TABLE IF NOT EXISTS 'clanmembers' ('Name' varchat(16), 'GuildId' int);");
            ResultSet res = statement.executeQuery("SELECT id FROM clanlist");
            int count = 0;
            do count++; while(res.next());
            for(int a = count-1;0 <= count;count-- ){
                ResultSet res1 = statement.executeQuery("SELECT * FROM clanlist WHERE id="+count+";");
                String name = res1.getString("Name");
                int Coins = res1.getInt("Coins");
                clans.add(count, new Clan(this, Coins, name, count));
            }
            ResultSet res2 = statement.executeQuery("SELECT GuildId FROM clanmembers");
            do{
                ResultSet res3 = statement.executeQuery("SELECT * FROM clanmembers");
                clans.get(res3.getInt("GuildId")).addPlayer(getProxy().getPlayer(res.getString("Name")));
            }while(res2.next());
            statement.close();
        }
        catch(SQLException e){
            getLogger().log(Level.WARNING, e.getMessage());
        }

    }

    @Override
    public void onDisable(){
        try{
            Statement s = c.createStatement();
            s.executeQuery("DELETE FROM clanlist;");
            s.executeQuery("DELETE FROM clanmembers;");
            s.close();
        }
        catch(SQLException e){
            getLogger().log(Level.WARNING, e.getMessage());
        }
    }

    public Clan getClanById(int id){
        try{
            return clans.get(id);
        }
        catch(NullPointerException e){
            return null;
        }
    }

}
