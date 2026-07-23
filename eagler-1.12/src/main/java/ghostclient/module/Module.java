package ghostclient.module;

import java.util.ArrayList;
import java.util.List;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.PacketEvent;
import ghostclient.setting.Value;
import net.minecraft.client.Minecraft;

/**
 * Base class for every GhostClient module.
 */
public abstract class Module {

    protected final Minecraft mc = Minecraft.getMinecraft();

    private final Category category;
    private final String name;
    private final String description;
    private final List<Value<?>> settings = new ArrayList<>();
    private boolean enabled;
    private boolean hidden = false;
    private int keybind = 0;

    public Module(Category category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Value<?>> getSettings() {
        return settings;
    }

    protected void addSetting(Value<?> setting) {
        settings.add(setting);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            onEnable();
            GhostClient.EVENT_BUS.register(this);
        } else {
            GhostClient.EVENT_BUS.unregister(this);
            onDisable();
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    protected void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onTick(ghostclient.event.TickEvent.Post event) {
    }

    public void onRender(RenderEvent.Post event) {
    }

    public void onKey(KeyInputEvent event) {
    }

    public void onPacket(PacketEvent.Receive event) {
    }

    public void onPacket(PacketEvent.Send event) {
    }
}
