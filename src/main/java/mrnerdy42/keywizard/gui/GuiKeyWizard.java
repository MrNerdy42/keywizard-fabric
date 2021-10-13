package mrnerdy42.keywizard.gui;

import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.input.Mouse.getButtonName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.input.Mouse;

import mrnerdy42.keywizard.KeyWizardConfig;
import mrnerdy42.keywizard.util.KeybindUtils;
import mrnerdy42.keywizard.util.KeyboardFactory;
import mrnerdy42.keywizard.util.KeyboardLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.settings.KeyModifier;

public class GuiKeyWizard extends GuiScreen {
	
	private enum SortType implements Comparator<KeyBinding> {
		NAME { @Override public int compare(KeyBinding arg0, KeyBinding arg1){ return I18n.format(arg0.getKeyDescription()).compareTo(I18n.format(arg1.getKeyDescription())); }},
		CATEGORY { @Override public int compare(KeyBinding arg0, KeyBinding arg1){ return I18n.format(arg0.getKeyCategory()).compareTo(I18n.format(arg1.getKeyCategory())); }},
		KEY { @Override public int compare(KeyBinding arg0, KeyBinding arg1){ return I18n.format(arg0.getDisplayName()).compareTo(I18n.format(arg1.getDisplayName())); }};
		
	}
	
	public KeyboardLayout layout = KeyWizardConfig.layout;
	
	protected GuiKeyboard keyboard;
	protected SortType sortType = SortType.NAME;
	
	private final GuiScreen parentScreen;
	
    private KeyboardLayout[] pages = {KeyWizardConfig.layout, KeyboardLayout.NUMPAD, KeyboardLayout.AUXILIARY};
    private int pageNum = 0;
	private int mouse = 0;
	private int maxMouse = KeyWizardConfig.maxMouseButtons - 1;
	private KeyBinding selectedKeybind;
	private KeyModifier activeModifier = KeyModifier.NONE;
	private String selectedCategory = "categories.all";
	private String searchText = "";
	private int guiWidth;
	private int guiStartX;


	private GuiCategorySelector categoryList;
	private GuiTextField searchBar;
	private GuiBindingList bindingList;
	private GuiButton buttonPage;
	private GuiButton buttonReset;
	private GuiButton buttonClear;
	private GuiButton buttonDone;
	private GuiButton buttonActiveModifier;
	private GuiButton buttonMouse;
	private GuiButton buttonMousePlus;
	private GuiButton buttonMouseMinus;
	private GuiButton buttonSortBy;

	

	public GuiKeyWizard(Minecraft mcIn, GuiScreen parentScreen) {
		this.mc = mcIn;
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		
		int maxBindingLength = 0;
	
		for (KeyBinding binding : KeybindUtils.ALL_BINDINGS) {
			if (binding.getDisplayName().length() > maxBindingLength)
				maxBindingLength = binding.getDisplayName().length();
		}
		
		int bindingListWidth = (maxBindingLength * 11);
	
		this.bindingList = new GuiBindingList(this, 10, this.height - 30, bindingListWidth, this.height - 40,
				fontRenderer.FONT_HEIGHT * 3 + 10);
		
		this.searchBar = new GuiTextField(0, this.fontRenderer, 10, this.height - 20, bindingListWidth, 14);
		this.searchBar.setFocused(true);
		this.searchBar.setCanLoseFocus(false);
	
		this.guiStartX = bindingListWidth + 15;
		this.guiWidth = this.width - this.guiStartX;
		
		ArrayList<String> categories = KeybindUtils.getCategories();
		categories.add(0, "categories.conflicts");
		categories.add(0, "categories.unbound");
		categories.add(0, "categories.all");
		
		int maxCategoryLength = 0;
		for(String s:categories) {
			if (I18n.format(s).length() > maxCategoryLength)
				maxCategoryLength = s.length();
		}
		
		this.categoryList = new GuiCategorySelector(this, this.guiStartX, 5, maxCategoryLength*9, categories);
		this.selectedCategory = this.categoryList.getSelctedCategory();
		
		this.keyboard = KeyboardFactory.makeKeyboard(this.pages[this.pageNum], this, this.guiStartX, this.height / 2 - 90, this.guiWidth - 5, this.height);
		
		this.buttonPage = new GuiButton(0, this.width - 110, 5, 100, 20, I18n.format("gui.page") + ": " + this.pages[this.pageNum].getDisplayName());
		this.buttonReset = new GuiButton(0, this.guiStartX, this.height - 40, 75, 20, I18n.format("gui.resetBinding"));
		this.buttonClear = new GuiButton(0, this.guiStartX + 76, this.height - 40, 75, 20, I18n.format("gui.clearBinding"));
		this.buttonDone = new GuiButton(0, this.width - 90, this.height - 40, 87, 20, I18n.format("gui.done"));
		this.buttonActiveModifier = new GuiButton(1, this.guiStartX, this.height - 63, 150, 20,
				I18n.format("gui.activeModifier")+ ": " + activeModifier.toString());
		this.buttonMouse = new GuiButton(0, this.guiStartX + 25, this.height - 85, 100, 20, I18n.format("gui.mouse") + ": " + getButtonName(this.mouse) );
		this.buttonMousePlus = new GuiButton(0, this.guiStartX + 126, this.height - 85, 25, 20, "+");
	    this.buttonMouseMinus = new GuiButton(0, this.guiStartX, this.height - 85, 25, 20, "-");
	    //this.buttonSortBy = new GuiButton(0, this.searchBar.x + 10, this.height - 20, 20, 14, "Cat");
		
		this.setSelectedKeybind(this.bindingList.getSelectedKeybind());
		
	    this.buttonList.add(this.buttonPage);
		this.buttonList.add(this.buttonReset);
		this.buttonList.add(this.buttonClear);
		this.buttonList.add(this.buttonDone);
		this.buttonList.add(this.buttonActiveModifier);
		this.buttonList.add(this.buttonMouse);
		this.buttonList.add(this.buttonMousePlus);
		this.buttonList.add(this.buttonMouseMinus);
		//this.buttonList.add(this.buttonSortBy);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.bindingList.drawScreen(mouseX, mouseY, partialTicks);
		this.searchBar.drawTextBox();
		
		this.keyboard.draw(this.mc, mouseX, mouseY, partialTicks);
		this.categoryList.drawButton(this.mc, mouseX, mouseY, partialTicks);
	}

