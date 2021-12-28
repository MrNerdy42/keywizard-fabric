package mrnerdy42.keywizard.gui;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

public class KeyBindingList extends EntryListWidget<KeyBindingList.Entry> {

	public KeyBindingList(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
		super(client, width, height, top, bottom, itemHeight);
		// TODO Auto-generated constructor stub
	}

	public class Entry extends ElementListWidget.Entry<KeyBindingList.Entry>{

		@Override
		public List<? extends Element> children() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX,
				int mouseY, boolean hovered, float tickDelta) {
			// TODO Auto-generated method stub
			
		}

	}

}
