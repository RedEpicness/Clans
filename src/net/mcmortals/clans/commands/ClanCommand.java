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
            help(sender, args);
        }
        else if(arg1.equals("create")){
            create(sender, args);
        }
        else if(arg1.equals("join")){
            join(sender, args);
        }
        else if(arg1.equals("leave")){
            leave(sender);
        }
        else if(arg1.equals("info")){
            info(sender);
        }
    }

    void help(CommandSender sender, String[] args){
        if(args.length == 1){
            sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help")
                    .color(ChatColor.GOLD).append(" -----").color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("/clan help <command>").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.AQUA).append("Displays help for that command.").color(ChatColor.YELLOW).create());
            sender.sendMessage(prefix().append("/clan create").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.AQUA).append("Creates a clan.").color(ChatColor.YELLOW).create());
            sender.sendMessage(prefix().append("/clan join").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.AQUA).append("Use to join a clan.").color(ChatColor.YELLOW).create());
            sender.sendMessage(prefix().append("/clan info").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.AQUA).append("info").color(ChatColor.YELLOW).create());
            sender.sendMessage(prefix().append("/clan leave").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.AQUA).append("leave").color(ChatColor.YELLOW).create());
            sender.sendMessage(prefix().append("--------------------").color(ChatColor.AQUA).create());
            return;
        }
        String arg2 = args[1];
        if(arg2.equals("create")){
            sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.RED).append("create").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/clan create <name>")
                    .color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
        }
        else if(arg2.equals("join")){
            sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.RED).append("join").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/clan join <id>")
                    .color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
        }
        else if(arg2.equals("info")){
            sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.RED).append("info").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/clan info")
                    .color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
        }
        else if(arg2.equals("leave")){
            sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clans help").color(ChatColor.GOLD).append(" - ")
                    .color(ChatColor.RED).append("leave").color(ChatColor.AQUA).append(" -----").color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/clan leave")
                    .color(ChatColor.AQUA).create());
            sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
        }
        else{
            sender.sendMessage(prefix().append("Unknown argument: ").color(ChatColor.RED).append(arg2).color(ChatColor.AQUA).create());
        }
    }

    void create(CommandSender sender, String[] args){
        if(args.length < 2){
            sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/clan create <name>")
                    .color(ChatColor.AQUA).create());
        }
        else{
            String name = args[1];
            Clan c;
            try{
                int id;
                try{
                    id = clans.clans.size();
                }
                catch(IndexOutOfBoundsException e){
                    id = 0;
                }
                if(clans.getClanMemberByUUID(clans.getProxy().getPlayer(sender.getName()).getUUID()) != null){
                    sender.sendMessage(prefix().append("You are already in a clan, so you cannot create a new one!").color(ChatColor.DARK_RED).create());
                    return;
                }
                Statement s = clans.c.createStatement();
                s.executeUpdate("INSERT INTO clanlist (id, Name, Coins) VALUES ("+id+", '"+name+"', 0);");
                c = new Clan(clans, 0, name, id);
                clans.clans.add(c);
                c.addPlayer(clans.getProxy().getPlayer(sender.getName()));
                s.close();
            }
            catch(SQLException e){
                clans.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
                sender.sendMessage(prefix().append("SQL Error!").color(ChatColor.DARK_RED).create());
                return;
            }
            sender.sendMessage(prefix().append("Succesfully created clan ").color(ChatColor.GREEN).append(name).color(ChatColor.GOLD)
                    .append(" ID:"+c.getId()).color(ChatColor.RED).create());
        }
    }

    void join(CommandSender sender, String[] args){
        if(args.length < 2){
            sender.sendMessage(prefix().append("Usage: ").color(ChatColor.GOLD).append("/clan join <id>").color(ChatColor.AQUA).create());
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
                if(tojoin.hasPlayer((ProxiedPlayer)sender)){
                    sender.sendMessage(prefix().append("You're already in this clan!").color(ChatColor.DARK_RED).create());
                }
                else if(clans.getClanMemberByUUID(clans.getProxy().getPlayer(sender.getName()).getUUID()) != null){
                    sender.sendMessage(prefix().append("You are already in a clan, so you cannot join a new one!").color(ChatColor.DARK_RED).create());
                }
                else if(!tojoin.hasPlayer((ProxiedPlayer)sender)){
                    tojoin.addPlayer((ProxiedPlayer)sender);
                    sender.sendMessage(prefix().append("Succesfully joined the clan ").color(ChatColor.GREEN).append(tojoin.getName()).color(ChatColor.GOLD).create());
                }
            }
        }
    }

    void leave(CommandSender sender){
        if(clans.getClanMemberByUUID(clans.getProxy().getPlayer(sender.getName()).getUUID()) == null){
            sender.sendMessage(prefix().append("You are not in a clan!").color(ChatColor.DARK_RED).create());
            return;
        }
        Clan c = clans.getClanMemberByUUID(clans.getProxy().getPlayer(sender.getName()).getUUID()).getClan();
        c.kickPlayer(clans.getProxy().getPlayer(sender.getName()));
        sender.sendMessage(prefix().append("You have left your clan!").color(ChatColor.GREEN).create());
    }

    void info(CommandSender sender){
        if(clans.getClanMemberByUUID(clans.getProxy().getPlayer(sender.getName()).getUUID()) == null){
            sender.sendMessage(prefix().append("You are not in a clan!").color(ChatColor.DARK_RED).create());
            return;
        }
        Clan c = clans.getClanMemberByUUID(clans.getProxy().getPlayer(sender.getName()).getUUID()).getClan();
        sender.sendMessage(prefix().append("----- ").color(ChatColor.AQUA).append("Clan info").color(ChatColor.GOLD).append(" -----").color(ChatColor.AQUA).create());
        sender.sendMessage(prefix().append("Name: ").color(ChatColor.BLUE).append(c.getName()).color(ChatColor.YELLOW).create());
        sender.sendMessage(prefix().append("Id: ").color(ChatColor.BLUE).append(c.getId() + "").color(ChatColor.YELLOW).create());
        sender.sendMessage(prefix().append("Coins: ").color(ChatColor.BLUE).append(c.getCoins()+"").color(ChatColor.YELLOW).create());
        ComponentBuilder members = prefix().append("Members: ").color(ChatColor.YELLOW);
        for(String a : c.getPlayers()){
            members.append(clans.getClanMemberByUUID(a).getName()).color(ChatColor.YELLOW).append(", ").color(ChatColor.GOLD);
        }
        sender.sendMessage(members.create());
        sender.sendMessage(prefix().append("------------------------------").color(ChatColor.AQUA).create());
    }
}
