package com.thelow_reincarnation_timer.thelow_reincarnation_timer.operation;

import java.util.List;

import com.thelow_reincarnation_timer.thelow_reincarnation_timer.thelow_reincarnation_timer;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.config.ConfigHandler;

public class Operation {
	public static void CalculateTimerStats() {
		//変数初期化
		int n = 0;
    	int min_time_tick = 0;
    	int ave_time_tick = 0;
    	
    	//剣のデータを格納
    	List<Integer> time_sword = ConfigHandler.getTimeSwordList();
    	for(n=0;time_sword.size()>n;n++) {
    		ave_time_tick += time_sword.get(n);
    		if(time_sword.get(n)<min_time_tick||min_time_tick==0) {
    			min_time_tick = time_sword.get(n);
    		}
    	}
    	if(time_sword.size()!=0) {
    	ave_time_tick /= time_sword.size();
    	}else{
    		ave_time_tick = 0;
    	}
    	thelow_reincarnation_timer.TimerData.ave_time_tick.set(0,ave_time_tick);
    	thelow_reincarnation_timer.TimerData.min_time_tick.set(0,min_time_tick);
    	ave_time_tick = 0;
    	min_time_tick = 0;
    	
    	//弓のデータを格納
    	List<Integer> time_bow = ConfigHandler.getTimeBowList();
    	for(n=0;time_bow.size()>n;n++) {
    		ave_time_tick += time_bow.get(n);
    		if(time_bow.get(n)<min_time_tick||min_time_tick==0) {
    			min_time_tick = time_bow.get(n);
    		}
    	}
    	if(time_bow.size()!=0) {
    	ave_time_tick /= time_bow.size();
    	}else {
    		ave_time_tick = 0;
    	}
    	thelow_reincarnation_timer.TimerData.ave_time_tick.set(1,ave_time_tick);
    	thelow_reincarnation_timer.TimerData.min_time_tick.set(1,min_time_tick);
    	ave_time_tick = 0;
    	min_time_tick = 0;
    	
    	//魔法のデータを格納
    	
    	List<Integer> time_magic = ConfigHandler.getTimeMagicList();
    	for(n=0;time_magic.size()>n;n++) {
    		ave_time_tick += time_magic.get(n);
    		if(time_magic.get(n)<min_time_tick||min_time_tick==0) {
    			min_time_tick = time_magic.get(n);
    		}
    	}
    	if(time_magic.size()!=0) {
    	ave_time_tick /= time_magic.size();
    	}else {
    		ave_time_tick = 0;
    	}
    	thelow_reincarnation_timer.TimerData.ave_time_tick.set(2,ave_time_tick);
    	thelow_reincarnation_timer.TimerData.min_time_tick.set(2,min_time_tick);
    	ave_time_tick = 0;
    	min_time_tick = 0;
    	
    	//全転生のデータを格納
    	List<Integer> time_all = ConfigHandler.getTimeAllList();
    	for(n=0;time_all.size()>n;n++) {
    		ave_time_tick += time_all.get(n);
    		if(time_all.get(n)<min_time_tick||min_time_tick==0) {
    			min_time_tick = time_all.get(n);
    		}
    	}
    	if(time_all.size()!=0) {
    	ave_time_tick /= time_all.size();
    	}else {
    		ave_time_tick = 0;
    	}
    	thelow_reincarnation_timer.TimerData.ave_time_tick.set(3,ave_time_tick);
    	thelow_reincarnation_timer.TimerData.min_time_tick.set(3,min_time_tick);
    	
	}
	public static String TicktoTimer(int tick){
		int time_m = 0;
    	int time_s = 0;
    	int time_tick = tick;//tick秒を取得
    	time_m = time_tick / 1200;//分に変換
    	time_tick %= 1200;//変換分を減らす
    	time_s = time_tick / 20;//秒に変換
    	time_tick %= 20;//変換分を減らす
    	time_tick *= 5;
    	String text = String.format("%02d:%02d:%02d" , time_m, time_s,time_tick);
		return text;
		
	}

}
