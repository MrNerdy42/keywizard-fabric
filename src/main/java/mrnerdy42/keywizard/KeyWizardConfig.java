package mrnerdy42.keywizard;

//import mrnerdy42.keywizard.util.KeyboardLayout;
import net.minecraftforge.common.ForgeConfigSpec;

public class KeyWizardConfig {
	public static final ForgeConfigSpec SPEC;
	
	//public static final ForgeConfigSpec.ConfigValue<KeyboardLayout> keyboardLayout;
	public static final ForgeConfigSpec.ConfigValue<Integer> mouseButtons;
	public static final ForgeConfigSpec.ConfigValue<Boolean> controlsGuiButton;
	
	static {
		ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
		b.push("Config for Keyboard Wizard");
		b.comment("If true, keyboard wizard will be accessible through a button in the controls gui.",
		     "as well as through the a keybinding. (default:F7)",
	         "Note: this option may do weird things if another mod",
	         "that overrides the controls gui is installed.");
		//keyboardLayout = b.define("Keyboard Layout", KeyboardLayout.QWERTY);
		b.comment("The number of mouse buttons to show in the GUI.");
		mouseButtons = b.defineInRange("test", 5, 1, 15);
		b.comment("Add 'Key Wizard' button to the controls gui?");
		controlsGuiButton = b.define("Controls GUI Button", true);
		
		SPEC = b.build();
	}


}
