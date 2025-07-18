package com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands;



import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class RankSender {
    public static void sendRank(UUID uuid,String mcid, int sword, int bow, int magic, int all) {
        try {
            // GASのURLに置き換える
            URL url = new URL("https://script.google.com/macros/s/AKfycbwfGy2bsiZ8qkHqCsqRA6QRAr5kJJ2YeQnTI-kOzVEhqKmB_h1U9AP6VjhIkHydTU3d/exec");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            

            String jsonData = String.format(
                "{\"uuid\":\"%s\", \"mcid\":\"%s\", \"sword\":%d, \"bow\":%d, \"magic\":%d, \"all\":%d}",
                uuid, mcid, sword, bow, magic, all
            );
            System.out.println(jsonData);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println("レスポンス" + code + ":" + conn.getResponseMessage());

            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            JsonObject responseJson = new JsonParser().parse(reader).getAsJsonObject();
            int status = responseJson.get("status").getAsInt();
            String message = responseJson.get("message").getAsString();

            if (status == 200) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§a[thelow_reincarnation_timer]§7送信に成功しました"));
            } else if (status / 100 == 4) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§a[thelow_reincarnation_timer]§cエラー: 送信に失敗しました"));
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§c" + status + ": " + message));
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
