package mrnerdy42.keywizard.keybinding;

import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;

public class KeyWrapper {
	
	private final Key key;
	
	public KeyWrapper(Key key) {
		this.key = key;
	}
	
	Key getKey() {
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
}
