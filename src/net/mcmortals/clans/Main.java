package net.mcmortals.clans;

import net.mcmortals.clans.commands.ClanCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{

    @Override
    public void onEnable(){
        getProxy().getPluginManager().registerCommand(this, new ClanCommand(this));
//TODO add all clans from databases into an arrray maybe?? Idk
    }

}
