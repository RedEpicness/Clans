package net.mcmortals.clans;

public class ClanMember {

    private Main clans;
    private int ClanId;
    //private Rank rank;
    private String name;
    private String UUID;

    public ClanMember(Main clans, int ClanId, String name, String UUID){
        this.clans = clans;
        this.ClanId = ClanId;
        this.name = name;
        this.UUID = UUID;
    }

    public String getUUID(){
        return UUID;
    }

    public Clan getClan(){
        return clans.getClanById(ClanId);
    }

    public String getName(){
        return name;
    }
}
