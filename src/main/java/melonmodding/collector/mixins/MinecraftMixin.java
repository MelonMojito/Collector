package melonmodding.collector.mixins;

import melonmodding.collector.Collector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.input.InputType;
import net.minecraft.client.input.controller.ControllerInput;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Shadow
	protected abstract void mineBlocks(int i, boolean flag);

	@Shadow
	@Nullable
	public Screen currentScreen;

	@Shadow
	public boolean inGameHasFocus;

	@Shadow
	public GameSettings gameSettings;
	@Shadow
	public ControllerInput controllerInput;
	@Shadow
	public PlayerLocal thePlayer;
	@Shadow
	public InputType inputType;
	@Unique
	boolean breakBlockPressed;

	@Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;mineBlocks(IZ)V"))
	void mineBlocks(Minecraft instance, int xPlaced, boolean blockX){

		breakBlockPressed = this.gameSettings.keyAttack.isPressed() || this.controllerInput != null && this.controllerInput.buttonRightTrigger.isPressed();

		if(Collector.toolDisabling.value && this.thePlayer.getHeldItem() != null && this.thePlayer.getHeldItem().getItem() instanceof ItemTool) {
			ItemStack heldItem = this.thePlayer.getHeldItem();

			if (heldItem != null && heldItem.getMetadata() == heldItem.getMaxDamage()) {
				mineBlocks(0, false);
			} else {
				mineBlocks(0, this.currentScreen == null && breakBlockPressed && this.inGameHasFocus);
			}

		} else {
			mineBlocks(0, this.currentScreen == null && breakBlockPressed && this.inGameHasFocus);
		}

	}

}
