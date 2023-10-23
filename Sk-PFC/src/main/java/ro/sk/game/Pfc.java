package ro.sk.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ro.sk.game.commands.pfc.PfcCmd;
import ro.sk.game.commands.pfc.PfcEvent;
import ro.skayizen.api.SkApi;
import ro.skayizen.api.commands.CommandsManager;
import ro.skayizen.api.logging.ConsoleLog;
import ro.skayizen.api.logging.ConsoleLogHandler;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class Pfc extends JavaPlugin {


    public ConsoleLog consoleLog;

    public static Pfc instance;
    public static Pfc getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Logger.getLogger("Sk-Pfc").addHandler((Handler) new ConsoleLogHandler());
        this.consoleLog = new ConsoleLog();
        this.consoleLog.setFormat("&3[&4Sk-Pfc&3]&r [{log_level}]: {log_message}");


        registerCommands();
        consoleLog.info("&2Events loaded");
        registerEvents();
        consoleLog.info("&2Commands loaded");

        consoleLog.info("&2Logger loaded");
    }

    @Override
    public void onDisable() {

    }


    public void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        SkApi.instance.eventsManager.addNewEvenListener(new PfcEvent(), getInstance(), pm);

    }

    public void registerCommands(){
        CommandsManager.registerNewCommand(new PfcCmd(this));
    }


}
