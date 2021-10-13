package mrnerdy42.keywizard.util;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.loading.FMLClientLaunchProvider;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;

public class KeybindUtils {
	
	public static final KeyBinding[] ALL_BINDINGS = Minecraft.getInstance().options.keyMappings;
	
	/** 
	 * Get the names of all bindings for a certain key and modifier
	 * 
	 * @param keyId the LWJGL code of the key to get the binding names for
	 * @param modifier the modifier of the key to get the binding names for
	 */
	public static ArrayList<String> getBindingNames(int keyId, KeyModifier modifier){
		ArrayList<String> bindingNames = new ArrayList<>();
		
		if (keyId == 0)
			return bindingNames;
		
		for (KeyBinding currentBinding : ALL_BINDINGS) {
			if (currentBinding.getKey() == keyId && currentBinding.getKeyModifier() == modifier)
			    bindingNames.add(I18n.format(currentBinding.getKeyDescription()));
		}
		return bindingNames;
	}
	
	public static ArrayList<String> getBindingNamesAndCategories(int keyId, KeyModifier modifier){
		ArrayList<String> bindingNames = new ArrayList<>();
		
		if (keyId == 0)
			return bindingNames;
		
		for (KeyBinding currentBinding : ALL_BINDINGS) {
			if (currentBinding.getKeyCode() == keyId && currentBinding.getKeyModifier() == modifier)
			    bindingNames.add(I18n.format(currentBinding.getKeyDescription()) + " (" + I18n.format(currentBinding.getKeyCategory()) + ")");
		}
		return bindingNames;
	}
	
	public static int getNumBindings(int keyId, KeyModifier modifier){
		int num = 0;
		
		if (keyId == 0)
			return num;
		
		for (KeyBinding currentBinding : ALL_BINDINGS) {
			if (currentBinding.getKeyCode() == keyId && currentBinding.getKeyModifier() == modifier)
			    num++;
		}
		return num;
	}
	
	/** 
	 * Get the number of conflicts for a key binding 
	 * 
	 * @param binding the binding to check
	 */
	public static int getNumConficts(KeyBinding binding) {
		int num = 0;
		for (KeyBinding currentBinding : ALL_BINDINGS) {
			if( !(currentBinding == binding) ) {
				if (currentBinding.getKeyCode() == binding.getKeyCode() && currentBinding.getKeyModifier() == binding.getKeyModifier())
				    num++;
			}
		}
		return num;
	}
	
	/** 
	 * Get a list of all binding categories
	 */
	
	public static ArrayList<String> getCategories() {
		ArrayList<String> categories = new ArrayList<>();
		
		for (KeyBinding currentBinding : ALL_BINDINGS) {
			if ( !categories.contains(currentBinding.getKeyCategory()) )
					categories.add(currentBinding.getKeyCategory());
		}
		
		return categories;
	}
}
