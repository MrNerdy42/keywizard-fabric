package mrnerdy42.keywizard.util;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingUtil {
	public static final String DYNAMIC_CATEGORY_ALL = "key.categories.keywizard.all";
	public static final String DYNAMIC_CATEGORY_CONFLICTS = "key.categories.keywizard.conflicts";
	public static final String DYNAMIC_CATEGORY_UNBOUND = "key.categories.keywizard.unbound";
	
	/**
	 * Get a list of all binding categories
	 */
	@SuppressWarnings("resource")
	public static ArrayList<String> getCategories() {
		ArrayList<String> categories = new ArrayList<>();

		for (KeyBinding k : MinecraftClient.getInstance().options.keysAll) {
			String c = k.getCategory();
			if (!categories.contains(c))
				categories.add(c);
		}

		return categories;
	}
	
	public static ArrayList<String> getCategoriesWithDynamics() {
		ArrayList<String> categories = new ArrayList<>();
		categories.add(DYNAMIC_CATEGORY_ALL);
		categories.add(DYNAMIC_CATEGORY_CONFLICTS);
		categories.add(DYNAMIC_CATEGORY_UNBOUND);
		categories.addAll(getCategories());
		return categories;
	}

}
