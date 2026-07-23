package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

/**
 * Automatically swaps to the best sword when attacking.
 */
public class AutoSword extends Module {

    public AutoSword() {
        super(Category.Combat, "AutoSword", "Switch to the best sword on attack.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        Entity target = findClosestEntity();
        if (target != null && mc.gameSettings.keyBindAttack.isKeyDown()) {
            int bestSlot = -1;
            float bestDamage = -1;
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSword) {
                    ItemSword sword = (ItemSword) mc.player.inventory.getStackInSlot(i).getItem();
                    float damage = sword.getDamageVsEntity();
                    if (damage > bestDamage) {
                        bestDamage = damage;
                        bestSlot = i;
                    }
                }
            }
            if (bestSlot != -1 && bestSlot != mc.player.inventory.currentItem) {
                mc.player.inventory.currentItem = bestSlot;
            }
        }
    }

    private Entity findClosestEntity() {
        double bestRange = 5.0;
        Entity target = null;
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !entity.isEntityAlive() || !(entity instanceof EntityLivingBase)) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist < bestRange) {
                bestRange = dist;
                target = entity;
            }
        }
        return target;
    }
}
