package ghostclient.util;

import net.minecraft.item.ItemStack;

/**
 * TeaVM/Eaglercraft 1.12 ItemStack helpers.
 * In this codebase 1.12's getCount()/isEmpty() are not deobfuscated,
 * so we use the mapped names func_190916_E() and func_190926_b().
 */
public class ItemUtil {

    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.func_190926_b();
    }

    public static int getCount(ItemStack stack) {
        return stack == null ? 0 : stack.func_190916_E();
    }
}
