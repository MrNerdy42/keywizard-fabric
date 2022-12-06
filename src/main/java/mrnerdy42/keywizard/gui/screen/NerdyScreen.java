package mrnerdy42.keywizard.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class NerdyScreen extends Screen {
	
	protected final Screen parent; 

	protected NerdyScreen(Text title, Screen parent) {
		super(title);
		this.parent = parent;
	}

}
