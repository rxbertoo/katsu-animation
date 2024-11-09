package io.github.maazapan.katsuAnimation.commands;

import io.github.maazapan.katsuAnimation.KatsuAnimation;
import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationLoader;
import io.github.maazapan.katsuAnimation.animations.animation.manager.AnimationManager;
import io.github.maazapan.katsuAnimation.animations.animation.type.AnimationType;
import io.github.maazapan.katsuAnimation.animations.textures.TexturesManager;
import io.github.maazapan.katsuAnimation.animations.textures.host.TextureHost;
import io.github.maazapan.katsuAnimation.utils.KatsuUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class KatsuCommand implements CommandExecutor, TabCompleter {

    private final KatsuAnimation plugin;

    public KatsuCommand(KatsuAnimation plugin) {
        this.plugin = plugin;
    }


    @Override
    @SuppressWarnings("all")
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        FileConfiguration config = plugin.getConfig();

        if (!(args.length > 0)) {
            sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args")));
            return true;
        }

        switch (args[0].toLowerCase()) {

            /*
             * This command is used to compile the resource pack.
             * + Permission: katsuanimation.cmd.compile
             * - Command: /kta compile
             */
            case "compile": {
                if (!sender.hasPermission("katsuanimation.cmd.compile")) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-permission")));
                    return true;
                }

                TexturesManager texturesManager = new TexturesManager(plugin);

                try {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.compile")));
                    texturesManager.compile();

                } catch (Exception e) {
                    sender.sendMessage(KatsuUtils.hex("&cAn error occurred while compiling the resource pack, see console for more information."));
                    e.printStackTrace();
                }
            }
            break;

            /*
             * This command is used to create the resource pack.
             * + Permission: katsuanimation.cmd.create
             * - Command: /kta create <gif> <size> <ascent>
             */
            case "create": {
                if (!sender.hasPermission("katsuanimation.cmd.create")) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-permission")));
                    return true;
                }

                if (!(args.length > 3)) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-create")));
                    return true;
                }

                String gif = args[1];

                int size = Integer.parseInt(args[2]);
                int ascent = Integer.parseInt(args[3]);

                if (!Files.exists(Paths.get(plugin.getDataFolder() + "/gifs/" + gif))) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.gif-not-found")));
                    return true;
                }

                AnimationManager animationManager = plugin.getAnimationManager();
                TexturesManager texturesManager = new TexturesManager(plugin);

                if (animationManager.getAnimation(KatsuUtils.formatGifName(gif)) != null) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.gif-exist")));
                    return true;
                }

                try {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.create-texture")));
                    texturesManager.createTexture(gif, ascent, size);

                    AnimationLoader animationLoader = new AnimationLoader(plugin);
                    animationLoader.load();

                } catch (Exception e) {
                    sender.sendMessage(KatsuUtils.hex("&cAn error occurred while creating the resource pack, see console for more information."));
                    e.printStackTrace();
                }
            }
            break;

            /*
             * This command is used to delete a texture.
             * + Permission: katsuanimation.cmd.delete
             * - Command: /kta delete <gif>
             */
            case "delete": {
                if (!sender.hasPermission("katsuanimation.cmd.delete")) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-permission")));
                    return true;
                }

                if (!(args.length > 1)) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-delete")));
                    return true;
                }

                AnimationManager animationManager = plugin.getAnimationManager();
                String gif = args[1];

                if (animationManager.getAnimation(gif) == null) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.gif-not-exist")));
                    return true;
                }

                sender.sendMessage(KatsuUtils.hex(config.getString("messages.delete-texture")));
                animationManager.deleteAnimation(gif);
            }
            break;

            /*
             * This command is used to apply the resource pack.
             * + Permission: katsuanimation.cmd.apply
             * - Command: /kta apply <player>
             */
            case "apply": {
                if (!sender.hasPermission("katsuanimation.cmd.apply")) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-permission")));
                    return true;
                }

                if (!(args.length > 1)) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-apply")));
                    return true;
                }

                if (TextureHost.TEXTURE_PACK_URL == null) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.texture-host-not-started")));
                    return true;
                }

                try {
                    if (!args[1].equalsIgnoreCase("all")) {
                        if (Bukkit.getPlayer(args[1]) == null) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.player-not-found")));
                            return true;
                        }

                        Player player = Bukkit.getPlayer(args[1]);
                        player.setResourcePack(TextureHost.TEXTURE_PACK_URL);
                        sender.sendMessage(KatsuUtils.hex(config.getString("messages.apply-texture").replaceAll("%player%", player.getName())));
                        return true;
                    }

                    Bukkit.getOnlinePlayers().forEach(player -> player.setResourcePack(TextureHost.TEXTURE_PACK_URL));
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.apply-texture-all")));

                } catch (Exception e) {
                    sender.sendMessage(KatsuUtils.hex("&cAn error occurred while applying the resource pack, see console for more information."));
                    e.printStackTrace();
                }
            }
            break;

            /*
             * This command is used to play the animation.
             * + Permission: katsuanimation.cmd.animation
             * - Command: /kta animation <play/stop> <name>
             */
            case "animation": {
                if (!sender.hasPermission("katsuanimation.cmd.play")) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-permission")));
                    return true;
                }

                if (!(args.length > 1)) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-animation")));
                    return true;
                }

                AnimationManager animationManager = plugin.getAnimationManager();

                switch (args[1].toLowerCase()) {
                    /*
                     * This command is used to stop the animation.
                     * - Command: /kta animation play <gif> <type> <repeat> <update-tick> <player>
                     */
                    case "play": {
                        if (!(args.length > 6)) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-animation-play")));
                            return true;
                        }

                        if (animationManager.getAnimation(args[2]) == null) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.gif-not-exist")));
                            return true;
                        }

                        if (AnimationType.get(args[3]) == null) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.animation-type-error")));
                            return true;
                        }

                        if (!KatsuUtils.isBool(args[4])) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.boolean-error")));
                            return true;
                        }

                        if(!KatsuUtils.isNumber(args[5])) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.number-error")));
                            return true;
                        }

                        AnimationType type = AnimationType.get(args[3]);
                        String name = args[2];

                        boolean repeat = Boolean.parseBoolean(args[4]);
                        int updateTick = Integer.parseInt(args[5]);

                        if (!args[6].equalsIgnoreCase("all")) {
                            if (Bukkit.getPlayer(args[6]) == null) {
                                sender.sendMessage(KatsuUtils.hex(config.getString("messages.player-not-found")));
                                return true;
                            }
                            Player target = Bukkit.getPlayer(args[6]);

                            if (animationManager.isActiveAnimation(target.getUniqueId())) {
                                sender.sendMessage(KatsuUtils.hex(config.getString("messages.active-animation")));
                                return true;
                            }
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.play-animation").replaceAll("%animation%", name)));
                            animationManager.play(target, name, type, repeat, updateTick);
                            return true;
                        }

                        // Play the animation for all players.
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if (animationManager.isActiveAnimation(player.getUniqueId())) {
                                animationManager.finish(player.getUniqueId());
                            }
                            animationManager.play(player, name, type, repeat, updateTick);
                        });
                        sender.sendMessage(KatsuUtils.hex(config.getString("messages.play-animation-all").replaceAll("%animation%", name)));
                    }
                    break;

                    /*
                     * This command is used to stop the animation.
                     * - Command: /kta animation stop <player>

                     */
                    case "stop": {
                        if (!(args.length > 2)) {
                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-animation-stop")));
                            return true;
                        }

                        if (!args[2].equalsIgnoreCase("all")) {
                            if (Bukkit.getPlayer(args[2]) == null) {
                                sender.sendMessage(KatsuUtils.hex(config.getString("messages.player-not-found")));
                                return true;
                            }

                            Player target = Bukkit.getPlayer(args[2]);

                            if (!animationManager.isActiveAnimation(target.getUniqueId())) {
                                sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-active-animation")));
                                return true;
                            }

                            sender.sendMessage(KatsuUtils.hex(config.getString("messages.stop-animation")));
                            animationManager.finish(target.getUniqueId());
                            return true;
                        }

                        // Stop the animation for all players.
                        Bukkit.getOnlinePlayers().stream()
                                .filter(player -> animationManager.isActiveAnimation(player.getUniqueId()))
                                .forEach(player -> animationManager.finish(player.getUniqueId()));

                        sender.sendMessage(KatsuUtils.hex(config.getString("messages.stop-animation-all")));
                    }
                    break;

                    default: {
                        sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args-animation")));
                    }
                    break;
                }
            }
            break;


            /*
             * This command is used to reload the plugin configuration.
             * + Permission: katsuanimation.cmd.reload
             * - Command: /kta reload
             */
            case "reload": {
                if (!sender.hasPermission("katsuanimation.cmd.reload")) {
                    sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-permission")));
                    return true;
                }
                sender.sendMessage(KatsuUtils.hex(config.getString("messages.reload")));

                plugin.reloadConfig();
                plugin.saveDefaultConfig();
                break;
            }

            default: {
                sender.sendMessage(KatsuUtils.hex(config.getString("messages.no-args")));
                break;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("all")
    public List<String> onTabComplete(CommandSender sender, Command c, String s, String[] args) {
        AnimationManager animationManager = plugin.getAnimationManager();

        List<String> empty = Arrays.asList("", "");

        List<String> completions = Arrays.asList("compile", "reload", "create", "delete", "apply", "animation");
        List<String> gifNames = animationManager.getAnimations().stream()
                .map(animation -> animation.getName()).toList();

        List<String> players = Stream.concat(
                Bukkit.getOnlinePlayers().stream().map(Player::getName),
                Stream.of("All")).toList();

        File file = new File(plugin.getDataFolder() + "/gifs/");
        List<String> gifFiles = Arrays.asList(file.list());

        if (args[0].equalsIgnoreCase("apply")) {
            if (args.length == 2) return players;
        }

        if (args.length == 1) return completions;
        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "create":
                    return gifFiles;
                case "delete":
                case "play":
                    return gifNames;
                case "animation":
                    return Arrays.asList("stop", "play");
                default:
                    return empty;
            }
        }

        if (args[0].equalsIgnoreCase("apply")) {
            if (args.length == 2) return players;
        }

        if (args[1].equalsIgnoreCase("stop")) {
            if (args.length == 3) return players;
        }

        // Command: /kta animation play <gif> <type> <repeat> <tick-update> <player>
        if (args[0].equalsIgnoreCase("animation") && args[1].equalsIgnoreCase("play")) {
            if (args.length == 3) return gifNames;
            if (args.length == 4) return Arrays.stream(AnimationType.values()).map(type -> type.name()).toList();
            if (args.length == 5) return Arrays.asList("true", "false");
            if (args.length == 6) return Arrays.asList("16", "8", "5", "4", "2", "0");
            if (args.length == 7) return players;
        }

        // Command: /kta create <gif> <size> <ascent>
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length == 3) return Arrays.asList("8", "16", "32", "64", "128", "255");
            if (args.length == 4) return Arrays.asList("0");
        }
        return empty;
    }
}
