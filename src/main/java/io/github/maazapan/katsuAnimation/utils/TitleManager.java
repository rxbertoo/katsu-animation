package io.github.maazapan.katsuAnimation.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TitleManager {

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public void sendTitle(Player player, TextComponent textComponent) {
        String jsonTitle = String.format("{\"text\":\"%s\",\"font\":\"%s\"}", textComponent.getText(), textComponent.getFont());

        PacketContainer packetTitle = protocolManager.createPacket(PacketType.Play.Server.SET_TITLE_TEXT);
        packetTitle.getChatComponents()
                .write(0, WrappedChatComponent.fromJson(jsonTitle));

        protocolManager.sendServerPacket(player, packetTitle);

        PacketContainer packetTime = protocolManager.createPacket(PacketType.Play.Server.SET_TITLES_ANIMATION);
        packetTime.getIntegers()
                .write(0, 0)
                .write(1, 20)
                .write(2, 0);

        protocolManager.sendServerPacket(player, packetTime);
    }
}
