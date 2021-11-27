package mrnerdy42.keywizard.gui;

import java.util.HashMap;

import mrnerdy42.keywizard.gui.KeyWizardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class KeyboardWidget extends ClickableWidget{

	public KeyboardWidget(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
		// TODO Auto-generated constructor stub
	}

	public KeyWizardScreen parent;
	
	protected HashMap<Integer, KeyboardKeyWidget> keyList = new HashMap<>();
	
	private double scaleFactor;
	
	private class KeyboardKeyWidget extends PressableWidget{
		
		public int keyCode;
		public String displayString;

		protected boolean hovered;
		
		private KeyboardWidget keyboard;
		
		public KeyboardKeyWidget(int x, int y, int width, int height, KeyboardWidget keyboard) {
			super(x, y, width, height, Text.of(""));
			this.keyboard = keyboard;
		}
		
		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			
		}
		

		public void drawKey(Minecraft mc, double mouseX, double mouseY, float partialTicks) {
			this.hovered = mouseX >= this.absX() && mouseY >= this.absY() && mouseX < this.absX() + this.width && mouseY < this.absY() + this.height;
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
			
			this.draw

			drawNoFillRect(this.absX(), this.absY(), this.absX() + this.width, this.absY() + this.height, color);
			drawCenteredString(this.keyboard.parent.getFontRenderer(), this.displayString, (float)(this.absX()+(this.width+2)/2.0F), (float)(this.absY()+(this.height-6)/2.0F), color & 0x00FFFFFF);
		}

		@Override
		public void onPress() {
			if(this.isHovered() && this.active) {
				this.playDownSound(MinecraftClient.getInstance().getSoundManager());
			}
		}
		
		protected void drawNoFillRect(double left, double top, double right, double bottom, int color) {
			drawHorizontalLine(left, right, top, color);
			drawHorizontalLine(left, right, bottom, color);
			drawVerticalLine(left, top, bottom, color);
			drawVerticalLine(right, top, bottom, color);
		}
		
		public double absX() {
			return this.keyboard.x + this.x;
		}
		public double absY() {
			return this.keyboard.y + this.y;
		}
	}

}
