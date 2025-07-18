package com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.operation.Operation;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class RankGetter {

    public static class RankEntry {
        public String uuid;
        public String mcid;
        public int time;

        public RankEntry(String uuid,String mcid, int time) {
            this.uuid = uuid;
            this.mcid = mcid;
            this.time = time;
        }
    }

    private static final String API_URL = "https://script.google.com/macros/s/AKfycbwfGy2bsiZ8qkHqCsqRA6QRAr5kJJ2YeQnTI-kOzVEhqKmB_h1U9AP6VjhIkHydTU3d/exec";

    // このメソッドだけ公開し、コマンドから呼び出す
    public static void fetchAndDisplayRanks(String category, ICommandSender sender, int count) {
    	
    	EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
        String selfUUID = player.getUniqueID().toString();
        
        // 通信処理は別スレッドで
        new Thread(() -> {
            List<RankEntry> ranks = getRankData(category);

            if (ranks == null || ranks.isEmpty()) {
                Minecraft.getMinecraft().addScheduledTask(() ->
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§c ランキングデータの取得に失敗しました 時間を空けて再度実行してください")));		                
                return;
            }

            // チャット出力はメインスレッドで
            Minecraft.getMinecraft().addScheduledTask(() -> {
            	String type = "";
            	if(category.equals("sword")) {
            		type = "剣";
            	}else if(category.equals("bow")) {
            		type = "弓";
            	}else if(category.equals("magic")) {
            		type = "魔法";
            	}else if(category.equals("all")) {
            		type = "全";
            	}
            	sender.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7データを取得しました"));
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§6～～～～" + type + "転生タイムランキングTOP"+count+"§6～～～～"));
                
                for (int i = 0; i < Math.min(count, ranks.size()); i++) {
                    RankEntry entry = ranks.get(i);
                   
                        sender.addChatMessage(new ChatComponentText("§e"+ (i+1) + "位" + entry.mcid + "§7: " + formatTicks(entry.time)));
                    
                }
                
                for (int i = 0; i < ranks.size(); i++) {

                    if (ranks.get(i).uuid.equals(selfUUID)) {
                sender.addChatMessage(new ChatComponentText("§6～～～～～～～～～～～～～～～～～"));

                        if (i > 0) {
                            RankEntry above = ranks.get(i - 1);
                            

                                sender.addChatMessage(new ChatComponentText("§e"+(i)+"位" + above.mcid + "§7: " + formatTicks(above.time)));
                        } else {
                            sender.addChatMessage(new ChatComponentText("§7現在1位です"));
                        }
                        sender.addChatMessage(new ChatComponentText("§a現在の順位: §e" + (i + 1) + "位 - " + formatTicks(ranks.get(i).time)));
                        break;
                    }
                }
            });
        }).start();
    }

 // HTTP通信 & JSONパース処理
    private static List<RankEntry> getRankData(String category) {
        List<RankEntry> ranks = new ArrayList<>();
        try {
            String urlStr = API_URL + "?action=get&category=" + category;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            in.close();

            // JSONパース
            Gson gson = new Gson();
            List<RankEntry> allEntries = gson.fromJson(result.toString(), new TypeToken<List<RankEntry>>() {}.getType());

            // tick = 0 を除外
            for (RankEntry entry : allEntries) {
                if (entry.time > 0) {
                    ranks.add(entry);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ranks;
    }
    
 // tick を 00:00:00 表示に変換するユーティリティメソッド
    private static String formatTicks(int ticks) {
    	String text = Operation.TicktoTimer(ticks);
        return String.format("%s", text);
    }

}
