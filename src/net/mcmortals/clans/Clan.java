package net.mcmortals.clans;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class Clan{

    private Main clans;
    private int coins;
    private String name;
    private String prefix;
    private int id;
    private ArrayList<String> members = new ArrayList<String>();
    //private ArrayList<Rank> ranks;

    public Clan(Main clans, int coins, String name/*, String prefix*/, int id){
        this.clans = clans;
        this.coins = coins;
        this.id = id;
        this.name = name;
        //this.prefix = prefix;
    }

    public ArrayList<String> getPlayers(){
        return members;
    }

    public int getCoins(){
        return coins;
    }

    public int getId(){
        return id;
    }

    public String getPrefix(){
        return prefix;
    }

    public String getName(){
        return name;
    }

    public boolean hasPlayer(ProxiedPlayer p){
        return members.contains(p.getUUID());
    }

    public boolean addPlayer(ProxiedPlayer p){
        if(members.contains(p.getUUID())){
            return false;
        }
        else if(clans.getClanMemberByUUID(p.getUUID()) != null){
            return false;
        }
        members.add(p.getUUID());
        try{
            Statement s = clans.c.createStatement();
            s.executeUpdate("INSERT INTO clanmembers VALUES ('"+p.getName()+"', "+getId()+", '"+p.getUUID()+"');");
            s.close();
        }
        catch(SQLException e){
            clans.getLogger().log(Level.WARNING, e.getMessage());
        }
        clans.clanmembers.add(new ClanMember(clans, getId(), p.getName(), p.getUUID()));
        return true;
    }

    public boolean kickPlayer(ProxiedPlayer p){
        if(!members.contains(p.getUUID())){
            return false;
        }
        try{
            Statement s = clans.c.createStatement();
            s.executeUpdate("DELETE FROM clanmembers WHERE Name='"+p.getName()+"';");
            s.close();
        }
        catch(SQLException e){
            clans.getLogger().log(Level.WARNING, e.getMessage());
        }
        members.remove(p.getUUID());
        clans.clanmembers.remove(clans.getClanMemberByUUID(p.getUUID()));
        return false;
    }

}
