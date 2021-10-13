package mrnerdy42.keywizard.util;

import static org.lwjgl.input.Keyboard.*;

import mrnerdy42.keywizard.gui.GuiKeyWizard;
import mrnerdy42.keywizard.gui.GuiKeyboard;

public class KeyboardFactory {
	public static GuiKeyboard makeKeyboard(KeyboardLayout layout, GuiKeyWizard parent, double x, double y, double width, double height) {
		switch(layout) {
		case QWERTY:
			return makeQwertyKeyboard(parent, x, y, width, height);
		case NUMPAD:
			return makeNumpad(parent, x, y, width, height);
		case AUXILIARY:
			return makeAuxiliary(parent, x, y, width, height);
		default:
			return null;
		}
	}
	
	private static GuiKeyboard makeQwertyKeyboard(GuiKeyWizard parent, double x, double y, double width, double height) {
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
	}
	
	private static GuiKeyboard makeNumpad(GuiKeyWizard parent, double x, double y, double width, double height) {
		GuiKeyboard kb = new GuiKeyboard(parent, x, y);
        double currentX = 0;
		double currentY = 0;
		
		double keySpacing = 5;
		double keyWidth = width/4-keySpacing;
		double keyHeight = height/14;
		
		currentX = addHorizontalRow(kb, new int[] {KEY_DIVIDE, KEY_MULTIPLY, KEY_SUBTRACT}, 0, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth / 3, keyHeight*2 + keySpacing, KEY_ADD);
		currentX=0;
		currentY += keyHeight + keySpacing;
		addHorizontalRow(kb, KEY_NUMPAD7, KEY_NUMPAD9, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentY += keyHeight + keySpacing;
		currentX = addHorizontalRow(kb, KEY_NUMPAD4, KEY_NUMPAD6, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth / 3, keyHeight*3 + keySpacing*2, KEY_NUMPADENTER);
		currentX = 0;
		currentY += keyHeight + keySpacing;
		addHorizontalRow(kb, KEY_NUMPAD1, KEY_NUMPAD3, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_NUMPAD0);
		currentX += keyWidth*2+keySpacing*2;
		kb.addKey(currentX, currentY, keyWidth, keyHeight, KEY_DECIMAL);
		
		return kb;
	}
	
	private static GuiKeyboard makeAuxiliary(GuiKeyWizard parent, double x, double y, double width, double height) {
		GuiKeyboard kb = new GuiKeyboard(parent, x, y);
        double currentX = 0;
		double currentY = 0;
		
		double keySpacing = 5;
		double keyWidth = width/5-keySpacing;
		double keyHeight = height/14;
		
		currentX = addHorizontalRow(kb, new int[] {KEY_SYSRQ, KEY_SCROLL, KEY_PAUSE, KEY_F13, KEY_F14}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentX = 0;
		currentY += keyHeight + keySpacing;
		currentX = addHorizontalRow(kb, new int[] {KEY_INSERT, KEY_HOME, KEY_PRIOR, KEY_F15, KEY_F16}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentX = 0;
		currentY += keyHeight + keySpacing;
		currentX = addHorizontalRow(kb, new int[] {KEY_DELETE, KEY_END, KEY_NEXT, KEY_F17, KEY_F18}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentX = 0;
		currentY += keyHeight + keySpacing;
		currentX = addHorizontalRow(kb, new int[] {KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN, KEY_F19}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		return kb;
	}
	
	/**
	 * Adds a uniformly spaced row to the keyboard it is passed.
	 * @param kb
	 * @param startCode
	 * @param endCode
	 * @param startX
	 * @param y
	 * @param width
	 * @param height
	 * @param spacing
	 * @return x position of left edge of the last key added
	 */
	private static double addHorizontalRow(GuiKeyboard kb, int startCode, int endCode, double startX, double y, double width, double height, double spacing) {
		double currentX = startX;
		for(int i=startCode; i<=endCode; i++) {
			kb.addKey(currentX, y, width, height, i);
			currentX += width + spacing;
		}
		return startX + (width * (endCode-startCode + 1) + spacing * ((endCode-startCode)+1));
	}
	
	/**
	 * Adds a uniformly spaced row to the keyboard it is passed.
	 * @param kb
	 * @param keys
	 * @param startX
	 * @param y
	 * @param width
	 * @param height
	 * @param spacing
	 * @return x position of left edge of the last key added
	 */
	private static double addHorizontalRow(GuiKeyboard kb, int[] keys, double startX, double y, double width, double height, double spacing) {
		double currentX = startX;
		for(int k:keys) {
			kb.addKey(currentX, y, width, height, k);
			currentX += width + spacing;
		}
		return startX + (width * (keys.length) + spacing * (keys.length));
	}

}
