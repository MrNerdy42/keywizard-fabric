package mrnerdy42.keywizard.mixin;

import mrnerdy42.keywizard.KeyWizard;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public class ControlsInjector {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		KeyWizard.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
