package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Chat tweaks: timestamps, duplicate-spam prevention, and formatting control.
 */
public class BetterChat extends Module {

    private final BooleanValue timestamps = new BooleanValue("Timestamps", "Add timestamps to chat", true);
    private final BooleanValue antiSpam = new BooleanValue("AntiSpam", "Block duplicate messages", true);
    private final BooleanValue clearFormatting = new BooleanValue("ClearFormatting", "Strip color codes from incoming chat", false);
    private final BooleanValue unlimitedChat = new BooleanValue("UnlimitedChat", "Allow longer chat messages", true);
    private String lastMessage = "";
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public BetterChat() {
        super(Category.Misc, "BetterChat", "Chat timestamps and anti-spam.");
        addSetting(timestamps);
        addSetting(antiSpam);
        addSetting(clearFormatting);
        addSetting(unlimitedChat);
    }

    @EventHandler
    public void onReceiveChat(PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof SPacketChat)) return;
        SPacketChat packet = (SPacketChat) event.getPacket();
        String text = packet.getChatComponent().getUnformattedText();

        if (clearFormatting.getValue()) {
            text = text.replaceAll("§[0-9a-fk-or]", "");
        }

        if (antiSpam.getValue() && text.equals(lastMessage)) {
            event.setCancelled(true);
            return;
        }
        lastMessage = text;

        if (timestamps.getValue()) {
            String time = "§7[" + timeFormat.format(new Date()) + "] §r";
            packet.getChatComponent().getSiblings().clear();
            packet.getChatComponent().appendText(time + text);
        }
    }

    @EventHandler
    public void onSendChat(PacketEvent.Send event) {
        if (!(event.getPacket() instanceof CPacketChatMessage)) return;
        if (!unlimitedChat.getValue()) return;
        CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();
        String msg = packet.getMessage();
        if (msg.length() > 100) {
            setPacketMessage(packet, msg.substring(0, 100));
        }
    }

    private void setPacketMessage(CPacketChatMessage packet, String message) {
        try {
            java.lang.reflect.Field f = CPacketChatMessage.class.getDeclaredField("message");
            f.setAccessible(true);
            f.set(packet, message);
        } catch (Exception ignored) {}
    }
}
