package mrnerdy42.keywizard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class KeyWizard implements ClientModInitializer {
	
	public static final String MODID = "keywizard";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final Identifier SCREEN_TOGGLE_WIDGETS = new Identifier(MODID, "textures/gui/screen_toggle_widgets.png");
	
	private static KeyBinding keyOpenKeyWizard;

	@Override
	public void onInitializeClient() {
		LOGGER.log(Level.DEBUG, MODID);
		
		keyOpenKeyWizard = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + MODID + ".openKeyWizard", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "key.categories." + MODID + ".bindings"));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
		        while (keyOpenKeyWizard.wasPressed()) {
		    	    client.openScreen(new KeyWizardScreen(client.currentScreen));
		        }
			});
	}

}
