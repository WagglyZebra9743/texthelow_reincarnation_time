package com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class RankSender {
    public static void sendRank(UUID uuid,String mcid, int sword, int bow, int magic, int all) {
        try {
            // GASのURLに置き換える
            URL url = new URL("https://script.google.com/macros/s/AKfycbyL3q-COrdeVAn5g1LuqzE5h6wZOklCo6dTl_zeaFBI1pWaYUXZcqslUX5oC72aa_i2/exec");

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
            System.out.println("送信結果: " + code);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7送信に成功しました"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