	@Override
	public void updateScreen() {
	    super.updateScreen();
	    this.searchBar.updateCursorCounter();
	    if ( this.buttonReset != null )
	    	this.buttonReset.enabled = !this.selectedKeybind.isSetToDefaultValue();
		if ( this.buttonClear != null ) {
			this.buttonClear.enabled = !(this.selectedKeybind.getKeyCode() == 0);
		}
	
	    if ( this.categoryList != null )
	    	this.selectedCategory = this.categoryList.getSelctedCategory();
	    
	    if ( !this.searchBar.getText().equals(this.searchText) ) {
	    	this.searchText = this.searchBar.getText();
	    }
	    
	    if (this.activeModifier != null) {
	    	switch (this.activeModifier.toString()) {
	    	case "CONTROL":
	    		this.keyboard.disableKey(KEY_LCONTROL);
	    		this.keyboard.disableKey(KEY_RCONTROL);
	    		
	    		this.keyboard.enableKey(KEY_LMENU);
	    		this.keyboard.enableKey(KEY_RMENU);
	    		this.keyboard.enableKey(KEY_LSHIFT);
	    		this.keyboard.enableKey(KEY_RSHIFT);
	    		break;
	    	case "ALT":
	    		this.keyboard.disableKey(KEY_LMENU);
	    		this.keyboard.disableKey(KEY_RMENU);
	    		
	    		this.keyboard.enableKey(KEY_LCONTROL);
	    		this.keyboard.enableKey(KEY_RCONTROL);
	    		this.keyboard.enableKey(KEY_LSHIFT);
	    		this.keyboard.enableKey(KEY_RSHIFT);
	    		break;
	    	case "SHIFT":
	    		this.keyboard.disableKey(KEY_LSHIFT);
	    		this.keyboard.disableKey(KEY_RSHIFT);
	    		
	    		this.keyboard.enableKey(KEY_LCONTROL);
	    		this.keyboard.enableKey(KEY_RCONTROL);
	    		this.keyboard.enableKey(KEY_LMENU);
	    		this.keyboard.enableKey(KEY_RMENU);
	    		break;
	    	case "NONE" :
	    		this.keyboard.enableKey(KEY_LCONTROL);
	    		this.keyboard.enableKey(KEY_RCONTROL);
	    		this.keyboard.enableKey(KEY_LMENU);
	    		this.keyboard.enableKey(KEY_RMENU);
	    		this.keyboard.enableKey(KEY_LSHIFT);
	    		this.keyboard.enableKey(KEY_RSHIFT);
	    	}
	    }
	    
	    switch (KeybindUtils.getNumBindings(-100 + this.mouse, this.activeModifier)) {
	    case 0:
	    	this.buttonMouse.displayString = I18n.format("gui.mouse") + ": " + getButtonName(this.mouse);
	    	break;
	    case 1:
	    	this.buttonMouse.displayString = I18n.format("gui.mouse") + ": " + TextFormatting.GREEN + getButtonName(this.mouse);
	    	break;
	    default:
	    	this.buttonMouse.displayString = I18n.format("gui.mouse") + ": " + TextFormatting.RED + getButtonName(this.mouse);
	    	break;
	    }
	    
	    this.buttonPage.displayString = I18n.format("gui.page") + ": " + this.pages[this.pageNum].getDisplayName();
	    this.bindingList.updateList();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		
		if (!this.categoryList.getExtended()) {
			if (button == this.buttonReset) {
				this.selectedKeybind.setToDefault();
				KeyBinding.resetKeyBindingArrayAndHash();
				this.buttonReset.enabled = !selectedKeybind.isSetToDefaultValue();
				return;
			}

			if (button == this.buttonClear) {
				this.selectedKeybind.setKeyModifierAndCode(KeyModifier.NONE, 0);
				KeyBinding.resetKeyBindingArrayAndHash();
				this.buttonClear.enabled = this.selectedKeybind.getKeyCode() != 0;
			}

			if (button == this.buttonDone) {
				if (this.parentScreen != null)
					this.mc.displayGuiScreen(this.parentScreen);
				else 
					this.mc.displayGuiScreen((GuiScreen)null);
			}

			if (button == this.buttonActiveModifier) {
				this.changeActiveModifier();
			}

			if (button == this.buttonPage) {
				this.pageNum++;
				if (this.pageNum > this.pages.length-1) {
					this.pageNum = 0;
				}
				this.keyboard = KeyboardFactory.makeKeyboard(this.pages[this.pageNum], this, this.guiStartX, this.height / 2 - 90, this.guiWidth - 5, this.height);
			}
			
			if (button == this.buttonMouse) {
				this.selectedKeybind.setKeyModifierAndCode(this.activeModifier, -100 + this.mouse);
				mc.gameSettings.setOptionKeyBinding(this.selectedKeybind, -100 + this.mouse);
				KeyBinding.resetKeyBindingArrayAndHash();
			}
			
			if (button == this.buttonMousePlus) {
				if(this.mouse >= this.maxMouse) {
					this.mouse = 0;
				} else {
					this.mouse++;
				}
			}
			
			if (button == this.buttonMouseMinus) {
				if(this.mouse <= 0) {
					this.mouse = this.maxMouse;
				} else {
					this.mouse--;
				}
			}

		this.buttonReset.enabled = !selectedKeybind.isSetToDefaultValue();
		}
	}

	
    @Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		
		int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		
		this.bindingList.handleMouseInput(mouseX, mouseY);
		this.categoryList.handleMouseInput(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
	    super.mouseClicked(x, y, button);
	    this.searchBar.mouseClicked(x, y, button);    
	    /*
	    if (button == 1 && x >= this.searchBar.x && x < this.searchBar.x + this.searchBar.width && y >= this.searchBar.y && y < this.searchBar.y + this.searchBar.height) {
	        this.searchBar.setText("");
	        System.out.println("hi");
	    */    
	    this.categoryList.mouseClicked(this.mc, x, y, button);
	    this.keyboard.mouseClicked(mc, x, y, button);
	}

