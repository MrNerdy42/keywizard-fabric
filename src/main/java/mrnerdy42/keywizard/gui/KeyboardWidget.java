package mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import mrnerdy42.keywizard.util.DrawingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
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
		this.keys.put(keyCode,
				new KeyboardKeyWidget(keyCode, this.anchorX + relativeX, this.anchorY + relativeY, width, height));
		return relativeX + width + keySpacing;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		List<? extends KeyboardKeyWidget> keys = this.children();
		for (KeyboardKeyWidget k : keys) {
			k.render(matrices, mouseX, mouseY, delta);
		}

		if (!keyWizardScreen.getCategorySelectorExtended()) {
			for (KeyboardKeyWidget k : keys) {
				if (k.active && k.isHovered()) {
					keyWizardScreen.renderTooltip(matrices, k.tooltipText, mouseX, mouseY);
				}
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (!keyWizardScreen.getCategorySelectorExtended()) {
			for (KeyboardKeyWidget k : this.children()) {
				if (k.mouseClicked(mouseX, mouseY, button)) {
					return true;
				}
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
		private List<Text> tooltipText = new ArrayList<>();

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
			int bindingCount = this.tooltipText.size();
			int color = 0;
			if (this.active) {
				if (this.isHovered() && !keyWizardScreen.getCategorySelectorExtended()) {
					color = 0xFFAAAAAA;
					if (bindingCount == 1) {
						color = 0xFF00AA00;
					} else if (bindingCount > 1) {
						color = 0xFFAA0000;
					}
				} else {
					color = 0xFFFFFFFF;
					if (bindingCount == 1) {
						color = 0xFF00FF00;
					} else if (bindingCount > 1) {
						color = 0xFFFF0000;
					}
				}
			} else {
				color = 0xFF555555;
			}
			DrawingUtil.drawNoFillRect(matrices, this.x, this.y, this.x + this.width, this.y + this.height, color);
			@SuppressWarnings("resource")
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			textRenderer.drawWithShadow(matrices, this.getMessage(),
					(this.x + (this.width) / 2 - textRenderer.getWidth(this.getMessage()) / 2),
					this.y + (this.height - 6) / 2, color);
		}

		@Override
		public void onPress() {
			this.playDownSound(MinecraftClient.getInstance().getSoundManager());
			KeyBinding selectedKeyBinding = keyWizardScreen.getSelectedKeyBinding();
			if (selectedKeyBinding != null) {
				selectedKeyBinding.setBoundKey(this.key);
				KeyBinding.updateKeysByCode();
			}
		}

		@SuppressWarnings("resource")
		private void updateTooltip() {
			ArrayList<String> tooltipText = new ArrayList<>();
			for (KeyBinding b : MinecraftClient.getInstance().options.keysAll) {
				if (b.matchesKey(this.key.getCode(), -1)) {
					tooltipText.add(I18n.translate(b.getTranslationKey()));
				}
			}
			this.tooltipText = tooltipText.stream().sorted().map(s -> new TranslatableText(s)).collect(Collectors.toCollection(ArrayList<Text>::new));
		}

		@Override
		public void tick() {
			updateTooltip();
		}

	}

}
