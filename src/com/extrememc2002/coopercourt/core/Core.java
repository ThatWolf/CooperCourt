package com.extrememc2002.coopercourt.core;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.matejdro.bukkit.jail.JailAPI;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Core extends JavaPlugin implements CommandExecutor {
	
	
// Permissions: 
// court.user (/sue, /court judges) 
	
// court.admin (/court reload, /court config, /court setjudge)

// court.judge (/court jail, /court fine, /court list) 
	
	JailAPI jail;
	
	public static Economy econ = null;
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		this.jailTime = getConfig().getInt("DefaultJailTime");
		this.fineAmount = getConfig().getInt("DefaultFine");
		
		
	}
	
	public void onDisable() {
		
	}
	
	int jailTime = 10;
	int fineAmount = 2000;
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("court")) {
			if (args[0].equalsIgnoreCase("help")) {

				p.sendMessage("§c§lCourt plugin developed by: ExtremeMC2002, and Wolf_Shephard.");
				p.sendMessage("");
				p.sendMessage("§6Commands:");
				p.sendMessage("");
				p.sendMessage("§6/sue <player> <amount>");
				p.sendMessage("§6/court judges");
			
				PermissionUser user1 = PermissionsEx.getUser(p);
				
				if(user1.inGroup("Judge")) {
					
					p.sendMessage("§6/court jail <player>");
					p.sendMessage("§6/court fine <player> <amount>");
					p.sendMessage("§6/court list");
					}
						
				if(p.isOp()) { 
						
					p.sendMessage("§6/court reload");
					p.sendMessage("§6/court setjudge <player>");
					
				}
			}
						
			if (args[0].equalsIgnoreCase("jail")) {
				
				if (args.length > 1) {
						
					String playerName = args[1];
						
					String reason = "Found Guilty";
							
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jail " + playerName + " " + this.jailTime + " r:" + reason);
					p.sendMessage("§c[§eCooperCraft§c] Successfully jailed §e" + playerName + " §cbecause they were found guilty!");
					Bukkit.broadcastMessage("§c[§eCooperCraft§c] §e" + playerName + " §c was just found guilty by Judge §e" + sender + " §cand sent to federal prison for their crime.");
				}
				else {
					p.sendMessage("§c[§eCooperCraft§c] Please specify a player to jail!");
					}
				}
			
			if (args[0].equalsIgnoreCase("reload")) {
					
				p.sendMessage("§c[§eCooperCraft§c] Successfully reloaded the config");
			
			}
			
			if (args[0].equalsIgnoreCase("config")) {
				
				p.sendMessage("§6Config Values:");
				p.sendMessage("");
				p.sendMessage("§cPrison Time:§e " + this.jailTime);
				p.sendMessage("§cCourt Default Fine Amount:§e " + this.fineAmount);
			}
				
			if (args[0].equalsIgnoreCase("fine")) {
					
				if (args.length > 1) {
						
					if (args.length == 3) {
							
						String takePlayer1 = args[1];
					        
						if (isInt(args[2])) {
							int customFineAmount = Integer.parseInt(args[2]);
					            
							Player finedPlayer1 = Bukkit.getPlayerExact(args[1]);
							
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "eco take " + finedPlayer1.getName() + " " + customFineAmount);
						    	
						    finedPlayer1.sendMessage("§c[§eCooperCraft§c] The Cooper Nation Court of law has fined you §e" + customFineAmount + " §c.");
						    p.sendMessage("§c[§eCooperCraft§c] You have fined §e" + finedPlayer1.getName() + " §cfor §e$" + customFineAmount + "§c.");
					            
						}
						else {
							p.sendMessage("§c[§eCooperCraft§c] Error, that is not a valid amount of money.");
						}
					}
					else {
						p.sendMessage("§c[§eCooperCraft§c] Error, please do the command (/court fine <player> <amount>");
					}
				}
			}
			return true;
		}
		return false;
	}
}
