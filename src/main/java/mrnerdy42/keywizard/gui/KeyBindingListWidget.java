package mrnerdy42.keywizard.gui;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

import mrnerdy42.keywizard.mixin.KeyBindingAccessor;
import mrnerdy42.keywizard.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Tickable;

public class KeyBindingListWidget extends FreeFormListWidget<KeyBindingListWidget.BindingEntry> implements Tickable {
	
	public KeyWizardScreen keyWizardScreen;
	private String currentFilterText = "";
	private String currentCategory = KeyBindingUtil.DYNAMIC_CATEGORY_ALL;

	public KeyBindingListWidget(KeyWizardScreen keyWizardScreen, int top, int left, int width, int height, int itemHeight) {
		super(MinecraftClient.getInstance(), top, left, width, height, itemHeight);
		this.keyWizardScreen = keyWizardScreen;
		
		for (KeyBinding k : this.minecraft.options.keysAll) {
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
		boolean filterUpdate = !this.currentFilterText.equals(this.keyWizardScreen.getFilterText());
		boolean categoryUpdate = !this.currentCategory.equals(this.keyWizardScreen.getSelectedCategory());
		
		if (categoryUpdate || filterUpdate) {			
			if (categoryUpdate) {
				this.currentCategory = this.keyWizardScreen.getSelectedCategory();
			}
			
			KeyBinding[] bindings = getBindingsByCategory(this.currentCategory);
			
			if (filterUpdate) {
				this.currentFilterText = this.keyWizardScreen.getFilterText();		
				if (!this.currentFilterText.equals("")) {
				    bindings = filterBindings(bindings, this.currentFilterText);
				}
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
			this.setScrollAmount(0);
		}
	}
	
	private KeyBinding[] filterBindings (KeyBinding[] bindings, String filterText) {
		KeyBinding[] bindingsFiltered = bindings;
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
	
	private KeyBinding[] filterBindingsByName(KeyBinding[] bindings, String bindingName){
		String[] words = bindingName.split("\\s+");
		KeyBinding[] bindingsFiltered = Arrays.stream(bindings).filter(binding -> {
				boolean flag = true;
				for (String w:words) {
					flag = flag && I18n.translate(binding.getId()).toLowerCase().contains(w.toLowerCase());
				}
				return flag;
			}).toArray(KeyBinding[]::new);
		return bindingsFiltered;
	}
	
	private KeyBinding[] filterBindingsByKey(KeyBinding[] bindings, String keyName) {
		return Arrays.stream(bindings).filter(b -> {
			String otherKeyName;
			if (I18n.hasTranslation(((KeyBindingAccessor)b).getKeyCode().getName())) {
				otherKeyName = I18n.translate(((KeyBindingAccessor)b).getKeyCode().getName());
			} else {
				otherKeyName = InputUtil.getKeycodeName(((KeyBindingAccessor)b).getKeyCode().getKeyCode());
			}
			return I18n.translate(otherKeyName).toLowerCase().equals(keyName.toLowerCase());
		}).toArray(KeyBinding[]::new);
	}
	
	private KeyBinding[] getBindingsByCategory(String category) {
		KeyBinding[] bindings = Arrays.copyOf(this.minecraft.options.keysAll, this.minecraft.options.keysAll.length);
		switch (category) {
		case KeyBindingUtil.DYNAMIC_CATEGORY_ALL:
		    return bindings;
		case KeyBindingUtil.DYNAMIC_CATEGORY_CONFLICTS:
			Map<InputUtil.KeyCode, Integer> bindingCounts = KeyBindingUtil.getBindingCountsByKey();
			return Arrays.stream(bindings).filter(b -> bindingCounts.get(((KeyBindingAccessor)b).getKeyCode()) > 1  && ((KeyBindingAccessor)b).getKeyCode().getKeyCode() != -1).toArray(KeyBinding[]::new) ;
		case KeyBindingUtil.DYNAMIC_CATEGORY_UNBOUND:
			return Arrays.stream(bindings).filter(b -> b.isNotBound()).toArray(KeyBinding[]::new);
		default:
			return Arrays.stream(bindings).filter(b -> b.getCategory() == category).toArray(KeyBinding[]::new);
		}
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
		public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			minecraft.textRenderer.drawWithShadow(I18n.translate(keyBinding.getId()), x, y, 0xFFFFFFFF);
			int color = 0xFF999999;
			minecraft.textRenderer.drawWithShadow(this.keyBinding.getLocalizedName(), x, y + minecraft.textRenderer.fontHeight + 5, color);
		}

	}
	

}
