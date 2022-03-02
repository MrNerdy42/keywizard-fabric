package mrnerdy42.keywizard.gui;

import java.util.Objects;

import com.mojang.blaze3d.systems.RenderSystem;

import mrnerdy42.keywizard.util.DrawingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindingListWidget extends FreeFormListWidget /*t<KeyBindingListWidget.BindingEntry>*/ {

	public KeyBindingListWidget(MinecraftClient client, int top, int left, int width, int height, int itemHeight) {
		super(client, 0, 0, 0, 0, itemHeight);
		this.height = height;
		this.top = top;
		this.left = left;
		this.width = width;

		this.bottom = top + height;
		this.right = left + width;

		this.method_31322(false);
		this.method_31323(false);

		for (KeyBinding k : this.client.options.keysAll) {
			this.addEntry(new BindingEntry(k));
		}
		this.setSelected(this.children().get(0));
	}

	@Override
	protected int getScrollbarPositionX() {
		return this.left + this.width - 5;
		// return super.getScrollbarPositionX();
	}

	@Override
	public int getRowWidth() {
		return this.width;
	}

	@Override
	public void renderBackground(MatrixStack matrices) {
		this.fillGradient(matrices, this.left, this.top, this.right, this.bottom, -1072689136, -804253680);
	}
	
	public KeyBinding getSelectedKeyBinding() {
		return this.getSelected().keyBinding;
	}
	
	@Override
	protected void renderList(MatrixStack matrices, int x, int y, int mouseX, int mouseY, float delta) {
		double scaleH = this.client.getWindow().getHeight() / (double) this.client.getWindow().getScaledHeight();
		double scaleW = this.client.getWindow().getWidth() / (double) this.client.getWindow().getScaledWidth();
		RenderSystem.enableScissor((int)(this.left * scaleW), (int)(this.client.getWindow().getHeight() - (this.bottom * scaleH)), (int)(this.width * scaleW), (int)(this.height * scaleH));

		for (int i = 0; i < this.getEntryCount(); ++i) {
			if (this.isSelectedEntry(i)) {
				DrawingUtil.drawNoFillRect(matrices, this.getRowLeft()-2, this.getRowTop(i) -2, this.getRowRight() - 8, this.getRowTop(i) + this.itemHeight - 4, 0xFFFFFFFF);
			}
			
			BindingEntry entry = getEntry(i);
			//this.itemHeight - 4??
			entry.render(matrices, i, this.getRowTop(i), this.getRowLeft(), this.getRowWidth(), this.itemHeight-4, mouseX, mouseY, this.isMouseOver((double) mouseX, (double) mouseY) && Objects.equals(this.getEntryAtPosition((double) mouseX, (double) mouseY), entry), delta);
		}
		RenderSystem.disableScissor();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		//drawHorizontalLine(matrices, this.left, this.right, this.top, 0xFFFFFFFF);
		//drawHorizontalLine(matrices, this.left, this.right, this.bottom, 0xFFFFFFFF);
		//drawVerticalLine(matrices, this.left, this.top, this.bottom, 0xFFFFFFFF);
		//drawVerticalLine(matrices, this.right, this.top, this.bottom, 0xFFFFFFFF);
	}

	@Override
	protected boolean isFocused() {
		return true;
	}

	public class BindingEntry extends FreeFormListWidget.Entry /*EntryListWidget.Entry<KeyBindingListWidget.BindingEntry>*/ {

		private final KeyBinding keyBinding;

		public BindingEntry(KeyBinding keyBinding) {
			this.keyBinding = keyBinding;
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			client.textRenderer.drawWithShadow(matrices, new TranslatableTe xt(this.keyBinding.getTranslationKey()), x, y, 0xFFFFFFFF);
			int color = 0xFF999999;
			client.textRenderer.drawWithShadow(matrices, this.keyBinding.getBoundKeyLocalizedText(), x, y + client.textRenderer.fontHeight + 5, color);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0) {
				this.onPressed();
				return true;
			} else {
				return false;
			}
		}

		private void onPressed() {
			KeyBindingListWidget.this.setSelected(this);
		}
	}
}
