package org.bleachhack.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Screen.class)
public interface AccessorScreen {

    @Accessor
    List<Drawable> getDrawables();

    @Accessor
    void setDrawables(List<Drawable> drawables);

    @Invoker
    void callRenderTextHoverEffect(MatrixStack matrices, Style style, int x, int y);
}