package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.PacketEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;
import net.minecraft.network.play.server.SPacketChat;

/**
 * Replaces your real name in incoming chat with a chosen alias.
 * This is client-side only because the server controls chat text.
 */
public class NameProtect extends Module {

    private final ModeValue name = new ModeValue("Name", "Display name", "You", "You", "Player", "Me");

    public NameProtect() {
        super(Category.Misc, "NameProtect", "Hide your real name in chat.");
        addSetting(name);
    }

    @EventHandler
    public void onReceiveChat(PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof SPacketChat)) return;
        if (mc.player == null) return;
        SPacketChat packet = (SPacketChat) event.getPacket();
        String real = mc.player.getName();
        String alias = name.getValue();
        String text = packet.getChatComponent().getUnformattedText();
        if (text.contains(real)) {
            packet.getChatComponent().getSiblings().clear();
            packet.getChatComponent().appendText(text.replace(real, alias));
        }
    }
}
