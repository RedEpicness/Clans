package net.mcmortals.clans.commands;

import net.mcmortals.clans.Main;
import net.md_5.bungee.api.CommandSender;

public class ClanCommand extends net.md_5.bungee.api.plugin.Command{

    Main clans;

    public ClanCommand(Main This) {
        super("clan", "Clans.default");
        clans = This;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
