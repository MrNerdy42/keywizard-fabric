package mrnerdy42.keywizard.keybinding;

import java.util.Objects;

import mrnerdy42.keywizard.mixin.KeyAccessor;
import net.minecraft.client.util.InputUtil;

public class KeyWrapper {
	
	private final InputUtil.Key key;
	
	KeyWrapper(InputUtil.Key key) {
		this.key = key;
	}
	
	InputUtil.Key getKey() {
		return this.key;
	}
	
	public String getUnlocalizedLabel() {
		return this.key.getTranslationKey();
	}
	
	public String getLocalizedLabel() {
		return this.key.getLocalizedText().asString();
	}
	
	public static KeyWrapper createKeyboardKeyFromCode(int keyCode) {
		return new KeyWrapper(InputUtil.Type.KEYSYM.createFromCode(keyCode));
	}
	
	public static KeyWrapper createMouseKeyFromCode(int keyCode) {
		return new KeyWrapper(InputUtil.Type.MOUSE.createFromCode(keyCode));
	}
	
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        KeyWrapper otherKey = (KeyWrapper)other;
        return this.getKey().getCode() == otherKey.getKey().getCode() && ((KeyAccessor)this.getKey()).getType() == ((KeyAccessor)otherKey.getKey()).getType();
    }

}
