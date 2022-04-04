
package org.bleachhack.module.mods;

import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.bleachhack.event.events.EventTick;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;
import org.bleachhack.setting.module.SettingSlider;
import org.bleachhack.util.world.EntityUtils;

import java.util.Optional;
import java.util.stream.Stream;

public class ChargeBow extends Module {

    public ChargeBow() {
        super("ChargeBow", KEY_UNBOUND, ModuleCategory.COMBAT, "Automatically Charge and Fire Bow",
                new SettingSlider("Charge", 0.1, 1, 0.5, 2).withDesc("How much to charge the bow before shooting."));
    }

    @Override
    public void onDisable(boolean inWorld) {
        mc.options.keyUse.setPressed(false);


        super.onDisable(inWorld);
    }

    @BleachSubscribe
    public void onTick(EventTick event) {

        Optional<Entity> entity = DebugRenderer.getTargetedEntity(mc.player, 7);
        LivingEntity target = Stream.of(entity.get())                .filter(e -> EntityUtils.isAttackable(e, true)
                && (mc.player.canSee(e)))
                .filter(e -> (EntityUtils.isPlayer(e))
                        || (EntityUtils.isMob(e))
                        || (EntityUtils.isAnimal(e)))
                .map(e -> (LivingEntity) e)
                .findFirst().orElse(null);
        mc.options.keyUse.setPressed(true);
        if (!(mc.player.getMainHandStack().getItem() instanceof RangedWeaponItem) || !mc.player.isUsingItem())
            return;
        if (mc.player.getMainHandStack().getItem() == Items.BOW
                && BowItem.getPullProgress(mc.player.getItemUseTime()) >= getSetting(0).asSlider().getValueFloat() && target != null) {
            mc.player.stopUsingItem();
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.UP));
        }
    }
}
