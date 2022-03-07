package mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.blaze3d.systems.RenderSystem;

import mrnerdy42.keywizard.util.DrawingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;

public class KeyboardWidget extends AbstractParentElement implements Drawable, TickableElement {
	public KeyWizardScreen keyWizardScreen;
	
	private HashMap<Integer, KeyboardKeyWidget> keys = new HashMap<>();
	private float anchorX;
	private float anchorY;
	
	protected KeyboardWidget(KeyWizardScreen keyWizardScreen, float anchorX, float anchorY) {
		this.keyWizardScreen = keyWizardScreen;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}
	
	public float addKey(float relativeX, float relativeY, float width, float height, float keySpacing, int keyCode) {
		this.keys.put(keyCode, new KeyboardKeyWidget(keyCode, this.anchorX + relativeX, this.anchorY + relativeY, width, height));
		return relativeX + width + keySpacing;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		List<? extends KeyboardKeyWidget> keys = this.children();
		for (KeyboardKeyWidget k : keys) {
			k.render(matrices, mouseX, mouseY, delta);
		}
		
		for (KeyboardKeyWidget k : keys) {
		    if (k.active && k.isHovered()) {
		        keyWizardScreen.renderTooltip(matrices, k.tooltipText, mouseX, mouseY);
		    }
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
		return new ArrayList<KeyboardKeyWidget>(this.keys.values());
	}
	

	@Override
	public void tick() {
		for (KeyboardKeyWidget k : this.children()) {
			k.tick();
		}
	}
	
	public class KeyboardKeyWidget extends PressableWidget implements TickableElement {	
		public float x;
		public float y;
		
		protected float width;
		protected float height;
		
		private InputUtil.Key key;
		private ArrayList<KeyBinding> bindings = new ArrayList<KeyBinding>();
		private List<Text> tooltipText = new ArrayList<Text>();
		
		protected KeyboardKeyWidget(int keyCode, float x, float y, float width, float height) {
			super((int) x, (int) y, (int) width, (int) height, Text.of(""));
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.key = InputUtil.Type.KEYSYM.createFromCode(keyCode);
			this.setMessage(this.key.getLocalizedText());
		}
		
		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			int bindings = this.bindings.size();
			int color = 0;
			if (this.active) {
				if (this.isHovered()) {
					color = 0xFFAAAAAA;
					if(bindings == 1) {
						color = 0xFF00AA00;
					} else if (bindings > 1) {
						color = 0xFFAA0000;
					}
				}else {
					color = 0xFFFFFFFF;
					if(bindings == 1) {
						color = 0xFF00FF00;
					} else if (bindings > 1) {
						color = 0xFFFF0000;
					}
				}
			} else {
				color = 0xFF555555;
			}
			DrawingUtil.drawNoFillRect(matrices, this.x, this.y, this.x + this.width, this.y + this.height, color);
			TextRenderer textRenderer = keyWizardScreen.getTextRenderer();
			textRenderer.drawWithShadow(matrices, this.getMessage(), (this.x + (this.width)/2 - textRenderer.getWidth(this.getMessage()) / 2), this.y + (this.height-6)/2, color);
        }

		@Override
		public void onPress() {
			this.playDownSound(MinecraftClient.getInstance().getSoundManager());
			keyWizardScreen.getSelectedKeyBinding().setBoundKey(this.key);
			KeyBinding.updateKeysByCode();
		}
		
		@SuppressWarnings("resource")
		private void updateBindings() {
			ArrayList<KeyBinding> bound = new ArrayList<>();
			
			for (KeyBinding k : MinecraftClient.getInstance().options.keysAll) {
				if (k.matchesKey(this.key.getCode(), -1)) {
					bound.add(k);
				}
			}
			
			this.bindings = bound;
		}
		
		private void updateTooltip() {
			//Style test = Style.EMPTY.withColor(TextColor.fromRgb(0x555555));
			ArrayList<MutableText> tooltipText = new ArrayList<>();
			for (KeyBinding b : this.bindings) {
				tooltipText.add( new TranslatableText(b.getTranslationKey()) );/*.append(new TranslatableText(" (this is a test)").setStyle(test)));*/
			}	
			this.tooltipText = tooltipText.stream().sorted((a, b) -> a.asString().compareTo(b.asString())).collect(Collectors.toList());
		}

		@Override
		public void tick() {
			updateBindings();
			updateTooltip();
		}
		

	}

}
