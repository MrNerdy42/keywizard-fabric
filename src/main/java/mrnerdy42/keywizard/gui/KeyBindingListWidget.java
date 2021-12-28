package mrnerdy42.keywizard.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;

public class KeyBindingListWidget extends EntryListWidget<KeyBindingListWidget.BindingEntry>{

	public KeyBindingListWidget(MinecraftClient client, int top, int left, int width, int height, int itemHeight) {
		super(client, width, height, top, top + height, itemHeight);
		this.left = left;
		
		for (KeyBinding k : this.client.options.keysAll) {
			this.addEntry(new BindingEntry(k));
		}
	}

	
    public class BindingEntry extends EntryListWidget.Entry<KeyBindingListWidget.BindingEntry> {
    	
    	private final KeyBinding keyBinding;
    	
    	public BindingEntry(KeyBinding keyBinding) {
    		this.keyBinding = keyBinding;
    	}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			DrawableHelper.drawCenteredText(matrices, client.textRenderer, this.keyBinding.getTranslationKey(), x, y, 0xFFFFFFFF);
		}
		
    	
    }
}
