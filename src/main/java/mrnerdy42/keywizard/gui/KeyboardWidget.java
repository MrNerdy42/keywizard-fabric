package mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class KeyboardWidget extends AbstractParentElement implements Drawable {
	public int x;
	public int y;
	public KeyWizardScreen parent;
	
	private ArrayList<ArrayList<KeyboardKeyWidget>> rows = new ArrayList<>();
	private double scaleFactor;
	
	public KeyboardWidget(int x, int y) {
		this.x = x;
		this.y = y;
		this.addRow();
		this.addKey(x, 0, 20, 20);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for (KeyboardKeyWidget k : this.children()) {
			k.render(matrices, mouseX, mouseY, delta);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (KeyboardKeyWidget e : this.children()) {
			if ( e.mouseClicked(mouseX, mouseY, button) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<? extends KeyboardKeyWidget> children() {
		return this.rows.stream().flatMap(ArrayList::stream).toList();
	}
	
	public void addKey(int x, int row, int width, int height) throws IndexOutOfBoundsException {
		this.rows.get(row).add(new KeyboardKeyWidget(x, 0, width, height, this));
	}
	
	protected void addRow() {
		this.rows.add(new ArrayList<KeyboardKeyWidget>());
	}
	
	protected class KeyboardKeyWidget extends PressableWidget implements Element{
		
		public int keyCode;
		//public String displayString;

		//protected boolean hovered;
		
		private KeyboardWidget keyboard;
		
		public KeyboardKeyWidget(int x, int y, int width, int height, KeyboardWidget keyboard) {
			super(x, y, width, height, Text.of("F"));
			this.keyboard = keyboard;
			this.visible = true;
		}
		
		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			//this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int modifiedBindings = 0;
			int unmodifiedBindings = 0;
			int color = 0;
			if (this.active) {
				if (this.isHovered()) {
					color = 0xFFAAAAAA;
					if(modifiedBindings == 1) {
						color = 0xFF00AA00;
					} else if (modifiedBindings > 1) {
						color = 0xFFAA0000;
					}
				}else {
					color = 0xFFFFFFFF;
					if(modifiedBindings == 1) {
						color = 0xFF00FF00;
					} else if (modifiedBindings > 1) {
						color = 0xFFFF0000;
					}
				}
			} else {
				color = 0xFF555555;
			}
			drawNoFillRect(matrices, this.x, this.y, this.x + this.width, this.y + this.height, color);
			DrawableHelper.drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, "F", this.x + (this.width + 2)/2, this.y + (this.height-6)/2, color);		
        }

		@Override
		public void onPress() {
			System.out.println("test!");
			this.playDownSound(MinecraftClient.getInstance().getSoundManager());
		}
		
		protected void drawNoFillRect(MatrixStack matrices, int left, int top, int right, int bottom, int color) {
			drawHorizontalLine(matrices, left, right, top, color);
			drawHorizontalLine(matrices, left, right, bottom, color);
			drawVerticalLine(matrices, left, top, bottom, color);
			drawVerticalLine(matrices, right, top, bottom, color);
		}
	}

}
