package mrnerdy42.keywizard.util;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingUtil {
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
	
	public enum DynamicCategories {
	    ALL("key.categories.all"),
	    CONFLICTS("key.categories.conflicts"),
	    UNBOUND("key.categories.conflicts");
		
		private final String category;

		DynamicCategories(final String category) {
			this.category = category;
		}
		
		@Override
		public String toString() {
			return this.category;
		}
	}

}
