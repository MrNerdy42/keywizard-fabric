package mrnerdy42.keywizard.gui;

import java.util.Arrays;

import org.jetbrains.annotations.Nullable;

import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindingListWidget extends FreeFormListWidget<KeyBindingListWidget.BindingEntry> implements TickableElement {
	
	public KeyWizardScreen keyWizardScreen;
	private String searchText = "";
	private String category = KeyBindingUtil.DYNAMIC_CATEGORY_ALL;

	public KeyBindingListWidget(KeyWizardScreen keyWizardScreen, int top, int left, int width, int height, int itemHeight) {
		super(MinecraftClient.getInstance(), top, left, width, height, itemHeight);
		this.keyWizardScreen = keyWizardScreen;
		
		for (KeyBinding k : this.client.options.keysAll) {
			this.addEntry(new BindingEntry(k));
		}
		this.setSelected(this.children().get(0));
	}
	
	@Nullable
	public KeyBinding getSelectedKeyBinding() {
		if (this.getSelected() == null) {
			return null;
		}
		return ((BindingEntry)this.getSelected()).keyBinding;
	}
	
	private void updateList() {
		if (!this.searchText.equals(this.keyWizardScreen.getSearchText()) || !this.category.equals(this.keyWizardScreen.getSelectedCategory())) {
			this.category = this.keyWizardScreen.getSelectedCategory();
			KeyBinding[] bindings = getBindingsByCategory(this.category);
			if (!this.searchText.equals(this.keyWizardScreen.getSearchText())) {
				//String[] words = this.searchText.split("\\s+");
			}
			this.children().clear();
			if (bindings.length > 0) {
				for (KeyBinding k : bindings) {
					this.addEntry(new BindingEntry(k));
				}
				this.setSelected(this.children().get(0));
			} else {
				this.setSelected(null);
			}
		}
	}
	
	private KeyBinding[] getBindingsByCategory(String category) {
		KeyBinding[] bindings = Arrays.copyOf(this.client.options.keysAll, this.client.options.keysAll.length);
		switch (category) {
		case KeyBindingUtil.DYNAMIC_CATEGORY_ALL:
		    return bindings;
		case KeyBindingUtil.DYNAMIC_CATEGORY_CONFLICTS:
			return bindings;
		case KeyBindingUtil.DYNAMIC_CATEGORY_UNBOUND:
			return Arrays.stream(bindings).filter(b -> b.isUnbound()).toArray(KeyBinding[]::new);
		default:
			return Arrays.stream(bindings).filter(b -> b.getCategory() == category).toArray(KeyBinding[]::new);
		}
	}
	
	private KeyBinding[] filterBindingsByName(KeyBinding[] bindings, String[] words){
		return null;
	}
	
	private KeyBinding[] filterBindingsByKey(KeyBinding[] bindings, String keyName) {
		return null;
	}
	
	@Override
	public void tick() {
		updateList();
	}

	public class BindingEntry extends FreeFormListWidget<KeyBindingListWidget.BindingEntry>.Entry{

		private final KeyBinding keyBinding;

		public BindingEntry(KeyBinding keyBinding) {
			this.keyBinding = keyBinding;
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			client.textRenderer.drawWithShadow(matrices, new TranslatableText(this.keyBinding.getTranslationKey()), x, y, 0xFFFFFFFF);
			int color = 0xFF999999;
			client.textRenderer.drawWithShadow(matrices, this.keyBinding.getBoundKeyLocalizedText(), x, y + client.textRenderer.fontHeight + 5, color);
		}

	}
	

}
