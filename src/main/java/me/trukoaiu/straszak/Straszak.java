package me.trukoaiu.straszak;

import me.trukoaiu.straszak.commands.strasz;
import me.trukoaiu.straszak.commands.straszTabComplete;
import org.bukkit.plugin.java.JavaPlugin;

public final class Straszak extends JavaPlugin {

    public static Straszak instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        instance = this;

        this.getCommand("strasz").setExecutor(new strasz());

        this.getCommand("strasz").setTabCompleter(new straszTabComplete());
    }

    public static Straszak getPlugin(){
        return instance;
    }

}
