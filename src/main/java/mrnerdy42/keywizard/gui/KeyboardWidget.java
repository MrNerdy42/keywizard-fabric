package mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class KeyboardWidget extends AbstractParentElement implements Drawable {
	public int x;
	public int y;
	//public KeyWizardScreen parent;
	
	private ArrayList<KeyboardRow> rows = new ArrayList<>();
	private int keySpacing = 5;
	private int nextRowY;
	
	protected KeyboardWidget(int x, int y) {
		this.x = x;
		this.y = y;
		this.nextRowY = y;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for (KeyboardKeyWidget k : this.children()) {
			k.render(matrices, mouseX, mouseY, delta);
		}
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (KeyboardKeyWidget k : this.children()) {
			if ( k.mouseClicked(mouseX, mouseY, button) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<? extends KeyboardKeyWidget> children() {
		return this.rows.stream().flatMap(r -> r.keys.stream()).toList();
	}
	
	protected void addRow(int defaultHeight) {
		this.rows.add(new KeyboardRow(this, this.nextRowY, defaultHeight));
		this.nextRowY += defaultHeight + this.keySpacing;
	}
	
	public KeyboardRow getRow(int index) {
		return this.rows.get(index);
	}
	
	public class KeyboardKeyWidget extends PressableWidget implements Element{
		
		private InputUtil.Key key;
		private KeyboardWidget keyboard;
		
		protected KeyboardKeyWidget(KeyboardWidget keyboard, int keyCode, int x, int y, int width, int height) {
			super(x, y, width, height, Text.of(""));
			this.keyboard = keyboard;
			this.key = InputUtil.Type.KEYSYM.createFromCode(keyCode);
			this.setMessage(this.key.getLocalizedText());
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
			DrawableHelper.drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, this.getMessage(), this.x + (this.width + 2)/2, this.y + (this.height-6)/2, color);		
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
	
	public class KeyboardRow {
		private KeyboardWidget keyboard;
		private ArrayList<KeyboardKeyWidget> keys = new ArrayList<>();
		private int defaultHeight;
		private int y;
		private int nextKeyX;
		
		protected KeyboardRow(KeyboardWidget keyboard, int y, int defaultHeight) {
			this.keyboard = keyboard;
			this.defaultHeight = defaultHeight;
			this.y = y;
			this.nextKeyX = keyboard.x;
		}
		
		protected void addKey(int keyCode, int width) {
			addKeyWithHeight(keyCode, width, this.defaultHeight);
		}
		
		protected void addKeyWithHeight(int keyCode, int width, int height) {
			this.keys.add(new KeyboardKeyWidget(this.keyboard, keyCode, nextKeyX, y, width, height));
			this.nextKeyX += width + this.keyboard.keySpacing; 
		}
		
		public int getHeight() {
			return this.defaultHeight;
		}
		
		public int getWidth() {
			return 0;
		}
	}

}
