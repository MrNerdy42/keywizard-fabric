package mrnerdy42.keywizard.gui.screen;

import mrnerdy42.keywizard.gui.TickableElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class NerdyScreen extends Screen {
	
	protected final Screen parent; 

	protected NerdyScreen(String title, Screen parent) {
		super(Text.of(title));
		this.parent = parent;
	}
	

    protected <T extends Element> void addElement(T child) {
        this.children.add(child);
    }

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		for (Element e : this.children) {
			if (e instanceof Drawable) {
				((Drawable) e).render(matrices, mouseX, mouseY, delta);
			}
		}
	}
	
	@Override
	public void tick() {
		for (Element e : this.children) {
			if (e instanceof TickableElement) {
				((TickableElement) e).tick();
			}
		}
	}
}
