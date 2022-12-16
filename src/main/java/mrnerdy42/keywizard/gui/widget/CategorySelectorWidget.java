package mrnerdy42.keywizard.gui.widget;

import mrnerdy42.keywizard.gui.screen.KeyWizardScreen;
import mrnerdy42.keywizard.keybinding.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import mrnerdy42.keywizard.gui.TextUtil;
import mrnerdy42.keywizard.gui.TickableElement;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class CategorySelectorWidget extends PressableWidget implements TickableElement {
	
	public KeyWizardScreen keyWizardScreen;
	public boolean extended = false;
	
	private BindingCategoryListWidget categoryList;
	
	public CategorySelectorWidget(KeyWizardScreen keyWizardScreen, int x, int y, int width, int height) {
		super(x, y, width, height, Text.of(""));
		this.keyWizardScreen = keyWizardScreen;
		MinecraftClient c = MinecraftClient.getInstance();
		int listItemHeight = c.textRenderer.fontHeight + 7;
		int listHeight = KeyBindingUtil.getCategoriesWithDynamics().size() * listItemHeight + 10;
		int listBottom = this.y + this.height + listHeight;
		if (listBottom > this.keyWizardScreen.height) {
			listHeight = this.keyWizardScreen.height - this.y - this.height - 10;
		}
		this.categoryList = new BindingCategoryListWidget(c, this.y + this.height, this.x, this.width, listHeight, listItemHeight);

	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean listClicked = this.categoryList.mouseClicked(mouseX, mouseY, button);
		boolean thisClicked = super.mouseClicked(mouseX, mouseY, button);
		if (! (listClicked || thisClicked)) {
			this.extended = false;
		}
		return listClicked || thisClicked;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY) || this.categoryList.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);
		this.categoryList.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button) || this.categoryList.mouseReleased(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		return super.mouseScrolled(mouseX, mouseY, amount) || this.categoryList.mouseScrolled(mouseX, mouseY, amount);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return super.isMouseOver(mouseX, mouseY) || this.categoryList.isMouseOver(mouseX, mouseY);
	}
	
	@Override
	public void onPress() {
		this.playDownSound(MinecraftClient.getInstance().getSoundManager());
		this.extended = !this.extended;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		this.categoryList.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void tick() {
		this.setMessage(TextUtil.guiTextOf(getSelctedCategory()));
    	this.categoryList.visible = this.extended;
	}
	
	public String getSelctedCategory() {
		if (this.categoryList.getSelected() == null) {
			return KeyBindingUtil.DYNAMIC_CATEGORY_ALL;
		}
    	return ((BindingCategoryListWidget.CategoryEntry) this.categoryList.getSelected()).category;
    }
    
	private class BindingCategoryListWidget extends FreeFormListWidget<BindingCategoryListWidget.CategoryEntry> {

		public BindingCategoryListWidget(MinecraftClient client, int top, int left, int width, int height, int itemHeight) {
			super(client, top, left, width, height, itemHeight);
			
			for (String c : KeyBindingUtil.getCategoriesWithDynamics()) {
				this.addEntry(new CategoryEntry(c));
			}
			this.setSelected(this.children().get(0));
		}
		
		public class CategoryEntry extends FreeFormListWidget<BindingCategoryListWidget.CategoryEntry>.Entry{

			private final String category;

			public CategoryEntry(String category) {
			    this.category = category;
			}

			@Override
			public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
				client.textRenderer.drawWithShadow(matrices, TextUtil.guiTextOf(category), x + 3 , y + 2, 0xFFFFFFFF);
			}

		}
	}
	
}
