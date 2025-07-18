package com.thelow_reincarnation_timer.thelow_reincarnation_timer.hud;

import com.thelow_reincarnation_timer.thelow_reincarnation_timer.thelow_reincarnation_timer;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.config.ConfigHandler;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.operation.Operation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class thelow_reincarnation_timerHUD extends Gui {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new thelow_reincarnation_timerHUD());
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;//プレイヤーまたはワールドがない場合は実行しない
        
        if (thelow_reincarnation_timer.TimerData.time_tick > 0) {
        	String type = thelow_reincarnation_timer.TimerData.type;
        	if (type.equals("sword")) {
        		type = "剣転生";
        	}else if(type.equals("bow")){
        		type = "弓転生";
        	}else if(type.equals("magic")) {
        		type = "魔法転生";
        	}else if(type.equals("all")) {
        		type = "全転生";
        	}else {
        		type = "謎転生";
        	}
        	String text = Operation.TicktoTimer(thelow_reincarnation_timer.TimerData.time_tick);
        	String time = String.format("%s:%s",type , text);//タイマー表示を作る
        	FontRenderer font = mc.fontRendererObj;
        	int x = ConfigHandler.hudX;//HUD表示位置指定
        	int y = ConfigHandler.hudY;//HUD表示位置指定
        	
        	int textWidth = font.getStringWidth(time);
        	int textHeight = font.FONT_HEIGHT;
        	int padding = 2;
        	Gui.drawRect(x - padding, y - padding, x + textWidth + padding, y + textHeight + padding, 0x50000000);
        	
        	
        	GlStateManager.pushMatrix();
        	mc.fontRendererObj.drawStringWithShadow(time , x, y, 0xFFFFFF);
        	GlStateManager.popMatrix();}
        if (thelow_reincarnation_timer.TimerData.time_tick_before > 0 && thelow_reincarnation_timer.TimerData.type_before.equals(thelow_reincarnation_timer.TimerData.type)) {

        	String text = Operation.TicktoTimer(thelow_reincarnation_timer.TimerData.time_tick_before);
        	String time_before = String.format("前回タイム:%s",text);
        	FontRenderer font = mc.fontRendererObj;
        	int x = ConfigHandler.hudX;//HUD表示位置指定
        	int y = ConfigHandler.hudY;//HUD表示位置指定
        	
        	int textWidth = font.getStringWidth(time_before);
        	int textHeight = font.FONT_HEIGHT;
        	int padding = 2;
        	Gui.drawRect(x - padding, y+13 - padding, x + textWidth + padding, y+13 + textHeight + padding, 0x50000000);
        	
        	GlStateManager.pushMatrix();
        	mc.fontRendererObj.drawStringWithShadow(time_before , x, y+13, 0xFFFFFF);
        	GlStateManager.popMatrix();
        	
        }
    }
}
