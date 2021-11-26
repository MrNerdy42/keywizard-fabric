package mrnerdy42.keywizard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyWizard implements ClientModInitializer {
	
	public static final String MODID = "keywizard";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	private static KeyBinding keyOpenKeyWizard;

	@Override
	public void onInitializeClient() {
		LOGGER.log(Level.DEBUG, MODID);
		
		keyOpenKeyWizard = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + MODID + ".openKeyWizard", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "category." + MODID + ".bindings"));
		ClientTickEvents.register(client -> {
			client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
		});
	}
	
	
	
}
