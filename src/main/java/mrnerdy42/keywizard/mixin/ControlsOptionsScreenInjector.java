package mrnerdy42.keywizard.mixin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenInjector extends GameOptionsScreen {
	
	private ControlsOptionsScreenInjector(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Shadow
	protected abstract <T extends ClickableWidget> T addButton(T button);
	@Shadow
	@Final
	protected Screen parent;
	@Shadow
	protected MinecraftClient client;
	
	@Shadow
	public int width;

	@Inject(at = @At("TAIL"), method = "init()V")
	private void init(CallbackInfo info) {
		KeyWizard.LOGGER.info("Controls screen injector mixin loaded!");
		ControlsOptionsScreen target = (ControlsOptionsScreen)((Object)this);
		TexturedButtonWidget screenToggleButton = new TexturedButtonWidget(this.width - 22, target.height - 22, 20, 20, 0, 0, 20, KeyWizard.SCREEN_TOGGLE_WIDGETS, 40, 40, (btn) -> {
		    client.openScreen(new KeyWizardScreen(parent));
		});
		this.addButton(screenToggleButton);
	}
	
}
