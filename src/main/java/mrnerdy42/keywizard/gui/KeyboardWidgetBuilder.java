package mrnerdy42.keywizard.gui;

import org.lwjgl.glfw.GLFW;

public class KeyboardWidgetBuilder {
	
	public static KeyboardWidget emptyKeyboard(int x, int y) {
		return new KeyboardWidget(x, y);
	}
	
	public static KeyboardWidget testKeyboard(int x, int y) {
		KeyboardWidget kb = emptyKeyboard(x,y);
		/*
		kb.addRow(20);
		kb.getRow(0).addKey(GLFW.GLFW_KEY_Q, 20);
		*/
		return kb;
	}
	
	/*private static KeyboardWidget makeQwertyKeyboard(double x, double y, double width, double height) {
		GuiKeyboard kb = new GuiKeyboard(parent, x, y);
		double currentX = 0;
		double currentY = 0;
		
		double keySpacing = 5;
		double keyWidth = width/12-keySpacing;
		double keyHeight = height/15;
	
		currentX = addHorizontalRow(kb, KEY_F1, KEY_F10, 0, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth, keyHeight, KEY_F11);
		currentX += keyWidth + keySpacing;
		kb.addKey(currentX , currentY, keyWidth, keyHeight, KEY_F12);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/15-keySpacing;
		kb.addKey(0, currentY, keyWidth, keyHeight, KEY_GRAVE);
		currentX = keyWidth+keySpacing;
		currentX = addHorizontalRow(kb, KEY_1, KEY_EQUALS, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth*2+5, keyHeight, KEY_BACK);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_TAB);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, KEY_Q, KEY_RBRACKET, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth, keyHeight, KEY_BACKSLASH);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_CAPITAL);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, KEY_A, KEY_APOSTROPHE, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth*2+keySpacing, keyHeight, KEY_RETURN);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_LSHIFT);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, KEY_Z, KEY_SLASH, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth*3+keySpacing*2, keyHeight, KEY_RSHIFT);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/7-keySpacing;
		currentX = addHorizontalRow(kb, new int[] {KEY_LCONTROL,KEY_LMETA,KEY_LMENU,KEY_SPACE,KEY_RMENU,KEY_RMETA,KEY_RCONTROL}, 0, currentY, keyWidth, keyHeight, keySpacing);
		
		return kb;
	}*/

}
