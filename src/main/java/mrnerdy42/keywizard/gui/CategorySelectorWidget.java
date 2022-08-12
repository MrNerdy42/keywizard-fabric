package mrnerdy42.keywizard.gui;

import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Tickable;

public class CategorySelectorWidget extends AbstractPressableButtonWidget implements Tickable {
	
	public KeyWizardScreen keyWizardScreen;
	public boolean extended = false;
	
	public BindingCategoryListWidget categoryList;
	
	public CategorySelectorWidget(KeyWizardScreen keyWizardScreen, int x, int y, int width, int height) {
		super(x, y, width, height, "");
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
	public void onPress() {
		this.playDownSound(MinecraftClient.getInstance().getSoundManager());
		this.extended = !this.extended;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		super.render(mouseX, mouseY, delta);
		this.categoryList.render(mouseX, mouseY, delta);
	}
	
	@Override
	public void tick() {
		this.setMessage(I18n.translate(getSelctedCategory()));
    	this.categoryList.visible = this.extended;
	}
	
	public String getSelctedCategory() {
		if (this.categoryList.getSelected() == null) {
			return KeyBindingUtil.DYNAMIC_CATEGORY_ALL;
		}
    	return ((BindingCategoryListWidget.CategoryEntry) this.categoryList.getSelected()).category;
    }
	
	public Element getCategoryList() {
		return (Element)this.categoryList;
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
			public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
				minecraft.textRenderer.drawWithShadow(I18n.translate(category), x + 3 , y + 2, 0xFFFFFFFF);
			}

		}
	}
	
}
