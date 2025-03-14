package melonmodding.meloncollector.mixins;

import melonmodding.meloncollector.MelonCollector;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {
	@Shadow
	public abstract ItemStack getCurrentEquippedItem();

	@Inject(
		method = "attackTargetEntityWithCurrentItem",
		at = @At("HEAD"),
		cancellable = true
	)
	void attackTargetEntityWithCurrentItem(Entity entity, CallbackInfo ci){
		if(MelonCollector.toolDisabling.value && getCurrentEquippedItem() != null && getCurrentEquippedItem().getItem() instanceof ItemTool){
			if(this.getCurrentEquippedItem().getMetadata() >= this.getCurrentEquippedItem().getMaxDamage() - 1){
				ci.cancel();
				return;
			}
		}
	}
}
