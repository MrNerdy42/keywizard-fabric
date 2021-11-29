package mrnerdy42.keywizard.gui;

import java.util.HashMap;
import java.util.List;

import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class KeyboardWidget extends AbstractParentElement{
	public KeyWizardScreen parent;
	
	protected HashMap<Integer, KeyboardKeyWidget> keyList = new HashMap<>();
	
	private double scaleFactor;

	public int x;
	public int y;
	
	public KeyboardWidget(int x, int y, int width, int height) {
		this.keyList.put(0, new KeyboardKeyWidget(this.x, this.y, 20, 20, this));
		this.x = x;
		this.y = y;
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		System.out.println(this.x);
		for (KeyboardKeyWidget k : keyList.values()) {
			k.render(matrices, mouseX, mouseY, delta);
		}
	}
	
	@Override
	public List<? extends Element> children() {
		return (List<? extends Element>) List.of(this.keyList.values());
	}
	
	private class KeyboardKeyWidget extends PressableWidget implements Element{
		
		public int keyCode;
		//public String displayString;

		//protected boolean hovered;
		
		private KeyboardWidget keyboard;
		
		public KeyboardKeyWidget(int x, int y, int width, int height, KeyboardWidget keyboard) {
			super(x, y, width, height, Text.of("F")); // super might not be setting x and y
			this.keyboard = keyboard;
			this.visible = true;
		}
		
		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int modifiedBindings = 0;
			int unmodifiedBindings = 0;
			int color = 0;
			if (this.active) {
				if (this.hovered) {
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
			System.out.println(this.x);
			drawNoFillRect(matrices, this.x, this.y, this.x + this.width, this.y + this.height, color);
			//draw(matrices, getMessage(), (float)(this.x+(this.width+2)/2.0F), (float)(this.y+(this.height-6)/2.0F), color);
			DrawableHelper.drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, "F", this.x, this.y, 0xFFFFFFFF);
			//drawCenteredString(this.keyboard.parent.getFontRenderer(), this.displayString, (float)(this.absX()+(this.width+2)/2.0F), (float)(this.absY()+(this.height-6)/2.0F), color & 0x00FFFFFF);
		}

		@Override
		public void onPress() {
			if(this.isHovered() && this.active) {
				this.playDownSound(MinecraftClient.getInstance().getSoundManager());
			}
		}
		
		protected void drawNoFillRect(MatrixStack matrices, int left, int top, int right, int bottom, int color) {
			drawHorizontalLine(matrices, left, right, top, color);
			drawHorizontalLine(matrices, left, right, bottom, color);
			drawVerticalLine(matrices, left, top, bottom, color);
			drawVerticalLine(matrices, right, top, bottom, color);
		}
	}

}
