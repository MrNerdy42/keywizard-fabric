package mrnerdy42.keywizard.keybinding;

import mrnerdy42.keywizard.mixin.KeyBindingAccessor;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingWrapper {
	
    private final KeyBinding keyBinding;
    
    KeyBindingWrapper(KeyBinding keyBinding) {
    	this.keyBinding = keyBinding;
    }
    
    public KeyWrapper getBoundKey() {
    	return new KeyWrapper(((KeyBindingAccessor)keyBinding).getBoundKey());
    }
    
    public void setBoundKey(KeyWrapper k) {
    	this.keyBinding.setBoundKey(k.getKey());
    }
    
    public KeyWrapper getDefaultKey() {
    	return new KeyWrapper(this.keyBinding.getDefaultKey());
    }
    
    public String getUnlocalizedName() {
		return this.keyBinding.getTranslationKey();
    }
    
    // Note the NOT operator.
    public boolean isBound() {
    	return !this.keyBinding.isUnbound();
    }
    
    public String getCategory() {
    	return this.keyBinding.getCategory();
    }
}
