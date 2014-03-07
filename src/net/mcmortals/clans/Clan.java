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
    //private Map<String, Rank> rankslist;

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
        members.add(p.getUUID());
        try{
            Statement s = clans.c.createStatement();
            s.executeQuery("INSERT INTO clanmembers VALUES (" + p.getName() + ", " + getId() + ");");
            s.close();
        }
        catch(SQLException e){
            clans.getLogger().log(Level.WARNING, e.getMessage());
        }

        return true;
    }

    public boolean kickPlayer(ProxiedPlayer p){
        if(members.contains(p.getUUID())){
            members.add(p.getUUID());
            return true;
        }
        return false;
    }

}
