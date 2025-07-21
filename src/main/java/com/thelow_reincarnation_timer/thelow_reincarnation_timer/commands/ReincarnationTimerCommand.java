package com.thelow_reincarnation_timer.thelow_reincarnation_timer.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.thelow_reincarnation_timer.thelow_reincarnation_timer.Secretdatas;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.thelow_reincarnation_timer;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.config.ConfigHandler;
import com.thelow_reincarnation_timer.thelow_reincarnation_timer.operation.Operation;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;



public class ReincarnationTimerCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "reincarnation_timer";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("timer");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/reincarnation_timer <place/stop/info/rank_send/rank_get/help>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("§c使用方法: /reincarnation_timer <place/stop/info/rank_send/rank_get/help>"));
            return;
        }

        String sub = args[0];

        switch (sub.toLowerCase()) {
            case "place":
                if (args.length != 3) {
                    sender.addChatMessage(new ChatComponentText("§c使用方法: /reincarnation_timer place <x> <y>"));
                    return;
                }
                try {
                    int x = Integer.parseInt(args[1]);
                    int y = Integer.parseInt(args[2]);

                    ConfigHandler.hudX = x;
                    ConfigHandler.hudY = y;
                    ConfigHandler.save();

                    sender.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7表示位置を(" + x + ", " + y + ")に変更しました。"));
                } catch (NumberFormatException e) {
                    sender.addChatMessage(new ChatComponentText("§c数値が無効です。整数で指定してください。"));
                }
                break;

            case "stop":
                thelow_reincarnation_timer.TimerData.timer_enable = 0;
                sender.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7タイマーを停止しました。"));
                break;

            case "help":
            	
                sender.addChatMessage(new ChatComponentText("§a---- [thelow_reincarnation_timer] コマンド一覧 ----"));
                sender.addChatMessage(new ChatComponentText("§7/reincarnation_timer place <x> <y> - HUDの表示位置を変更します"));
                sender.addChatMessage(new ChatComponentText("§7/reincarnation_timer stop - タイマーを停止します(転生時再開)"));
                sender.addChatMessage(new ChatComponentText("§7/reincarnation_timer info - 自分のタイムの情報を取得します"));
                sender.addChatMessage(new ChatComponentText("§7/reincarnation_timer rank_send - 最速タイムを送信します"));
                sender.addChatMessage(new ChatComponentText("§7/reincarnation_timer rank_get <sword|bow|magic|all> <個数> - それぞれの最速タイムランキングを取得します.個数は省略可能です(allとは全転生のことです)"));
                sender.addChatMessage(new ChatComponentText("§7/reincarnation_timer help - このヘルプを表示します"));
                break;
           
            case "rank_send": {
                if (!(sender instanceof EntityPlayer)) {
                    sender.addChatMessage(new ChatComponentText("§cこのコマンドはプレイヤーのみ使用できます。"));
                    return;
                }

                EntityPlayer player = (EntityPlayer) sender;
                UUID uuid = player.getUniqueID();
                
                Operation.CalculateTimerStats();
                int sword = thelow_reincarnation_timer.TimerData.min_time_tick.get(0);
                int bow = thelow_reincarnation_timer.TimerData.min_time_tick.get(1);
                int magic = thelow_reincarnation_timer.TimerData.min_time_tick.get(2);
                int all = thelow_reincarnation_timer.TimerData.min_time_tick.get(3);
                String mcid = Minecraft.getMinecraft().thePlayer.getName();
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/thelow_api "+Secretdatas.APItype);
                // Google Sheets に送信
                new Thread(() -> {
                    RankSender.sendRank(uuid,mcid, sword, bow, magic, all);
                }).start();

                
                break;
            }
            case "rank_get": {
                if (!(args.length >= 2)) {
                    sender.addChatMessage(new ChatComponentText("§c使用方法: /reincarnation_timer rank_get <sword|bow|magic|all> <個数>"));
                    return;
                }

                String category = args[1].toLowerCase();
                if (!category.equals("sword") && !category.equals("bow") && !category.equals("magic") && !category.equals("all")) {
                    sender.addChatMessage(new ChatComponentText("§c 'sword', 'bow', 'magic', 'all' のいずれかでランキングを取得"));
                    return;
                }
                int count = 10;
                if (args.length >= 3) {
                	try{
                		count = Integer.parseInt(args[2]);
                	}catch (NumberFormatException e) {}
                }	
                

                sender.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7データを受信中..."));
                RankGetter.fetchAndDisplayRanks(category, sender,count);
                break;
            }
            case "info": {
            	Operation.CalculateTimerStats();
            	sender.addChatMessage(new ChatComponentText("§a[thelow_reincarnation_timer]§7転生タイム情報"));
            	sender.addChatMessage(new ChatComponentText("§6～～最速タイム～～"));
            	
            	String text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.min_time_tick.get(0));
            	sender.addChatMessage(new ChatComponentText(String.format("剣転生:  %s" , text)));
            	
            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.min_time_tick.get(1));
            	sender.addChatMessage(new ChatComponentText(String.format("弓転生:  %s" , text)));

            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.min_time_tick.get(2));
            	sender.addChatMessage(new ChatComponentText(String.format("魔法転生:%s" , text)));

            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.min_time_tick.get(3));
            	sender.addChatMessage(new ChatComponentText(String.format("全転生:  %s" , text)));
            	
            	sender.addChatMessage(new ChatComponentText("§6～～平均タイム～～"));
            	
            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.ave_time_tick.get(0));
            	sender.addChatMessage(new ChatComponentText(String.format("剣転生:  %s" , text)));
            	
            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.ave_time_tick.get(1));
            	sender.addChatMessage(new ChatComponentText(String.format("弓転生:  %s" , text)));
            	
            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.ave_time_tick.get(2));
            	sender.addChatMessage(new ChatComponentText(String.format("魔法転生:%s" , text)));
            	
            	text = Operation.TicktoTimer( thelow_reincarnation_timer.TimerData.ave_time_tick.get(3));
            	sender.addChatMessage(new ChatComponentText(String.format("全転生:  %s" , text)));
            	break;
            }
            default:
                sender.addChatMessage(new ChatComponentText("§c不明なコマンドです。/reincarnation_timer help でヘルプを表示します。"));
                break;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("place");
            options.add("stop");
            options.add("info");
            options.add("rank_send");
            options.add("rank_get");
            options.add("help");
            return getListOfStringsMatchingLastWord(args, options.toArray(new String[0]));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("rank_get")) {
            List<String> categories = new ArrayList<>();
            categories.add("sword");
            categories.add("bow");
            categories.add("magic");
            categories.add("all");
            return getListOfStringsMatchingLastWord(args, categories.toArray(new String[0]));
        }
        return null;
    }
}
