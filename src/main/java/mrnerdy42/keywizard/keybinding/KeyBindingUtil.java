package mrnerdy42.keywizard.keybinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import mrnerdy42.keywizard.mixin.KeyBindingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;

public class KeyBindingUtil {
	public static final String DYNAMIC_CATEGORY_ALL = "key.categories.keywizard.all";
	public static final String DYNAMIC_CATEGORY_CONFLICTS = "key.categories.keywizard.conflicts";
	public static final String DYNAMIC_CATEGORY_UNBOUND = "key.categories.keywizard.unbound";
	
	/**
	 * Get a list of all binding categories
	 */
	public static ArrayList<String> getCategories() {
		return KeyBindingAccessor.getKeyCategories().stream().sorted().collect(Collectors.toCollection(ArrayList<String>::new));
	}
	
	public static ArrayList<String> getCategoriesWithDynamics() {
		ArrayList<String> categories = getCategories();
		categories.add(0, DYNAMIC_CATEGORY_UNBOUND);
		categories.add(0, DYNAMIC_CATEGORY_CONFLICTS);
		categories.add(0, DYNAMIC_CATEGORY_ALL);
		return categories;
	}
	
	@SuppressWarnings("resource")
	public static Map<Key, Integer> getBindingCountsByKey() {
		HashMap<InputUtil.Key, Integer> map = new HashMap<>();
		for (KeyBinding b : MinecraftClient.getInstance().options.keysAll) {
			map.merge(((KeyBindingAccessor)b).getBoundKey(), 1, Integer::sum);
		}
		return Collections.unmodifiableMap(map);
	}
}
