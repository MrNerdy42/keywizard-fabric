package mrnerdy42.keywizard.gui;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
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
	private CategorySelectorWidget categorySelector;

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
		int maxCategoryWidth = 0;
		for (String s : KeyBindingUtil.getCategories()) {
			int w = this.textRenderer.getWidth(new TranslatableText(s));
			if (w > maxCategoryWidth)
				maxCategoryWidth = w;
		}
		
		int bindingListWidth = (maxBindingNameWidth + 20);
		this.bindingList = new KeyBindingListWidget(this.client, 10, 10, bindingListWidth, this.height - 40, this.textRenderer.fontHeight * 3 + 10);
		this.keyboard = KeyboardWidgetBuilder.StandardKeyboard(this, bindingListWidth + 15, this.height / 2 - 90, this.width - (bindingListWidth + 15), 200);
		this.categorySelector = new CategorySelectorWidget(this, bindingListWidth + 15, 5, maxCategoryWidth + 10, 20);
		
		this.addChild(this.bindingList);
		this.addChild(this.keyboard);
		this.addChild(categorySelector);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.keyboard.render(matrices, mouseX, mouseY, delta);
		this.bindingList.render(matrices, mouseX, mouseY, delta);
		this.categorySelector.render(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void tick() {
		this.keyboard.tick();
		this.categorySelector.tick();
	}
	
	public TextRenderer getTextRenderer() {
		return this.textRenderer;
	}
	
	public KeyBinding getSelectedKeyBinding() {
		return this.bindingList.getSelectedKeyBinding();
	}
	
	public boolean getCategorySelectorExtended() {
		return this.categorySelector.extended;
	}
	
	public static KeyWizardScreen getNewScreen() {
		MinecraftClient client = MinecraftClient.getInstance();
		return new KeyWizardScreen(client.currentScreen, client.options, Text.of(KeyWizard.MODID));
	}
	
}
