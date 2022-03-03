package mrnerdy42.keywizard.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindingListWidget extends FreeFormListWidget<KeyBindingListWidget.BindingEntry> /*t<KeyBindingListWidget.BindingEntry>*/ {

	public KeyBindingListWidget(MinecraftClient client, int top, int left, int width, int height, int itemHeight) {
		super(client, top, left, width, height, itemHeight);
		
		for (KeyBinding k : this.client.options.keysAll) {
			this.addEntry(new BindingEntry(k));
		}
		this.setSelected(this.children().get(0));
	}
	
	public KeyBinding getSelectedKeyBinding() {
		return ((BindingEntry)this.getSelected()).keyBinding;
	}

	/*
	@Override
	protected void renderList(MatrixStack matrices, int x, int y, int mouseX, int mouseY, float delta) {
		double scaleH = this.client.getWindow().getHeight() / (double) this.client.getWindow().getScaledHeight();
		double scaleW = this.client.getWindow().getWidth() / (double) this.client.getWindow().getScaledWidth();
		RenderSystem.enableScissor((int)(this.left * scaleW), (int)(this.client.getWindow().getHeight() - (this.bottom * scaleH)), (int)(this.width * scaleW), (int)(this.height * scaleH));

		for (int i = 0; i < this.getEntryCount(); ++i) {
			if (this.isSelectedEntry(i)) {
				DrawingUtil.drawNoFillRect(matrices, this.getRowLeft()-2, this.getRowTop(i) -2, this.getRowRight() - 8, this.getRowTop(i) + this.itemHeight - 4, 0xFFFFFFFF);
			}
			
			BindingEntry entry = (BindingEntry) getEntry(i);
			//this.itemHeight - 4??
			entry.render(matrices, i, this.getRowTop(i), this.getRowLeft(), this.getRowWidth(), this.itemHeight-4, mouseX, mouseY, this.isMouseOver((double) mouseX, (double) mouseY) && Objects.equals(this.getEntryAtPosition((double) mouseX, (double) mouseY), entry), delta);
		}
		RenderSystem.disableScissor();
	}
	*/

	@Override
	protected boolean isFocused() {
		return true;
	}

	public class BindingEntry extends FreeFormListWidget<KeyBindingListWidget.BindingEntry>.Entry /*EntryListWidget.Entry<KeyBindingListWidget.BindingEntry>*/ {

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

	}
}
