package melonmodding.meloncollector;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class MelonCollector implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
    public static final String MOD_ID = "meloncollector";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static OptionsPage MelonCollectorOptions;
	public static OptionBoolean toolDisabling;

    @Override
    public void onInitialize() {
        LOGGER.info("MelonCollector initialized.");
    }

	public static void initOptions(GameSettings settings){
		toolDisabling = new OptionBoolean(settings, "meloncollector.category.tools.toolDisabling", false);
	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		MelonCollectorOptions =
			new OptionsPage("options.meloncollector.title", new ItemStack(Items.BASKET))
				.withComponent(new OptionsCategory("options.meloncollector.category.tools")
					.withComponent(new BooleanOptionComponent(toolDisabling)));

		OptionsPages.register(MelonCollectorOptions);
	}
}
