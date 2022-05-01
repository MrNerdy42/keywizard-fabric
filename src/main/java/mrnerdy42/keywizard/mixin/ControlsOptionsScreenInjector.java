package mrnerdy42.keywizard.mixin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.TranslatableText;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenInjector {
	@Inject(at = @At("TAIL"), method = "init()V")
	private void init(CallbackInfo info) {
		KeyWizard.LOGGER.info("Controls screen injector mixin loaded!");
		ControlsOptionsScreen target = (ControlsOptionsScreen)((Object)this);
		try {
			Method addButton = Screen.class.getDeclaredMethod("addButton", ClickableWidget.class);
			ButtonWidget keyWizardButton = new ButtonWidget(target.width - 22, target.height - 22, 20, 20, new TranslatableText("keywizard.button.openKeyWizard"), (button) -> {
				MinecraftClient.getInstance().openScreen(KeyWizardScreen.getNewScreen());
			});
			addButton.invoke(target, keyWizardButton);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			KeyWizard.LOGGER.warn("Button injection failed. Printing stack trace...");
			e.printStackTrace();
		}
	}
}
