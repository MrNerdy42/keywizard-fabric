package mrnerdy42.keywizard.keybinding;

import mrnerdy42.keywizard.mixin.KeyBindingAccessor;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingWrapper {
	
    private final KeyBindingAccessor keyBinding;
    
    KeyBindingWrapper(KeyBinding keyBinding) {
    	this.keyBinding = (KeyBindingAccessor) keyBinding;
    }
    
    public KeyWrapper getBoundKey() {
    	return new KeyWrapper(keyBinding.getBoundKey());
    }
}
