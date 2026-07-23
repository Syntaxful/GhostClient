package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Spawns a fake player model visible to the client only.
 * This is intentionally a client-side visual; it cannot be made server-side from the client mod.
 */
public class FakePlayer extends Module {

    private net.minecraft.client.entity.EntityOtherPlayerMP fakePlayer = null;

    public FakePlayer() {
        super(Category.Misc, "FakePlayer", "Client-side fake player visual.");
    }

    @Override
    public void onEnable() {
        if (mc.world == null || mc.player == null) return;
        fakePlayer = new net.minecraft.client.entity.EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        fakePlayer.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ);
        fakePlayer.rotationYaw = mc.player.rotationYaw;
        fakePlayer.rotationPitch = mc.player.rotationPitch;
        mc.world.addEntityToWorld(-1000, fakePlayer);
    }

    @Override
    public void onDisable() {
        if (fakePlayer != null && mc.world != null) {
            mc.world.removeEntity(fakePlayer);
            fakePlayer = null;
        }
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (fakePlayer != null && mc.player != null) {
            fakePlayer.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ);
            fakePlayer.rotationYaw = mc.player.rotationYaw;
            fakePlayer.rotationPitch = mc.player.rotationPitch;
        }
    }
}
