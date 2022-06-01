package mrnerdy42.keywizard.gui;

import org.jetbrains.annotations.Nullable;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class KeyWizardScreen extends GameOptionsScreen{
	
	private String searchText = "";
	
	private KeyboardWidget keyboard;
	private KeyBindingListWidget bindingList;
	private CategorySelectorWidget categorySelector;
	private TexturedButtonWidget screenToggleButton;

	@SuppressWarnings("resource")
	public KeyWizardScreen(Screen parent) {
		super(parent, MinecraftClient.getInstance().options, Text.of(KeyWizard.MODID));
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
		this.bindingList = new KeyBindingListWidget(this, 10, 10, bindingListWidth, this.height - 40, this.textRenderer.fontHeight * 3 + 10);
		this.keyboard = KeyboardWidgetBuilder.StandardKeyboard(this, bindingListWidth + 15, this.height / 2 - 90, this.width - (bindingListWidth + 15), 200);
		this.categorySelector = new CategorySelectorWidget(this, bindingListWidth + 15, 5, maxCategoryWidth + 20, 20);
		this.screenToggleButton = new TexturedButtonWidget(this.width - 22, this.height - 22, 20, 20, 20, 0, 20, KeyWizard.SCREEN_TOGGLE_WIDGETS, 40, 40, (btn) -> {
			this.client.openScreen(new ControlsOptionsScreen(this.parent, this.gameOptions));
		});
		this.addChild(this.bindingList);
		this.addChild(this.keyboard);
		this.addChild(categorySelector);
		this.addButton(screenToggleButton);
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
		this.bindingList.tick();
	}
	
	public TextRenderer getTextRenderer() {
		return this.textRenderer;
	}
	
	@Nullable
	public KeyBinding getSelectedKeyBinding() {
		return this.bindingList.getSelectedKeyBinding();
	}
	
	public boolean getCategorySelectorExtended() {
		return this.categorySelector.extended;
	}
	
	public String getSelectedCategory() {
		String category = this.categorySelector.getSelctedCategory();
		return category == null ? KeyBindingUtil.DYNAMIC_CATEGORY_ALL : category;
	}
	
	public String getSearchText() {
		return this.searchText;
	}
	
	public void setSearchText(String s) {
		this.searchText = s;
		//this.searchBar.setText(s);
	}
	
}
