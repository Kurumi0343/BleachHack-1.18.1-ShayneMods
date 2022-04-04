package org.bleachhack.module.mods;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.bleachhack.event.events.EventTick;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;
import org.bleachhack.setting.module.SettingItemList;

public class AutoDrop extends Module {

    public AutoDrop() {
        super("AutoDrop", KEY_UNBOUND, ModuleCategory.PLAYER, "This will automatically Drop unwanted items for you",
                new SettingItemList("Edit Items", "Edit Unwanted Items",
                        Items.DIRT).withDesc("Edit the Unwanted items."));
    }


    @BleachSubscribe
    public void onTick(EventTick event) {
        if(mc.currentScreen instanceof HandledScreen)
            return;
        for(int slot = 9; slot < 45; slot++)
        {
            int adjustedSlot = slot;

            if(adjustedSlot >= 36)
                adjustedSlot -= 36;
            ItemStack stack = mc.player.getInventory().getStack(adjustedSlot);

            if(stack.isEmpty())
                continue;
            Item item = stack.getItem();



            if(!getSetting(0).asList(Item.class).contains(item))
                continue;
            PlayerScreenHandler currentScreenHandler = (PlayerScreenHandler) mc.player.currentScreenHandler;
            mc.interactionManager.clickSlot(currentScreenHandler.syncId, slot, 1, SlotActionType.THROW, mc.player);
        }
    }

}
