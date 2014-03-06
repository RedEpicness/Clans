package net.mcmortals.clans.commands;

import net.mcmortals.clans.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ClanCommand extends net.md_5.bungee.api.plugin.Command{

    Main clans;

    public ClanCommand(Main This) {
        super("clan", "Clans.default");
        clans = This;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(new ComponentBuilder("Not enough arguments, do ").color(ChatColor.RED).append("/clan help")
                    .color(ChatColor.AQUA).append(" for help.").color(ChatColor.RED).create());
            return;
        }
        String arg1 = args[0];
        if(arg1.equals("help")){
            if(args.length == 1){
                sender.sendMessage(new ComponentBuilder("/clan help <command>").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("Displays help for that command.").color(ChatColor.YELLOW).create());
                sender.sendMessage(new ComponentBuilder("/clan create").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("Creates a clan.").color(ChatColor.YELLOW).create());
                sender.sendMessage(new ComponentBuilder("/clan placeholder1").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("placeholder1").color(ChatColor.YELLOW).create());
                sender.sendMessage(new ComponentBuilder("/clan placeholder2").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("placeholder2").color(ChatColor.YELLOW).create());
                sender.sendMessage(new ComponentBuilder("/clan placeholder3").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("placeholder3").color(ChatColor.YELLOW).create());
                return;
            }
            String arg2 = args[1];
            if(arg2.equals("create")){
                sender.sendMessage(new ComponentBuilder("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("create").color(ChatColor.AQUA).create());
                sender.sendMessage(new ComponentBuilder("Usage: ").color(ChatColor.GOLD).append("/guild create <name>")
                        .color(ChatColor.AQUA).create());
            }
            else if(arg2.equals("placeholder1")){
                sender.sendMessage(new ComponentBuilder("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("placeholder1").color(ChatColor.AQUA).create());
                sender.sendMessage(new ComponentBuilder("Usage: ").color(ChatColor.GOLD).append("/guild placeholder1")
                        .color(ChatColor.AQUA).create());
            }
            else if(arg2.equals("placeholder2")){
                sender.sendMessage(new ComponentBuilder("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("placeholder2").color(ChatColor.AQUA).create());
                sender.sendMessage(new ComponentBuilder("Usage: ").color(ChatColor.GOLD).append("/guild placeholder2")
                        .color(ChatColor.AQUA).create());
            }
            else if(arg2.equals("placeholder3")){
                sender.sendMessage(new ComponentBuilder("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("placeholder3").color(ChatColor.AQUA).create());
                sender.sendMessage(new ComponentBuilder("Usage: ").color(ChatColor.GOLD).append("/guild placeholder3")
                        .color(ChatColor.AQUA).create());
            }
            else{
                sender.sendMessage(new ComponentBuilder("Unknown command: ").color(ChatColor.RED).append(arg2).color(ChatColor.AQUA).create());
            }

        }
    }
}
