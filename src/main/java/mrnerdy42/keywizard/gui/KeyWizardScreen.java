package mrnerdy42.keywizard.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class KeyWizardScreen extends GameOptionsScreen{

	public KeyWizardScreen(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.drawHorizontalLine(matrices, this.width/2 - 20, this.width / 2 + 20, this.width/2, 0xFFFFFFFF);
		super.render(matrices, mouseX, mouseY, delta);
	}

}
