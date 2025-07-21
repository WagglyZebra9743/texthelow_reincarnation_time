package com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.Secretdatas;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class APIListener {

    public static String latestData = null;
    

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText();

        if (msg.startsWith("$api")) {
            String[] split = msg.split(" ", 2);
            if (split.length == 2) {
                try {
                	JsonObject json = new JsonParser().parse(split[1]).getAsJsonObject();
                    String apiType = json.get("apiType").getAsString();
                    String vecter = json.get(Secretdatas.GetAPI3).getAsString();
                    if (Secretdatas.APItype.equals(apiType)) {
                        JsonObject response = json.getAsJsonObject("response");
                        latestData = apiType+response.get(Secretdatas.GetAPI).getAsString()+vecter;
                        
                    }
                } catch (Exception e) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§c 解析失敗: " + e.getMessage()));
                }
            }
        }
    }

    public static String getother() {
        return latestData;
    }
}
