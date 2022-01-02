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
import net.minecraft.util.math.MathHelper;

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
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX,
				int mouseY, boolean hovered, float tickDelta) {
			DrawableHelper.drawTextWithShadow(matrices, client.textRenderer,
					new TranslatableText(this.keyBinding.getTranslationKey()), x, y, 0xFFFFFFFF);
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
