package mrnerdy42.keywizard.gui;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TextUtil {
	public static Text guiTextOf(String s) {
		return new TranslatableText(s);
	}
}
