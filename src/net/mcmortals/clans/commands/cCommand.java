package net.mcmortals.clans.commands;

import net.mcmortals.clans.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class cCommand extends Command {

    Main clans;

    public cCommand(Main This) {
        super("c", "");
        this.clans = This;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ClanCommand cmd = new ClanCommand(clans);
        cmd.execute(commandSender, args);
    }
}
