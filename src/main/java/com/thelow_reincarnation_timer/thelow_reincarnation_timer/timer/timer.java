package com.thelow_reincarnation_timer.thelow_reincarnation_timer.timer;

import com.thelow_reincarnation_timer.thelow_reincarnation_timer.thelow_reincarnation_timer;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands.RankSender;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class timer {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
    		
    	if (RankSender.timer!=0) {
    		RankSender.timer++;
    		if(RankSender.timer >= 20) {
    			RankSender.timer = 0;
    		}
    	}
        if (event.phase != TickEvent.Phase.START) return;

        if (mc.thePlayer != null && mc.theWorld != null && thelow_reincarnation_timer.TimerData.timer_enable == 2) {
        	thelow_reincarnation_timer.TimerData.time_tick++;
        }
    }
}