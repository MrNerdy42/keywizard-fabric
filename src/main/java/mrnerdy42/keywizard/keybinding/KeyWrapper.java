package mrnerdy42.keywizard.keybinding;

import mrnerdy42.keywizard.mixin.KeyAccessor;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

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
		Text t = this.key.getLocalizedText();
		if (t instanceof TranslatableText) {
			return I18n.translate(((TranslatableText) t).getKey());
		} else {
			return t.getString();
		}
	}
	
	public InputUtil.Type getType() {
		return ((KeyAccessor)((Object)this.key)).getType();
	}
	
	public int getCode() {
		return this.key.getCode();
	}
	
	public static KeyWrapper createKeyboardKeyFromCode(int keyCode) {
		return new KeyWrapper(InputUtil.Type.KEYSYM.createFromCode(keyCode));
	}
	
	public static KeyWrapper createMouseKeyFromCode(int keyCode) {
		return new KeyWrapper(InputUtil.Type.MOUSE.createFromCode(keyCode));
	}

	@Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return this.key.equals(((KeyWrapper)other).key);
    }
	
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

}
