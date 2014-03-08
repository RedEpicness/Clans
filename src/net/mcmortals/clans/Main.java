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
    public ArrayList<ClanMember> clanmembers = new ArrayList<ClanMember>();

    //testing?

    @Override
    public void onEnable(){
        sql = new MySQL(this);
        c = sql.openConnection();
        getProxy().getPluginManager().registerCommand(this, new ClanCommand(this));
        getProxy().getPluginManager().registerCommand(this, new cCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ClansCommand(this));
        try{
            Statement statement = c.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS clanlist (id int, Name text, Coins int);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS clanmembers (Name text, ClanId int, UUID text);");
            ResultSet res = statement.executeQuery("SELECT * FROM clanlist");
            do{
                ResultSet res1 = statement.executeQuery("SELECT * FROM clanlist;");
                String name = res1.getString("Name");
                int Coins = res1.getInt("Coins");
                int id = res1.getInt("id");
                clans.set(id, new Clan(this, Coins, name, id));
            }
            while(res.next());
            ResultSet res2 = statement.executeQuery("SELECT * FROM clanmembers");
            do{
                ResultSet res3 = statement.executeQuery("SELECT * FROM clanmembers");
                clans.get(res3.getInt("ClanId")).addPlayer(getProxy().getPlayer(res.getString("Name")));
                ClanMember m = new ClanMember(this, res3.getInt("ClanId"), res3.getString("Name"), res3.getString("UUID"));
                clanmembers.add(m);
            }while(res2.next());
            statement.close();
        }
        catch(SQLException e){
            getLogger().log(Level.WARNING, e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable(){
        try{
            c.close();
        }
        catch(SQLException e){}
    }

    public Clan getClanById(int id){
        try{
            return clans.get(id);
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    public ClanMember getClanMemberByUUID(String UUID){
        for (ClanMember a : clanmembers){
            if(a.getUUID().equals(UUID)){
                return a;
            }
        }
        return null;
    }

}
