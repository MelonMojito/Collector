package melonmodding.collector.mixins;

import melonmodding.collector.Collector;
import net.minecraft.client.player.controller.PlayerControllerMP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerControllerMP.class, remap = false)
public class PlayerControllerMPMixin {
	@Inject(
		method = "attack",
		at = @At("HEAD"),
		cancellable = true
	)
	void attack(Player entityplayer, Entity entity, CallbackInfo ci){
		if(Collector.toolDisabling.value && entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemTool){
			if(entityplayer.getCurrentEquippedItem().getMetadata() >= entityplayer.getCurrentEquippedItem().getMaxDamage() - 1){
				ci.cancel();
				return;
			}
		}
	}

	@Inject(
		method = "useItem",
		at = @At("HEAD"),
		cancellable = true
	)
	void useItem(Player entityplayer, World world, ItemStack itemstack, CallbackInfoReturnable<Boolean> cir){
		if(Collector.toolDisabling.value && entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemTool){
			if(entityplayer.getCurrentEquippedItem().getMetadata() >= entityplayer.getCurrentEquippedItem().getMaxDamage() - 1){
				cir.cancel();
				return;
			}
		}
	}

	@Inject(
		method = "useItemOn",
		at = @At("HEAD"),
		cancellable = true
	)
	void useItemOn(Player entityplayer, World world, ItemStack itemstack, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
		if(Collector.toolDisabling.value && entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemTool){
			if(entityplayer.getCurrentEquippedItem().getMetadata() >= entityplayer.getCurrentEquippedItem().getMaxDamage() - 1){
				cir.cancel();
				return;
			}
		}
	}
}
