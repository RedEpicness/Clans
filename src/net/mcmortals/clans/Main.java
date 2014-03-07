package net.mcmortals.clans;

import net.mcmortals.clans.commands.ClanCommand;
import net.mcmortals.clans.mysql.MySQL;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;

public class Main extends Plugin{

    MySQL sql;
    Connection c;

    @Override
    public void onEnable(){
        sql = new MySQL(this, "127.0.0.0", "23456", "database", "user", "pass");
        c = sql.openConnection();
        getProxy().getPluginManager().registerCommand(this, new ClanCommand(this));
        //TODO add all clans from databases into an array maybe, and create all guild objects
    }

}
