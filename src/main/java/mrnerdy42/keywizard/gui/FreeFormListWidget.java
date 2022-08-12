package mrnerdy42.keywizard.gui;

import java.util.Objects;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import mrnerdy42.keywizard.util.DrawingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.MathHelper;

public abstract class FreeFormListWidget<E extends FreeFormListWidget<E>.Entry> extends EntryListWidget<FreeFormListWidget<E>.Entry> {
	public boolean visible = true;

	public FreeFormListWidget(MinecraftClient client, int top, int left, int width, int height, int itemHeight) {
		super(client, 0, 0, 0, 0, itemHeight);
		this.top = top;
		this.left = left;
		this.height = height;
		this.width = width;

		this.bottom = top + height;
		this.right = left + width;

		//this.method_31322(false);
		//this.method_31323(false);
	}

	@Override
	protected int getScrollbarPosition() {
		return this.left + this.width - 5;
	}

	@Override
	public int getRowWidth() {
		return this.width;
	}
	
	@Override
    public void render(int mouseX, int mouseY, float delta) {		
		if (this.visible) {

        int i = this.getScrollbarPosition();
        int j = i + 6;
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        this.minecraft.getTextureManager().bindTexture(DrawableHelper.BACKGROUND_LOCATION);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        //float f = 32.0f;

        int k = this.getRowLeft();
        int l = this.top + 4 - (int)this.getScrollAmount();
        if (this.renderHeader) {
            this.renderHeader(k, l, tessellator);
        }
        this.renderList(k, l, mouseX, mouseY, delta);
        GlStateManager.disableDepthTest();
        this.renderHoleBackground(0, this.top, 255, 255);
        this.renderHoleBackground(this.bottom, this.height, 255, 255);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlphaTest();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture();
        int n = Math.max(0, this.getMaxPosition() - (this.bottom - this.top - 4));
        if (n > 0) {
            int o = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getMaxPosition());
            o = MathHelper.clamp(o, 32, this.bottom - this.top - 8);
            int p = (int)this.getScrollAmount() * (this.bottom - this.top - o) / n + this.top;
            if (p < this.top) {
                p = this.top;
            }
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(i, this.bottom, 0.0).texture(0.0, 1.0).color(0, 0, 0, 255).next();
            bufferBuilder.vertex(j, this.bottom, 0.0).texture(1.0, 1.0).color(0, 0, 0, 255).next();
            bufferBuilder.vertex(j, this.top, 0.0).texture(1.0, 0.0).color(0, 0, 0, 255).next();
            bufferBuilder.vertex(i, this.top, 0.0).texture(0.0, 0.0).color(0, 0, 0, 255).next();
            tessellator.draw();
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(i, p + o, 0.0).texture(0.0, 1.0).color(128, 128, 128, 255).next();
            bufferBuilder.vertex(j, p + o, 0.0).texture(1.0, 1.0).color(128, 128, 128, 255).next();
            bufferBuilder.vertex(j, p, 0.0).texture(1.0, 0.0).color(128, 128, 128, 255).next();
            bufferBuilder.vertex(i, p, 0.0).texture(0.0, 0.0).color(128, 128, 128, 255).next();
            tessellator.draw();
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(i, p + o - 1, 0.0).texture(0.0, 1.0).color(192, 192, 192, 255).next();
            bufferBuilder.vertex(j - 1, p + o - 1, 0.0).texture(1.0, 1.0).color(192, 192, 192, 255).next();
            bufferBuilder.vertex(j - 1, p, 0.0).texture(1.0, 0.0).color(192, 192, 192, 255).next();
            bufferBuilder.vertex(i, p, 0.0).texture(0.0, 0.0).color(192, 192, 192, 255).next();
            tessellator.draw();
        }
        this.renderDecorations(mouseX, mouseY);
        GlStateManager.enableTexture();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlphaTest();
        GlStateManager.disableBlend();
		}
    }

	@Override
	public void renderBackground() {
		this.fillGradient(this.left, this.top, this.right, this.bottom, -1072689136, -804253680);
	}
	
	@Override
	protected void renderList(int x, int y, int mouseX, int mouseY, float delta) {
		double scaleH = this.minecraft.window.getHeight() / (double) this.minecraft.window.getScaledHeight();
		double scaleW = this.minecraft.window.getWidth() / (double) this.minecraft.window.getScaledWidth();
		GL11.glScissor((int)(this.left * scaleW), (int)(this.minecraft.window.getHeight() - (this.bottom * scaleH)), (int)(this.width * scaleW), (int)(this.height * scaleH));
		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		for (int i = 0; i < this.getItemCount(); ++i) {
			if (this.isSelectedItem(i)) {
				DrawingUtil.drawNoFillRect(this.getRowLeft()-2, this.getRowTop(i) -2, this.getRowLeft() + this.getRowWidth() - 8, this.getRowTop(i) + this.itemHeight - 4, 0xFFFFFFFF);
			}
			
			Entry entry = getEntry(i);
			//this.itemHeight - 4??
			entry.render(i, this.getRowTop(i), this.getRowLeft(), this.getRowWidth(), this.itemHeight-4, mouseX, mouseY, this.isMouseOver((double) mouseX, (double) mouseY) && Objects.equals(this.getEntryAtPosition((double) mouseX, (double) mouseY), entry), delta);
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	@Override
	protected boolean isFocused() {
		return true;
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.visible && super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.visible && super.mouseReleased(mouseX, mouseY, button);

	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return this.visible && super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		return this.visible && super.mouseScrolled(mouseX, mouseY, amount);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.visible && super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return this.visible && super.keyReleased(keyCode, scanCode, modifiers);

	}
	
	@Override
	public boolean charTyped(char chr, int modifiers) {
		return this.visible && super.charTyped(chr, modifiers);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return this.visible && super.isMouseOver(mouseX, mouseY);
	}
	
	public abstract class Entry extends EntryListWidget.Entry<FreeFormListWidget<E>.Entry>{
		@Override
		public abstract void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta);

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0) {
				this.onPressed();
				return true;
			}
				
			return false;
		}
		
		private void onPressed() {
			FreeFormListWidget.this.setSelected(this);
		}
	}
}
