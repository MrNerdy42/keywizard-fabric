package mrnerdy42.keywizard.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class KeyWizardScreen extends GameOptionsScreen{
	
	private KeyboardWidget keyboard;
	private KeyBindingListWidget bindingList;

	public KeyWizardScreen(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}
	
	@Override
	protected void init() {
		this.keyboard = KeyboardWidgetBuilder.testKeyboard(this.width/2, this.height/2);
		this.addChild(keyboard);
		this.bindingList = new KeyBindingListWidget(client, 50, this.width/2, 200, 300, 50); 
		this.addChild(this.bindingList);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		System.out.println("MouseX: %f MouseY: %f".formatted(mouseX, Math.abs(mouseY-this.height)));
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		//this.keyboard.render(matrices, mouseX, mouseY, delta);
		this.bindingList.render(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);
		
		//Gl coords start at bottom left and one unit is half of a minecraft unit
	}

}
