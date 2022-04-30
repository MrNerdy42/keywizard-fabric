package mrnerdy42.keywizard.mixin;

import mrnerdy42.keywizard.KeyWizard;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenInjector {
	@Inject(at = @At("TAIL"), method = "init()V")
	private void init(CallbackInfo info) {
		KeyWizard.LOGGER.info("Controls injector mixin loaded!");
		ControlsOptionsScreen target = (ControlsOptionsScreen)((Object)this);
		
	}
}
