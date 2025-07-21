package com.thelow_reincarnation_timer.thelow_reincarnation_timer.chat;

import java.util.List;

import com.thelow_reincarnation_timer.thelow_reincarnation_timer.thelow_reincarnation_timer;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatListener {

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
    	if (Minecraft.getMinecraft().thePlayer != null) {
        IChatComponent message = event.message;
        String text = message.getFormattedText(); // 色コードを含めてテキストを取得

        if (text.contains("§e[転生]")) {//前提として転生メッセージかどうか
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7転生を検知しました！"));

        	//タイムをconfigに記録
        	int n = 0;
        	int min_time_tick = 0;
        	int ave_time_tick = 0;
        	String type = "none";
        	if (thelow_reincarnation_timer.TimerData.timer_enable!=0) {
            	thelow_reincarnation_timer.TimerData.type_before = thelow_reincarnation_timer.TimerData.type;
            	thelow_reincarnation_timer.TimerData.time_tick_before = thelow_reincarnation_timer.TimerData.time_tick;
            	thelow_reincarnation_timer.TimerData.time_tick = 0;
        	if (thelow_reincarnation_timer.TimerData.type_before.equals("sword")) {
        		ConfigHandler.addCurrentTickToSword(thelow_reincarnation_timer.TimerData.time_tick_before);
        		//最速タイム計測
            	List<Integer> time_sword = ConfigHandler.getTimeSwordList();
            	for(n=0;time_sword.size()>n;n++) {
            		ave_time_tick += time_sword.get(n);
            		if(time_sword.get(n)<min_time_tick||min_time_tick==0) {
            			min_time_tick = time_sword.get(n);
            		}
            	}
            	ave_time_tick /= time_sword.size();
            	thelow_reincarnation_timer.TimerData.ave_time_tick.set(0, ave_time_tick);
            	type = "剣";
        	}
        	
        	if (thelow_reincarnation_timer.TimerData.type_before.equals("bow")) {
        		ConfigHandler.addCurrentTickToBow(thelow_reincarnation_timer.TimerData.time_tick_before);
        		//最速タイム計測
            	List<Integer> time_bow = ConfigHandler.getTimeBowList();
            	for(n=0;time_bow.size()>n;n++) {
            		ave_time_tick += time_bow.get(n);
            		if(time_bow.get(n)<min_time_tick||min_time_tick==0) {
            			min_time_tick = time_bow.get(n);
            		}
            	}
            	ave_time_tick /= time_bow.size();
            	thelow_reincarnation_timer.TimerData.ave_time_tick.set(1, ave_time_tick);
            	type = "弓";
        	}
        	
        	if (thelow_reincarnation_timer.TimerData.type_before.equals("magic")) {
        		ConfigHandler.addCurrentTickToMagic(thelow_reincarnation_timer.TimerData.time_tick_before);
        		//最速タイム計測
            	List<Integer> time_magic = ConfigHandler.getTimeMagicList();
            	for(n=0;time_magic.size()>n;n++) {
            		ave_time_tick += time_magic.get(n);
            		if(time_magic.get(n)<min_time_tick||min_time_tick==0) {
            			min_time_tick = time_magic.get(n);
            		}
            	}
            	ave_time_tick /= time_magic.size();
            	thelow_reincarnation_timer.TimerData.ave_time_tick.set(2, ave_time_tick);
            	type = "魔法";
        	}
        	
        	if (thelow_reincarnation_timer.TimerData.type_before.equals("all")) {
        		ConfigHandler.addCurrentTickToAll(thelow_reincarnation_timer.TimerData.time_tick_before);
        		//最速タイム計測
            	List<Integer> time_all = ConfigHandler.getTimeAllList();
            	for(n=0;time_all.size()>n;n++) {
            		ave_time_tick += time_all.get(n);
            		if(time_all.get(n)<min_time_tick||min_time_tick==0) {
            			min_time_tick = time_all.get(n);
            		}
            	}
            	ave_time_tick /= time_all.size();
            	thelow_reincarnation_timer.TimerData.ave_time_tick.set(3, ave_time_tick);
            	type = "全";
        	}
        	if(min_time_tick >= thelow_reincarnation_timer.TimerData.time_tick_before && min_time_tick != 0 && thelow_reincarnation_timer.TimerData.time_tick_before != 0) {
        		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7"+ type +"転生の最速タイムを更新しました"));
        	}
        	}
        	
            if (text.contains("§a剣")){
            	thelow_reincarnation_timer.TimerData.type_before = thelow_reincarnation_timer.TimerData.type;
            	thelow_reincarnation_timer.TimerData.type = "sword";
            	thelow_reincarnation_timer.TimerData.timer_enable = 1;
            } else if (text.contains("§a弓")) {
            	thelow_reincarnation_timer.TimerData.type_before = thelow_reincarnation_timer.TimerData.type;
            	thelow_reincarnation_timer.TimerData.type = "bow";
            	thelow_reincarnation_timer.TimerData.timer_enable = 1;
            } else if(text.contains("§a魔法")){
            	thelow_reincarnation_timer.TimerData.type_before = thelow_reincarnation_timer.TimerData.type;
            	thelow_reincarnation_timer.TimerData.type = "magic";
            	thelow_reincarnation_timer.TimerData.timer_enable = 1;
            } else if(text.contains("§a武器")) {
            	thelow_reincarnation_timer.TimerData.type_before = thelow_reincarnation_timer.TimerData.type;
            	thelow_reincarnation_timer.TimerData.type = "all";
            	thelow_reincarnation_timer.TimerData.timer_enable = 1;
            } else {
            	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§cエラー:不明な転生を取得しました"));
            }
            thelow_reincarnation_timer.TimerData.time_tick = 0;
            
            System.out.println("最速タイム" + min_time_tick);
            System.out.println("平均タイム"+ ave_time_tick);
            } else if (( thelow_reincarnation_timer.TimerData.type.equals("sword") || thelow_reincarnation_timer.TimerData.type.equals("bow") || thelow_reincarnation_timer.TimerData.type.equals("magic") ) && text.contains("レベルになった代わりにユニットを得ました")||thelow_reincarnation_timer.TimerData.type.equals("all") && text.contains("§aユニット +450 unit")) {
            	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7転生タイマーを起動しました.type:"+thelow_reincarnation_timer.TimerData.type));
            	thelow_reincarnation_timer.TimerData.timer_enable = 2;
            }
        	//ここからはthelowapiの取得処理
        	
    	}
    }
}