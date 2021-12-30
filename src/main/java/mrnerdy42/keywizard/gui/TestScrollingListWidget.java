package mrnerdy42.keywizard.gui;

import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class TestScrollingListWidget extends AlwaysSelectedEntryListWidget<TestScrollingListWidget.LanguageEntry>{

		public TestScrollingListWidget(MinecraftClient client, int width, int height) {
			super(client, width, height, 32, height - 65 + 4, 18);
			Iterator var3 =  client.getLanguageManager().getAllLanguages().iterator();

			while(var3.hasNext()) {
				LanguageDefinition languageDefinition = (LanguageDefinition)var3.next();
				TestScrollingListWidget.LanguageEntry languageEntry = new TestScrollingListWidget.LanguageEntry(languageDefinition);
				this.addEntry(languageEntry);
				if (client.getLanguageManager().getLanguage().getCode().equals(languageDefinition.getCode())) {
					this.setSelected(languageEntry);
				}
			}

			if (this.getSelected() != null) {
				this.centerScrollOn(this.getSelected());
			}

		}

		protected int getScrollbarPositionX() {
			return super.getScrollbarPositionX() + 20;
		}

		public int getRowWidth() {
			return super.getRowWidth() + 50;
		}

		public void setSelected(@Nullable TestScrollingListWidget.LanguageEntry languageEntry) {
			super.setSelected(languageEntry);
			if (languageEntry != null) {
				NarratorManager.INSTANCE.narrate((new TranslatableText("narrator.select", new Object[]{languageEntry.languageDefinition})).getString());
			}

		}

		protected boolean isFocused() {
			return true;
		}

		@Environment(EnvType.CLIENT)
		public class LanguageEntry extends AlwaysSelectedEntryListWidget.Entry<TestScrollingListWidget.LanguageEntry> {
			private final LanguageDefinition languageDefinition;

			public LanguageEntry(LanguageDefinition languageDefinition) {
				this.languageDefinition = languageDefinition;
			}

			public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
				String string = this.languageDefinition.toString();
				client.textRenderer.drawWithShadow(matrices, string, (float)(width / 2 - client.textRenderer.getWidth(string) / 2), (float)(y + 1), 16777215, true);
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
