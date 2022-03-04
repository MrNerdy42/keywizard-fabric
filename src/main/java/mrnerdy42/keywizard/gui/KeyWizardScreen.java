package mrnerdy42.keywizard.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class KeyWizardScreen extends GameOptionsScreen{
	
	private KeyboardWidget keyboard;
	private KeyBindingListWidget bindingList;

	public KeyWizardScreen(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}
	
	@Override
	protected void init() {
		int maxBindingNameWidth = 0;
		for (KeyBinding k : this.client.options.keysAll) {
			int w = this.textRenderer.getWidth(new TranslatableText(k.getTranslationKey()));
			if (w > maxBindingNameWidth)
				maxBindingNameWidth = w;
		}
		
		int bindingListWidth = (maxBindingNameWidth + 20);
		this.keyboard = KeyboardWidgetBuilder.StandardKeyboard(this, bindingListWidth + 15, this.height / 2 - 90, this.width - (bindingListWidth + 15), 200);
		this.addChild(keyboard);
		this.bindingList = new KeyBindingListWidget(this.client, 10, 10, bindingListWidth, this.height - 40, this.textRenderer.fontHeight * 3 + 10);
		this.addChild(this.bindingList);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.keyboard.render(matrices, mouseX, mouseY, delta);
		this.bindingList.render(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void tick() {
		this.keyboard.tick();
	}
	
	public TextRenderer getTextRenderer() {
		return this.textRenderer;
	}
	
	public KeyBinding getSelectedKeyBinding() {
		return this.bindingList.getSelectedKeyBinding();
	}
	
}