	@Override
	protected void keyTyped(char c, int keyCode) throws IOException {
	    super.keyTyped(c, keyCode);
	    this.searchBar.textboxKeyTyped(c, keyCode);
	}

	/** Change the active modifier */
	private void changeActiveModifier() {

		if (this.activeModifier == KeyModifier.NONE) {
			this.activeModifier = KeyModifier.ALT;
		} else if (this.activeModifier == KeyModifier.ALT) {
			this.activeModifier = KeyModifier.CONTROL;
		} else if (this.activeModifier == KeyModifier.CONTROL) {
			this.activeModifier = KeyModifier.SHIFT;
		} else {
			this.activeModifier = KeyModifier.NONE;
		}

		this.buttonActiveModifier.displayString = I18n.format("gui.activeModifier" )+ ": " + activeModifier.toString();
	}
	
    public Minecraft getClient() {
		return this.mc;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
	
	public String getSearchText() {
		return this.searchText;
	}
	
	public void setSearchText(String s) {
		this.searchText = s;
		this.searchBar.setText(s);
	}
	
	public String getSelectedCategory() {
		return this.selectedCategory;
	}
	
	public KeyModifier getActiveModifier() {
		return this.activeModifier;
	}
	
	public KeyBinding getSelectedKeybind() {
		return this.selectedKeybind;
	}

	protected void setSelectedKeybind(KeyBinding binding){
    	this.selectedKeybind = binding;
    }
	
	public boolean getCategoryListExtended() {
		return this.categoryList.getExtended();
	}
}
