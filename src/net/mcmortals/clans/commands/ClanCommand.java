package net.mcmortals.clans.commands;

import net.mcmortals.clans.Clan;
import net.mcmortals.clans.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class ClanCommand extends net.md_5.bungee.api.plugin.Command{

    Main clans;

    public ClanCommand(Main This) {
        super("clan", "");
        clans = This;
    }

    ComponentBuilder prefix(){
        return new ComponentBuilder("[").color(ChatColor.DARK_RED).append("Clan").color(ChatColor.RED).append("] ").color(ChatColor.DARK_RED);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(prefix().append("Not enough arguments, do ").color(ChatColor.RED).append("/clan help")
                    .color(ChatColor.AQUA).append(" for help.").color(ChatColor.RED).create());
            return;
        }
        String arg1 = args[0];
        if(arg1.equals("help")){
            if(args.length == 1){
                sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help")
                        .color(ChatColor.GOLD).append(" -----").color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("/clan help <command>").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("Displays help for that command.").color(ChatColor.YELLOW).create());
                sender.sendMessage(prefix().append("/clan create").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("Creates a clan.").color(ChatColor.YELLOW).create());
                sender.sendMessage(prefix().append("/clan placeholder1").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("placeholder1").color(ChatColor.YELLOW).create());
                sender.sendMessage(prefix().append("/clan placeholder2").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("placeholder2").color(ChatColor.YELLOW).create());
                sender.sendMessage(prefix().append("/clan placeholder3").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.AQUA).append("placeholder3").color(ChatColor.YELLOW).create());
                sender.sendMessage(prefix().append("--------------------").color(ChatColor.AQUA).create());
                return;
            }
            String arg2 = args[1];
            if(arg2.equals("create")){
                sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("create").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/guild create <name>")
                        .color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
            }
            else if(arg2.equals("placeholder1")){
                sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("placeholder1").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/guild placeholder1")
                        .color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
            }
            else if(arg2.equals("placeholder2")){
                sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("placeholder1").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/guild placeholder2")
                        .color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
            }
            else if(arg2.equals("placeholder3")){
                sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                        .color(ChatColor.RED).append("placeholder1").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/guild placeholder3")
                        .color(ChatColor.AQUA).create());
                sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
            }
            else{
                sender.sendMessage(prefix().append("Unknown argument: ").color(ChatColor.RED).append(arg2).color(ChatColor.AQUA).create());
            }

        }
        else if(arg1.equals("create")){
            if(args[1] == null){
                sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/guild create <name>")
                        .color(ChatColor.AQUA).create());
            }
            else{
                String name = args[1];
                Clan c;
                try{
                    Statement s = clans.c.createStatement();
                    s.executeQuery("INSERT INTO clanlist VALUES ("+clans.clans.size()+", "+name+", 0);");

                    c = new Clan(clans, 0, name, clans.clans.size());
                    clans.clans.set(c.getId(), c);
                    c.addPlayer((ProxiedPlayer)sender);
                    s.close();
                }
                catch(SQLException e){
                    clans.getLogger().log(Level.WARNING, e.getMessage());
                    sender.sendMessage(prefix().append("SQL Error!").color(ChatColor.DARK_RED).create());
                    return;
                }
                sender.sendMessage(prefix().append("Succesfully created clan: ").color(ChatColor.GREEN).append(name).color(ChatColor.GOLD).append(" ID:"+c.getId()).create());
            }
        }
        else if(arg1.equals("join")){
            if(args[1] == null){
                sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/guild join <id>").color(ChatColor.AQUA).create());
            }
            else{
                int id;
                try{
                    id = Integer.parseInt(args[1]);
                }
                catch(Exception e){
                    sender.sendMessage(prefix().append(args[1]+" is not a number!").color(ChatColor.DARK_RED).create());
                    return;
                }
                Clan tojoin = clans.getClanById(id);
                if(tojoin == null){
                    sender.sendMessage(prefix().append("No clan by that id!").color(ChatColor.DARK_RED).create());
                }
                else{
                    tojoin.addPlayer((ProxiedPlayer)sender);
                    sender.sendMessage(prefix().append("Succesfully joined ").color(ChatColor.GREEN).append(tojoin.getName()).color(ChatColor.GOLD).create());
                }
            }
        }
    }
}
