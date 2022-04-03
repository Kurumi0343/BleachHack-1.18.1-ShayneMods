package org.bleachhack.module.mods;

import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import org.bleachhack.event.events.EventTick;
import org.bleachhack.eventbus.BleachSubscribe;
import org.bleachhack.module.Module;
import org.bleachhack.module.ModuleCategory;


public class Spider extends Module {
    public Spider() {
        super("Spider", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Player climbs like a Spider.");
    }
    @BleachSubscribe
    public void onTick(EventTick event) {
        if(!mc.player.horizontalCollision)
            return;
        if(mc.player.getVelocity().y >= 0.2)
            return;
        mc.player.setVelocity(mc.player.getVelocity().x, 0.2, mc.player.getVelocity().z);
}
}
