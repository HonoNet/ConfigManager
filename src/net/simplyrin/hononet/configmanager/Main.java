package net.simplyrin.hononet.configmanager;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.config.Configuration;
import net.simplyrin.config.Config;

/**
 * Created by SimplyRin on 2018/02/02.
 */
public class Main extends JavaPlugin implements Listener {

	/**
	 * このプラグインは 2017/10/29 に作成されたプラグインを元に再構成されたものです。
	 */
	private static Main plugin;

	@Override
	public void onEnable() {
		plugin = this;
		if(!plugin.getDescription().getAuthors().contains("SimplyRin")) {
			plugin.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		plugin.getServer().getPluginManager().registerEvents(this, this);
		plugin.getCommand("configmanager").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("configmanager.use")) {
			sender.sendMessage(this.getPrefix() + "§cYou do not have access to this command");
			sender.sendMessage(this.getPrefix() + "§cGitHub: §nhttps://github.com/HonoNet/ConfigManager");
			return true;
		}

		if(args.length > 2) {
			String path = args[0];
			String param = "";
			for (int i = 2; i < args.length; i++) {
				param = param + args[i] + " ";
			}
			int length = param.length();
			length--;
			param = param.substring(0, length);

			File file = new File(path);
			if (!file.exists()) {
				sender.sendMessage(this.getPrefix() + "§cこのファイルは存在しません！");
				return true;
			}

			Configuration config = Config.getConfig(file);
			config.set(args[1], param);
			Config.saveConfig(config, file);

			sender.sendMessage(this.getPrefix() + "§a対象の Config へのパラメータを設定しました。");
			return true;
		}
		sender.sendMessage(this.getPrefix() + "§cUsage: /" + cmd.getName() + " <ファイルパス> <Configパス> <パラメータ>");
		return true;
	}

	public String getPrefix() {
		return "§7[§cConfigManager§7] §r";
	}

}
