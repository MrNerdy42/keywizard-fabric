package mrnerdy42.keywizard.gui;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;

public class KeyWizardScreen extends Screen {
	
	private final int[] mouseCodes = {GLFW.GLFW_MOUSE_BUTTON_1, GLFW.GLFW_MOUSE_BUTTON_2, GLFW.GLFW_MOUSE_BUTTON_3, GLFW.GLFW_MOUSE_BUTTON_4, GLFW.GLFW_MOUSE_BUTTON_5, GLFW.GLFW_MOUSE_BUTTON_6, GLFW.GLFW_MOUSE_BUTTON_7, GLFW.GLFW_MOUSE_BUTTON_8};
	private int mouseCodeIndex = 0;
	
	private KeyboardWidget keyboard;
	private KeyboardWidget mouseButton;
	private ButtonWidget mousePlus;
	private ButtonWidget mouseMinus;
	private KeyBindingListWidget bindingList;
	private CategorySelectorWidget categorySelector;
	private TexturedButtonWidget screenToggleButton;
	private TextFieldWidget searchBar;
	private ButtonWidget resetBinding;
	private ButtonWidget resetAll;
	private ButtonWidget clearBinding;
	
	private final Screen parent;
	private final GameOptions options;

	@SuppressWarnings("resource")
	public KeyWizardScreen(Screen parent) {
		super(new TranslatableText(KeyWizard.MODID));
		this.parent = parent;
		this.options = MinecraftClient.getInstance().options;
	}
	
	@Override
	protected void init() {
		int mouseButtonX = this.width - 105;
		int mouseButtonY = this.height / 2 - 115;
		int mouseButtonWidth = 80;
		int mouseButtonHeight = 20;
		
		int maxBindingNameWidth = 0;
		for (KeyBinding k : this.minecraft.options.keysAll) {
			int w = this.minecraft.textRenderer.getStringWidth(I18n.translate(k.getId()));
			if (w > maxBindingNameWidth)
				maxBindingNameWidth = w;
		}
		
		int maxCategoryWidth = 0;
		for (String s : KeyBindingUtil.getCategories()) {
			int w = this.minecraft.textRenderer.getStringWidth(I18n.translate(s));
			if (w > maxCategoryWidth)
				maxCategoryWidth = w;
		}
		
		int bindingListWidth = (maxBindingNameWidth + 20);
		this.bindingList = new KeyBindingListWidget(this, 10, 10, bindingListWidth, this.height - 40, this.minecraft.textRenderer.fontHeight * 3 + 10);
		this.keyboard = KeyboardWidgetBuilder.standardKeyboard(this, bindingListWidth + 15, this.height / 2 - 90, this.width - (bindingListWidth + 15), 180);
		this.categorySelector = new CategorySelectorWidget(this, bindingListWidth + 15, 5, maxCategoryWidth + 20, 20);
		this.screenToggleButton = new TexturedButtonWidget(this.width - 22, this.height - 22, 20, 20, 20, 0, 20, KeyWizard.SCREEN_TOGGLE_WIDGETS, 40, 40, (btn) -> {
			this.minecraft.openScreen(new ControlsOptionsScreen(this.parent, this.options));
		});
		this.searchBar = new TextFieldWidget(this.minecraft.textRenderer, 10, this.height - 20, bindingListWidth, 14, "");
		this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], InputUtil.Type.MOUSE);
		this.mousePlus = new ButtonWidget( (int)this.mouseButton.getAnchorX() + 83, (int)this.mouseButton.getAnchorY(), 25, 20, "+", (btn) -> {
			this.mouseCodeIndex ++;
			if (this.mouseCodeIndex >= this.mouseCodes.length ) {
				this.mouseCodeIndex = 0;
			}
			this.children.remove(this.mouseButton);
			this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], InputUtil.Type.MOUSE);
			this.children.add(this.mouseButton);
		});
		this.mouseMinus = new ButtonWidget( (int)this.mouseButton.getAnchorX() - 26, (int)this.mouseButton.getAnchorY(), 25, 20, "-", (btn) -> {
			this.mouseCodeIndex --;
			if (this.mouseCodeIndex < 0) {
				this.mouseCodeIndex = this.mouseCodes.length - 1;
			}
			this.children.remove(this.mouseButton);
			this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], InputUtil.Type.MOUSE);
			this.children.add(this.mouseButton);
		});
		this.resetBinding = new ButtonWidget(bindingListWidth + 15, this.height - 23, 50, 20, I18n.translate("controls.reset"), (btn) -> {
			KeyBinding selectedBinding = this.getSelectedKeyBinding();
			selectedBinding.setKeyCode(selectedBinding.getDefaultKeyCode());
			KeyBinding.updateKeysByCode();
		});
		this.clearBinding = new ButtonWidget(bindingListWidth + 66, this.height - 23, 50, 20, I18n.translate("gui.clear"), (btn) -> {
			KeyBinding selectedBinding = this.getSelectedKeyBinding();
			selectedBinding.setKeyCode(InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_UNKNOWN));
			KeyBinding.updateKeysByCode();
		});
		this.resetAll = new ButtonWidget(bindingListWidth + 117, this.height - 23, 70, 20, I18n.translate("controls.resetAll"), (btn) -> {
			for(KeyBinding b : this.options.keysAll) {
				b.setKeyCode(b.getDefaultKeyCode());
			}
			KeyBinding.updateKeysByCode();
		});
		
		this.children.add(categorySelector);
		this.children.add(this.keyboard);
		this.children.add(this.bindingList);
		this.children.add(this.categorySelector);
		this.children.add(this.categorySelector.getCategoryList());
		this.children.add(this.screenToggleButton);
		this.children.add(this.searchBar);
		this.children.add(this.mouseButton);
		this.children.add(this.mousePlus);
		this.children.add(this.mouseMinus);
		this.children.add(this.resetBinding);
		this.children.add(this.clearBinding);
		this.children.add(this.resetAll);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		for (Element e : this.children) {
			if (e instanceof Drawable) {
				((Drawable) e).render(mouseX, mouseY, delta);
			}
		}
	}
	
	@Override
	public void tick() {
		for (Element e : this.children) {
			if (e instanceof Tickable) {
				((Tickable) e).tick();
			}
		}
	}
	
	@Nullable
	public KeyBinding getSelectedKeyBinding() {
		return this.bindingList.getSelectedKeyBinding();
	}
	
	public boolean getCategorySelectorExtended() {
		return this.categorySelector.extended;
	}
	
	public String getSelectedCategory() {
		return this.categorySelector.getSelctedCategory();
	}
	
	public String getFilterText() {
		return this.searchBar.getText();
	}
	
	public void setSearchText(String s) {
		this.searchBar.setText(s);
	}
	
}
