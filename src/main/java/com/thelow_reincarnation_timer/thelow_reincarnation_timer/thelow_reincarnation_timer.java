package com.thelow_reincarnation_timer.thelow_reincarnation_timer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.thelow_reincarnation_timer.thelow_reincarnation_timer.chat.ChatListener;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands.ReincarnationTimerCommand;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.config.ConfigHandler;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.hud.thelow_reincarnation_timerHUD;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.operation.Operation;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.timer.timer;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "thelow_reincarnation_timer", version = "1.0")
public class thelow_reincarnation_timer {
	public static class TimerData {
	    public static int time_tick = 0;
	    public static int timer_enable = 0;
	    public static String type = "none";
	    public static int time_tick_before = 0;
	    public static String type_before = "none";
	    public static List<Integer> ave_time_tick = Arrays.asList(0,0,0,0);
	    public static List<Integer> min_time_tick = Arrays.asList(0,0,0,0);
	    //最速タイムと平均タイムをリセットするコード
	    static {
	    Operation.CalculateTimerStats();	
	    }
	    
		}
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = event.getSuggestedConfigurationFile();
        ConfigHandler.loadConfig(configFile);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	thelow_reincarnation_timerHUD.register();
        ClientCommandHandler.instance.registerCommand(new ReincarnationTimerCommand());
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        MinecraftForge.EVENT_BUS.register(new timer());
    }
}