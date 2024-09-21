package com.earthpol.barky;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Barky extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("bark")).setExecutor(new BarkCommand());
        Objects.requireNonNull(this.getCommand("bark")).setTabCompleter(new BarkTabCompleter());
        Objects.requireNonNull(this.getCommand("barky")).setExecutor(new BarkyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
