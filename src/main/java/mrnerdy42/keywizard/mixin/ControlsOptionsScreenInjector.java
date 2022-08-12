package mrnerdy42.keywizard.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenInjector extends Screen {
	
	@Shadow
	private Screen parent;
	
	// Useless constructor to make the compiler happy. Don't ever call it.
	private ControlsOptionsScreenInjector() {
		super(null);
	}

	@Inject(at = @At("TAIL"), method = "init()V")
	private void init(CallbackInfo info) {
		KeyWizard.LOGGER.info("Controls screen injector mixin loaded!");
		TexturedButtonWidget screenToggleButton = new TexturedButtonWidget(this.width - 22, this.height - 22, 20, 20, 0, 0, 20, KeyWizard.SCREEN_TOGGLE_WIDGETS, 40, 40, (btn) -> {
		    minecraft.openScreen(new KeyWizardScreen(this.parent));
		});
		this.addButton(screenToggleButton);
	}
	
}
