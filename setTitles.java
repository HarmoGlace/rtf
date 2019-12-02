package fr.harmoglace.plugin;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class setTitles {
    public void sendTitle(Player player, int time, String title, String subtitle) {

        IChatBaseComponent basetitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");

        PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, basetitle);

//        if (subtitle != "") {
//            IChatBaseComponent bs = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
//
//
//            PacketPlayOutTitle bsp = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, bs);
//            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bsp);
//        }

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(titlepacket);
        sendTime(player, time);
    }


    private void sendTime(Player player, int ticks){
        PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);
    }


    }



