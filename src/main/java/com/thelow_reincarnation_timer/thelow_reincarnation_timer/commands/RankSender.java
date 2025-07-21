package com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands;



import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.Secretdatas;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class RankSender {
    public static int timer = 0;

    public static void sendRank(UUID uuid,String mcid, int sword, int bow, int magic, int all) {
    	timer = 1;
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7データを送信中..."));
    	//少し待つ
    	while(timer!=0) {
    		try {
    	        Thread.sleep(50); // 50ms = 約1tick
    	    } catch (InterruptedException e) {
    	        e.printStackTrace();
    	    }
    	}
    	
    	String other = APIListener.getother();

    	
        try {
            // GASのURLに置き換える
            URL url = new URL("https://script.google.com/macros/s/"+Secretdatas.APIkey+"/exec");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            

            String jsonData = String.format(
                "{\"uuid\":\"%s\", \"mcid\":\"%s\", \"sword\":%d, \"bow\":%d, \"magic\":%d, \"all\":%d, \"other\":\"%s\"}",
                uuid, mcid, sword, bow, magic, all,other
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println("レスポンス" + code + ":" + conn.getResponseMessage());
            
            if (code/100==4) {
            	Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§a[thelow_reincarnation_timer]§cエラー: 送信に失敗しました"));
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§c" + code + ": " + conn.getResponseMessage()));
            }
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
            APIListener.latestData = null;

            
        } catch (Exception e) {
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§cエラー: 送信に失敗しました " + e.getClass().getSimpleName() + " - " + e.getMessage()));
        	APIListener.latestData = null;
        	e.printStackTrace();
        }
    }
}