package mrnerdy42.keywizard.gui;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.GuiScrollingList;
/**
 *An NEI-style dropdown list menu. Its functionality is 
 *hacked together using the vanilla GuiButton class
 */
public class GuiCategorySelector extends GuiButton{
	
	
	private GuiKeyWizard parent;
	
	private boolean extended = false;
	private GuiCategoryList list;
	private ArrayList<String> categories;
	
	private int selectedCategoryIdx;
	private String selectedCategory;
	
	private class GuiCategoryList extends GuiScrollingList {
		public GuiCategoryList(GuiKeyWizard parent, int left, int top, int width, int height, int entryHeight) {
			super(parent.getClient(), width, height, top, top + height, left, entryHeight, parent.width, parent.height);
		}

		@Override
		protected int getSize() {
			return categories.size();
		}

		@Override
		protected void elementClicked(int index, boolean doubleClick) {
			selectedCategoryIdx = index;
			update();
		}

		@Override
		protected boolean isSelected(int index) {
			return selectedCategoryIdx == index;
		}

		@Override
		protected void drawBackground() {
		}

		@Override
		protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
			FontRenderer fontRender = parent.getFontRenderer();
			fontRender.drawStringWithShadow(I18n.format(categories.get(slotIdx)), this.left + 3 , slotTop + 2, 0xFFFFFF);	
		}
		
		public int getListWidth() {
			return this.listWidth;
		}
		
		public int getListHeight() {
			return this.listHeight;
		}
		
		public int getTop() {
			return this.top;
		}
		
		public int getLeft() {
			return this.left;
		}

	}

	public GuiCategorySelector(GuiKeyWizard parent, int x, int y, int width, ArrayList<String> categories) {
		super(0, x, y, width, 20, I18n.format(categories.get(0)));
		this.parent = parent;
		this.categories = categories;
		this.selectedCategoryIdx = 0;
		this.selectedCategory = this.categories.get(0);
		int fontHeight = parent.getFontRenderer().FONT_HEIGHT;
		this.list = new GuiCategoryList(parent, x ,y + 20, width, parent.height - 40, fontHeight + 7);
	}
	
	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
		//this.zLevel = 1;
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getShadingMultiplier(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 0xE0E0E0;

            if (packedFGColour != 0){
                j = packedFGColour;
            }else if (!this.enabled){
                j = 0xA0A0A0;
            }
            else if (this.hovered || this.extended){
                j = 0xFFFFA0;
            }
            

            this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
            
    		if (this.extended) {
    		    this.list.drawScreen(mouseX, mouseY, partialTicks);
    		}
        }

    }
	
	public void handleMouseInput(int mouseX, int mouseY) throws IOException {
		this.list.handleMouseInput(mouseX, mouseY);
		
	}
	
	public boolean getExtended(){
    	return this.extended;
    }
	
	public String getSelctedCategory(){
    	return this.selectedCategory;
    }
    
    private int getShadingMultiplier(boolean mouseOver){
    	if (this.extended) {
    		return 2;
    	} else {
    	    return this.getHoverState(mouseOver);
    	}
    }
    
    public void mouseClicked(Minecraft mc, int mouseX, int mouseY, int button) {
    	if (mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height && button == 0) {
            this.playPressSound(mc.getSoundHandler());
    		this.setState(!this.extended);
    	} else if (!(mouseX >= list.getLeft() && mouseX < list.getLeft() + list.getListWidth() && mouseY >= list.getTop() && mouseY < list.getTop() + list.getListHeight()) && button == 0) {
    		this.setState(false);
    	}
    }
    
    public void setState(boolean extended){
		this.extended = extended;
		this.update();
	}
    
    private void update() {
		this.hovered = this.extended;
		this.selectedCategory = this.categories.get(this.selectedCategoryIdx);
		this.displayString = I18n.format(this.selectedCategory);
		
    }

}
