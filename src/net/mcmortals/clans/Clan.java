package net.mcmortals.clans;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class Clan{

    private int coins;
    private String name;
    private String prefix;
    private int id;
    private ArrayList<String> members = new ArrayList<String>();
    //private ArrayList<Rank> ranks;
    //private Map<String, Rank> rankslist;

    Clan(int coins, String name/*, String prefix*/, int id, boolean New){
        this.coins = coins;
        this.id = id;
        this.name = name;
        //this.prefix = prefix;
        if(New){
            //TODO add clan to database
        }
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

    public boolean addPlayer(ProxiedPlayer p){
        if(members.contains(p.getUUID())){
            return false;
        }
        members.add(p.getUUID());
        return true;
        //TODO add database stuff here
    }

    public boolean kickPlayer(ProxiedPlayer p){
        if(members.contains(p.getUUID())){
            members.add(p.getUUID());
            return true;
        }
        return false;
    }

}
