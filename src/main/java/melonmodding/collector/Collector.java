package melonmodding.collector;

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


public class Collector implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
    public static final String MOD_ID = "collector";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static OptionsPage CollectorOptions;
	public static OptionBoolean toolDisabling;

    @Override
    public void onInitialize() {
        LOGGER.info("Collector initialized.");
    }

	public static void initOptions(GameSettings settings){
		toolDisabling = new OptionBoolean(settings, "collector.category.tools.toolDisabling", false);
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
		CollectorOptions =
			new OptionsPage("options.collector.title", new ItemStack(Items.BASKET))
				.withComponent(new OptionsCategory("options.collector.category.tools")
					.withComponent(new BooleanOptionComponent(toolDisabling)));

		OptionsPages.register(CollectorOptions);
	}
}
