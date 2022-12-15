package mrnerdy42.keywizard.gui.widget;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

import mrnerdy42.keywizard.gui.screen.KeyWizardScreen;
import mrnerdy42.keywizard.keybinding.KeyBindingUtil;
import mrnerdy42.keywizard.keybinding.KeyBindingWrapper;
import mrnerdy42.keywizard.keybinding.KeyWrapper;
import mrnerdy42.keywizard.mixin.KeyBindingAccessor;
import net.minecraft.client.MinecraftClient;
import mrnerdy42.keywizard.gui.TickableElement;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class KeyBindingListWidget extends FreeFormListWidget<KeyBindingListWidget.BindingEntry> implements TickableElement {
	
	public KeyWizardScreen keyWizardScreen;
	private String currentFilterText = "";
	private String currentCategory = KeyBindingUtil.DYNAMIC_CATEGORY_ALL;

	public KeyBindingListWidget(KeyWizardScreen keyWizardScreen, int top, int left, int width, int height, int itemHeight) {
		super(MinecraftClient.getInstance(), top, left, width, height, itemHeight);
		this.keyWizardScreen = keyWizardScreen;
		
		for (KeyBindingWrapper k : KeyBindingUtil.getKeyBindings()) {
			this.addEntry(new BindingEntry(k));
		}
		this.setSelected(this.children().get(0));
	}
	
	@Nullable
	public KeyBindingWrapper getSelectedKeyBinding() {
		if (this.getSelected() == null) {
			return null;
		}
		return ((BindingEntry)this.getSelected()).keyBinding;
	}
	
	private void updateList() {
		boolean filterUpdate = !this.currentFilterText.equals(this.keyWizardScreen.getFilterText());
		boolean categoryUpdate = !this.currentCategory.equals(this.keyWizardScreen.getSelectedCategory());
		
		if (categoryUpdate || filterUpdate) {			
			if (categoryUpdate) {
				this.currentCategory = this.keyWizardScreen.getSelectedCategory();
			}
			
			KeyBindingWrapper[] bindings = getBindingsByCategory(this.currentCategory);
			
			if (filterUpdate) {
				this.currentFilterText = this.keyWizardScreen.getFilterText();		
				if (!this.currentFilterText.equals("")) {
				    bindings = filterBindings(bindings, this.currentFilterText);
				}
			}
			
			this.children().clear();
			if (bindings.length > 0) {
				for (KeyBindingWrapper k : bindings) {
					this.addEntry(new BindingEntry(k));
				}
				this.setSelected(this.children().get(0));
			} else {
				this.setSelected(null);
			}
			this.setScrollAmount(0);
		}
	}
	
	private KeyBindingWrapper[] filterBindings (KeyBindingWrapper[] bindings, String filterText) {
		KeyBindingWrapper[] bindingsFiltered = bindings;
		String keyNameRegex = "<.*>";
		Matcher keyNameMatcher = Pattern.compile(keyNameRegex).matcher(filterText);
		
		
		if (keyNameMatcher.find()) {
			String keyNameWithBrackets = keyNameMatcher.group();
			String keyName = keyNameWithBrackets.replace("<", "").replace(">", "");
			filterText = filterText.replace(keyNameWithBrackets, "");
			bindingsFiltered = filterBindingsByKey(bindingsFiltered, keyName);
		}
		
		if (!filterText.equals("")) {
			bindingsFiltered = filterBindingsByName(bindingsFiltered, filterText);
		}
		
		return bindingsFiltered;
	}
	
	private KeyBindingWrapper[] filterBindingsByName(KeyBindingWrapper[] bindings, String bindingName){
		String[] words = bindingName.split("\\s+");
		KeyBindingWrapper[] bindingsFiltered = Arrays.stream(bindings).filter(binding -> {
				boolean flag = true;
				for (String w:words) {
					flag = flag && binding.getLocalizedName().toLowerCase().contains(w.toLowerCase());
				}
				return flag;
			}).toArray(KeyBindingWrapper[]::new);
		return bindingsFiltered;
	}
	
	private KeyBindingWrapper[] filterBindingsByKey(KeyBindingWrapper[] bindings, String keyName) {
		return Arrays.stream(bindings).filter(b -> b.getBoundKey().getLocalizedLabel().toLowerCase().equals(keyName.toLowerCase())).toArray(KeyBindingWrapper[]::new);
	}
	
	private KeyBindingWrapper[] getBindingsByCategory(String category) {
		KeyBindingWrapper[] bindings = KeyBindingUtil.getKeyBindings();
		switch (category) {
		case KeyBindingUtil.DYNAMIC_CATEGORY_ALL:
		    return bindings;
		case KeyBindingUtil.DYNAMIC_CATEGORY_CONFLICTS:
			Map<KeyWrapper, Integer> bindingCounts = KeyBindingUtil.getBindingCountsByKey();
			return Arrays.stream(bindings).filter(b -> bindingCounts.get(b.getBoundKey()) > 1  && b.getBoundKey().getCode() != -1).toArray(KeyBindingWrapper[]::new) ;
		case KeyBindingUtil.DYNAMIC_CATEGORY_UNBOUND:
			return Arrays.stream(bindings).filter(b -> !b.isBound()).toArray(KeyBindingWrapper[]::new);
		default:
			return Arrays.stream(bindings).filter(b -> b.getCategory() == category).toArray(KeyBindingWrapper[]::new);
		}
	}

	@Override
	public void tick() {
		updateList();
	}

	public class BindingEntry extends FreeFormListWidget<KeyBindingListWidget.BindingEntry>.Entry{

		private final KeyBindingWrapper keyBinding;

		public BindingEntry(KeyBindingWrapper keyBinding) {
			this.keyBinding = keyBinding;
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			client.textRenderer.drawWithShadow(matrices, new TranslatableText(this.keyBinding.getUnlocalizedName()), x, y, 0xFFFFFFFF);
			int color = 0xFF999999;
			client.textRenderer.drawWithShadow(matrices, this.keyBinding.getBoundKey().getLocalizedLabel(), x, y + client.textRenderer.fontHeight + 5, color);
		}

	}
	

}
