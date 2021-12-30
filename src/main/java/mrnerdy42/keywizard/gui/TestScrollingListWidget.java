package mrnerdy42.keywizard.gui;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import mrnerdy42.keywizard.gui.KeyBindingListWidget.BindingEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class TestScrollingListWidget extends AlwaysSelectedEntryListWidget<TestScrollingListWidget.LanguageEntry> {

	public TestScrollingListWidget(MinecraftClient client, int width, int height) {
		super(client, width, height, 32, height - 65 + 4, 18);
		
		for (KeyBinding k : this.client.options.keysAll) {
			this.addEntry(new LanguageEntry(k));
		}
		this.setSelected(this.children().get(0));
		
		this.method_31322(false);
		this.method_31323(false);

		/*
		while (var3.hasNext()) {
			LanguageDefinition languageDefinition = (LanguageDefinition) var3.next();
			TestScrollingListWidget.LanguageEntry languageEntry = new TestScrollingListWidget.LanguageEntry(
					languageDefinition);
			this.addEntry(languageEntry);
			if (client.getLanguageManager().getLanguage().getCode().equals(languageDefinition.getCode())) {
				this.setSelected(languageEntry);
			}
		}

		if (this.getSelected() != null) {
			this.centerScrollOn(this.getSelected());
		}
		*/

	}

	protected int getScrollbarPositionX() {
		return super.getScrollbarPositionX() + 20;
	}

	public int getRowWidth() {
		return super.getRowWidth() + 50;
	}

	public void setSelected(@Nullable TestScrollingListWidget.LanguageEntry languageEntry) {
		super.setSelected(languageEntry);

	}

	protected boolean isFocused() {
		return true;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		drawHorizontalLine(matrices, this.left, this.right, this.top, 0xFFFFFFFF);
		drawHorizontalLine(matrices, this.left, this.right, this.bottom, 0xFFFFFFFF);
		drawVerticalLine(matrices, this.left, this.top, this.bottom, 0xFFFFFFFF);
		drawVerticalLine(matrices, this.right, this.top, this.bottom, 0xFFFFFFFF);
	}

	@Environment(EnvType.CLIENT)
	public class LanguageEntry extends AlwaysSelectedEntryListWidget.Entry<TestScrollingListWidget.LanguageEntry> {
		private final KeyBinding keyBinding;

		public LanguageEntry(KeyBinding keyBinding) {
			this.keyBinding = keyBinding;
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			DrawableHelper.drawTextWithShadow(matrices, client.textRenderer, new TranslatableText(this.keyBinding.getTranslationKey()), x, y, 0xFFFFFFFF);
		}

		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0) {
				this.onPressed();
				return true;
			} else {
				return false;
			}
		}

		private void onPressed() {
			TestScrollingListWidget.this.setSelected(this);
		}
	}

}
