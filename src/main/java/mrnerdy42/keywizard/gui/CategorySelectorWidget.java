package mrnerdy42.keywizard.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import com.google.common.util.concurrent.Runnables;

import mrnerdy42.keywizard.gui.KeyBindingListWidget.BindingEntry;
import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class CategorySelectorWidget extends PressableWidget{
	
	public KeyWizardScreen keyWizardScreen;
	
	private boolean extended = false;
	private BindingCategoryListWidget list;
	private ArrayList<String> categories;
	
	private int selectedCategoryIdx;
	private String selectedCategory;

	public CategorySelectorWidget(int x, int y, int width, int height) {
		super(x, y, width, height, new TranslatableText("CATEGORY TEST"));
		MinecraftClient c = MinecraftClient.getInstance();
		this.list = new BindingCategoryListWidget(c, y, x, width, keyWizardScreen.height - 20, c.textRenderer.fontHeight + 7);
		this.list.visible = false;
	}
	
	@Override
	public void onPress() {
		this.playDownSound(MinecraftClient.getInstance().getSoundManager());
	}


	public boolean getExtended(){
    	return this.extended;
    }
	
	public String getSelctedCategory(){
    	return null;
    	//TODO: fix
    }
    
    public void setExtended(boolean extended){
		this.extended = extended;
		this.update();
	}
    
    private void update() {
		this.hovered = this.extended;
		this.selectedCategory = this.categories.get(this.selectedCategoryIdx);
		this.setMessage(new TranslatableText(this.selectedCategory));
		
    }
    
	private class BindingCategoryListWidget extends FreeFormListWidget<BindingCategoryListWidget.CategoryEntry> {

		public BindingCategoryListWidget(MinecraftClient client, int top, int left, int width, int height, int itemHeight) {
			super(client, top, left, width, height, itemHeight);
			
			for (String c : KeyBindingUtil.getCategories()) {
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
				client.textRenderer.drawWithShadow(matrices, new TranslatableText(this.category), x + 3 , y + 2, 0xFFFFFFFF);
			}

		}
	}	
	
}
