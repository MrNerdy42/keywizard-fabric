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

	public class BindingEntry extends FreeFormListWidget<KeyBindingListWidget.BindingEntry>.Entry{

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
