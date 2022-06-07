package mrnerdy42.keywizard.util;

import java.util.ArrayList;
import java.util.stream.Collectors;

import mrnerdy42.keywizard.mixin.KeyBindingAccessor;

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
	
}
