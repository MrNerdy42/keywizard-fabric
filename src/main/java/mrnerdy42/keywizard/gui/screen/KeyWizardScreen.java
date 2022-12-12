package mrnerdy42.keywizard.gui.screen;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import mrnerdy42.keywizard.KeyWizard;
import mrnerdy42.keywizard.gui.widget.CategorySelectorWidget;
import mrnerdy42.keywizard.gui.widget.KeyBindingListWidget;
import mrnerdy42.keywizard.gui.widget.KeyboardWidget;
import mrnerdy42.keywizard.gui.widget.KeyboardWidgetBuilder;
import mrnerdy42.keywizard.keybinding.KeyBindingUtil;
import mrnerdy42.keywizard.keybinding.KeyBindingWrapper;
import mrnerdy42.keywizard.keybinding.KeyWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import mrnerdy42.keywizard.gui.TickableElement;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class KeyWizardScreen extends NerdyScreen {
	
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

	@SuppressWarnings("resource")
	public KeyWizardScreen(Screen parent) {
		super(Text.of(KeyWizard.MODID), parent);
	}
	
	@SuppressWarnings("resource")
	@Override
	protected void init() {
		int mouseButtonX = this.width - 105;
		int mouseButtonY = this.height / 2 - 115;
		int mouseButtonWidth = 80;
		int mouseButtonHeight = 20;
		
		int maxBindingNameWidth = 0;
		for (KeyBindingWrapper k : KeyBindingUtil.getKeyBindings()) {
			int w = this.textRenderer.getWidth(new TranslatableText(k.getUnlocalizedName()));
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
		this.keyboard = KeyboardWidgetBuilder.standardKeyboard(this, bindingListWidth + 15, this.height / 2 - 90, this.width - (bindingListWidth + 15), 180);
		this.categorySelector = new CategorySelectorWidget(this, bindingListWidth + 15, 5, maxCategoryWidth + 20, 20);
		this.screenToggleButton = new TexturedButtonWidget(this.width - 22, this.height - 22, 20, 20, 20, 0, 20, KeyWizard.SCREEN_TOGGLE_WIDGETS, 40, 40, (btn) -> {
			this.client.openScreen(new ControlsOptionsScreen(this.parent, MinecraftClient.getInstance().options));
		});
		this.searchBar = new TextFieldWidget(this.textRenderer, 10, this.height - 20, bindingListWidth, 14, Text.of(""));
		this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], true);
		this.mousePlus = new ButtonWidget( (int)this.mouseButton.getAnchorX() + 83, (int)this.mouseButton.getAnchorY(), 25, 20, Text.of("+"), (btn) -> {
			this.mouseCodeIndex ++;
			if (this.mouseCodeIndex >= this.mouseCodes.length ) {
				this.mouseCodeIndex = 0;
			}
			this.children.remove(this.mouseButton);
			this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], true);
			this.children.add(this.mouseButton);
		});
		this.mouseMinus = new ButtonWidget( (int)this.mouseButton.getAnchorX() - 26, (int)this.mouseButton.getAnchorY(), 25, 20, Text.of("-"), (btn) -> {
			this.mouseCodeIndex --;
			if (this.mouseCodeIndex < 0) {
				this.mouseCodeIndex = this.mouseCodes.length - 1;
			}
			this.children.remove(this.mouseButton);
			this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], true);
			this.children.add(this.mouseButton);
		});
		this.resetBinding = new ButtonWidget(bindingListWidth + 15, this.height - 23, 50, 20, new TranslatableText("controls.reset"), (btn) -> {
			KeyBindingWrapper selectedBinding = this.getSelectedKeyBinding();
			selectedBinding.setBoundKey(selectedBinding.getDefaultKey());
		});
		this.clearBinding = new ButtonWidget(bindingListWidth + 66, this.height - 23, 50, 20, new TranslatableText("gui.clear"), (btn) -> {
			KeyBindingWrapper selectedBinding = this.getSelectedKeyBinding();
			selectedBinding.setBoundKey(KeyWrapper.createKeyboardKeyFromCode(GLFW.GLFW_KEY_UNKNOWN));
		});
		this.resetAll = new ButtonWidget(bindingListWidth + 117, this.height - 23, 70, 20, new TranslatableText("controls.resetAll"), (btn) -> {
			for(KeyBindingWrapper b : KeyBindingUtil.getKeyBindings()) {
				b.setBoundKey(b.getDefaultKey());
			}
		});
		
		this.addChild(this.bindingList);
		this.addChild(this.keyboard);
		this.addChild(this.categorySelector);
		this.addChild(this.categorySelector.getCategoryList());
		this.addChild(this.screenToggleButton);
		this.addChild(this.searchBar);
		this.addChild(this.mouseButton);
		this.addChild(this.mousePlus);
		this.addChild(this.mouseMinus);
		this.addChild(this.resetBinding);
		this.addChild(this.clearBinding);
		this.addChild(this.resetAll);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		for (Element e : this.children) {
			if (e instanceof Drawable) {
				((Drawable) e).render(matrices, mouseX, mouseY, delta);
			}
		}
	}
	
	@Override
	public void tick() {
		for (Element e : this.children) {
			if (e instanceof TickableElement) {
				((TickableElement) e).tick();
			}
		}
	}
	
	@Nullable
	public KeyBindingWrapper getSelectedKeyBinding() {
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
