package mrnerdy42.keywizard.gui;

import java.util.Objects;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindingListWidget extends EntryListWidget<KeyBindingListWidget.BindingEntry> {

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
		int i = this.getEntryCount();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		for (int j = 0; j < i; ++j) {
			int k = this.getRowTop(j);
			//int l = this.getRowTop(j) + this.itemHeight;
			int m = y + j * this.itemHeight + this.headerHeight;
			int n = this.itemHeight - 4;
			BindingEntry entry = this.getEntry(j);
			int o = this.getRowWidth();
			int r;
			if (this.isSelectedEntry(j)) {
				r = this.left + this.width / 2 - o / 2;
				int q = this.left + this.width / 2 + o / 2;
				RenderSystem.disableTexture();
				float f = this.isFocused() ? 1.0F : 0.5F;
				RenderSystem.color4f(f, f, f, 1.0F);
				bufferBuilder.begin(7, VertexFormats.POSITION);
				bufferBuilder.vertex((double) r, (double) (m + n + 2), 0.0D).next(); //draw a white square behind the selected item.
				bufferBuilder.vertex((double) q, (double) (m + n + 2), 0.0D).next();
				bufferBuilder.vertex((double) q, (double) (m - 2), 0.0D).next();
				bufferBuilder.vertex((double) r, (double) (m - 2), 0.0D).next();
				tessellator.draw();
				RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
				bufferBuilder.begin(7, VertexFormats.POSITION);
				bufferBuilder.vertex((double) (r + 1), (double) (m + n + 1), 0.0D).next(); // fill in an alpha square inside the white square.
				bufferBuilder.vertex((double) (q - 1), (double) (m + n + 1), 0.0D).next();
				bufferBuilder.vertex((double) (q - 1), (double) (m - 1), 0.0D).next();
				bufferBuilder.vertex((double) (r + 1), (double) (m - 1), 0.0D).next();
				tessellator.draw();
				RenderSystem.enableTexture();
			}

			r = this.getRowLeft();
			entry.render(matrices, j, k, r, o, n, mouseX, mouseY, this.isMouseOver((double) mouseX, (double) mouseY)
					&& Objects.equals(this.getEntryAtPosition((double) mouseX, (double) mouseY), entry), delta);
		}
		RenderSystem.disableScissor();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawHorizontalLine(matrices, this.left, this.right, this.top, 0xFFFFFFFF);
		drawHorizontalLine(matrices, this.left, this.right, this.bottom, 0xFFFFFFFF);
		drawVerticalLine(matrices, this.left, this.top, this.bottom, 0xFFFFFFFF);
		drawVerticalLine(matrices, this.right, this.top, this.bottom, 0xFFFFFFFF);
	}

	@Override
	protected boolean isFocused() {
		return true;
	}

	public class BindingEntry extends EntryListWidget.Entry<KeyBindingListWidget.BindingEntry> {

		private final KeyBinding keyBinding;

		public BindingEntry(KeyBinding keyBinding) {
			this.keyBinding = keyBinding;
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			client.textRenderer.drawWithShadow(matrices, new TranslatableText(this.keyBinding.getTranslationKey()), x, y, 0xFFFFFFFF);
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
