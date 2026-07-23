package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.util.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Highlights nearby entities with boxes through walls.
 */
public class ESP extends Module {

    private final BooleanValue players = new BooleanValue("Players", "Show players", true);
    private final BooleanValue mobs    = new BooleanValue("Mobs",    "Show hostile mobs", true);
    private final BooleanValue animals = new BooleanValue("Animals", "Show animals", true);
    private final BooleanValue items   = new BooleanValue("Items",   "Show dropped items", true);

    public ESP() {
        super(Category.Render, "ESP", "See entities through walls.");
        addSetting(players);
        addSetting(mobs);
        addSetting(animals);
        addSetting(items);
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.getRenderManager() == null || mc.player == null) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !entity.isEntityAlive()) continue;

            int color = 0;
            if (entity instanceof EntityPlayer) {
                if (!players.getValue()) continue;
                color = entity.getName().equals(mc.player.getName()) ? 0xFFFFFFFF : 0xFFFF5555;
            } else if (entity instanceof EntityMob) {
                if (!mobs.getValue()) continue;
                color = 0xFFFF55FF;
            } else if (entity instanceof EntityAnimal) {
                if (!animals.getValue()) continue;
                color = 0xFF55FF55;
            } else if (entity instanceof EntityItem) {
                if (!items.getValue()) continue;
                color = 0xFFFFFF55;
            } else {
                continue;
            }

            AxisAlignedBB box = entity.getEntityBoundingBox();
            RenderUtils.drawBox(box, color, true);
        }
    }
}
