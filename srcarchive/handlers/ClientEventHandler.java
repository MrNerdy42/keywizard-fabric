package mrnerdy42.keywizard.handlers;

import org.lwjgl.glfw.GLFW;

import mrnerdy42.keywizard.KeyWizard;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class ClientEventHandler {
	
	public static KeyBinding keyOpenKeyWizard = new KeyBinding(I18n.get((KeyWizard.MODID + ".keybind.openKeyboardWizard")), GLFW.GLFW_KEY_F7, "key.categories.misc");
	

	//private Minecraft client  = FMLClientHandler.instance().getClient();
	
	public static void register() {
		ClientRegistry.registerKeyBinding(keyOpenKeyWizard);
	}
	
	/*
	@SubscribeEvent
    public void controlsGuiInit(InitGuiEvent e) {
    	if (KeyWizardConfig.openFromControlsGui && e.getGui() instanceof GuiControls ) {
    		int width = e.getGui().width;
    		int buttonY = 0;
    		for(GuiButton b: e.getButtonList()) {
    			if(b.id == 200) { // Done
    				b.width = 100;
    				b.x = width / 2 + 60;
    				
    				buttonY = b.y;
    			}
    			if(b.id == 201) { // Reset All
    				b.width = 100;
    				b.x = width / 2 - 160;
    			}
    		}
    		e.getButtonList().add(new GuiButton(203, width / 2 - 50, buttonY, 100, 20, I18n.format("gui.openKeyWiz")));
    	}
    }

	@SubscribeEvent
	public void controlsGuiActionPreformed(ActionPerformedEvent e) {
		if (KeyWizardConfig.openFromControlsGui && e.getGui() instanceof GuiControls && e.getButton().id == 203) {
			client.displayGuiScreen(new GuiKeyWizard(client, e.getGui()));
		}
	}
    */
	
    @SubscribeEvent
    public void keyPressed(KeyInputEvent e) {
;    	System.out.println("KEY");
    	System.out.println(e.getKey());
    	System.out.println(e.getScanCode());
    	System.out.println(InputMappings.getKey(e.getKey(), e.getScanCode()));
    	System.out.println(InputMappings.getKey(e.getKey(), 15));
    	System.out.println(InputMappings.getKey(e.getKey(), e.getScanCode()).getNumericKeyValue());
    }

}
