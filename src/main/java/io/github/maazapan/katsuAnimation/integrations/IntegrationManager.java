package io.github.maazapan.katsuAnimation.integrations;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.integrations.bstats.Metrics;
import io.github.maazapan.katsuAnimation.integrations.spigot.SpigotUpdate;

public class IntegrationManager {

    private final KatsuAnimation plugin;

    public IntegrationManager(KatsuAnimation plugin) {
        this.plugin = plugin;
    }

    public void load() {
        Metrics metrics = new Metrics(plugin, 23858);


        new SpigotUpdate(plugin, 120684).getVersion(version -> {
            if (plugin.getDescription().getVersion().equals(version)) {
                plugin.getLogger().info("There is not a new update available.");

            } else {
                plugin.getLogger().info("There is a new update available https://www.spigotmc.org/resources/120684/");
            }
        });
    }
}

