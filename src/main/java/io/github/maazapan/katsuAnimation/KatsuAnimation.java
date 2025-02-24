package io.github.maazapan.katsuAnimation;

import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationLoader;
import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationManager;
import io.github.maazapan.katsuAnimation.animations.animation.task.AnimationTask;
import io.github.maazapan.katsuAnimation.animations.files.FilesManager;
import io.github.maazapan.katsuAnimation.animations.textures.host.TextureHost;
import io.github.maazapan.katsuAnimation.commands.KatsuCommand;
import io.github.maazapan.katsuAnimation.integrations.IntegrationManager;
import io.github.maazapan.katsuAnimation.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KatsuAnimation extends JavaPlugin {

    private FilesManager filesManager;
    private AnimationManager animationManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().warning("ProtocolLib is not installed, disabling plugin.");
            getLogger().warning("Download and install ProtocolLib from https://www.spigotmc.org/resources/protocollib.1997/");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        animationManager = new AnimationManager(this);
        filesManager = new FilesManager(this);
        filesManager.setup();

        this.registerCommands();
        this.registerListeners();

        IntegrationManager integrationManager = new IntegrationManager(this);
        integrationManager.load();

        TextureHost textureHost = new TextureHost(this);
        textureHost.start();

        AnimationLoader animationLoader = new AnimationLoader(this);
        animationLoader.load();

        AnimationTask animationTask = new AnimationTask(this);
        animationTask.runTaskTimer(this, 0, 1);
    }

    private void registerCommands() {
        getCommand("kta").setExecutor(new KatsuCommand(this));
        getCommand("kta").setTabCompleter(new KatsuCommand(this));
    }


    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public FilesManager getFilesManager() {
        return filesManager;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }
}


