package melonmodding.meloncollector.mixins;

import melonmodding.meloncollector.MelonCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin {

	@Shadow
	private int metadata;

	@Shadow
	public abstract int getMaxDamage();

	@Shadow
	public abstract String getDisplayName();

	@Shadow
	@NotNull
	public abstract Item getItem();

	@Inject(
		method = "damageItem",
		at = @At("HEAD")
	)
	void damageItem(int i, Entity entity, CallbackInfo ci){
		if(MelonCollector.toolDisabling.value) {
			if (!(entity instanceof Player) || ((Player) entity).getGamemode().toolDurability()) {
				if (this.metadata == this.getMaxDamage() - 1 && this.getItem() != Items.PAINTBRUSH && !(this.getItem() instanceof ItemArmor)) {
					Minecraft.getMinecraft().thePlayer.sendMessage(TextFormatting.GRAY + "[" + TextFormatting.LIGHT_GRAY + this.getDisplayName() + TextFormatting.GRAY + "]" + TextFormatting.LIGHT_GRAY + "'s " + TextFormatting.RED + "Durability is Low...");
					Minecraft.getMinecraft().thePlayer.sendMessage(TextFormatting.RED + "- [Tool Disabled] -");
					Minecraft.getMinecraft().sndManager.playSound("note.bd", SoundCategory.GUI_SOUNDS, 2f, 2f);
				}
			}
		}
	}

	@Inject(
		method = "hitEntity",
		at = @At("HEAD"),
		cancellable = true
	)
	void hitEntity(Mob target, Player attacker, CallbackInfo ci){
		if(MelonCollector.toolDisabling.value){
			if(this.metadata == this.getMaxDamage() - 2){
				Minecraft.getMinecraft().thePlayer.sendMessage(TextFormatting.GRAY + "[" + TextFormatting.LIGHT_GRAY + this.getDisplayName() + TextFormatting.GRAY + "]" + TextFormatting.LIGHT_GRAY + "'s " + TextFormatting.RED + "Durability is Low...");
				Minecraft.getMinecraft().thePlayer.sendMessage(TextFormatting.RED + "- [Tool Disabled] -");
				Minecraft.getMinecraft().sndManager.playSound("note.bd", SoundCategory.GUI_SOUNDS, 2f, 2f);
			}
			if(this.metadata >= this.getMaxDamage() - 1){
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
	void useItem(Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
		if(MelonCollector.toolDisabling.value){
			if(this.getItem() instanceof ItemTool && this.metadata >= this.getMaxDamage()){
				cir.cancel();
				return;
			}
		}
	}

}
