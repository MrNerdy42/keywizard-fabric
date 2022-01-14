package mrnerdy42.keywizard.gui;

import org.lwjgl.glfw.GLFW;

public class KeyboardWidgetBuilder {
	
	
	public static KeyboardWidget StandardKeyboard(KeyWizardScreen keyWizardScreen, int anchorX, int anchorY, int width, int height) {
		KeyboardWidget kb = new KeyboardWidget(keyWizardScreen, anchorX, anchorY);

		int currentX = 0;
		int currentY = 0;
		
		int keySpacing = 5;
		int keyWidth = width/12-keySpacing;
		int keyHeight = height/15;
	
		currentX = addHorizontalRow(kb, GLFW.GLFW_KEY_F1, GLFW.GLFW_KEY_F10, 0, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth, keyHeight, GLFW.GLFW_KEY_F11);
		currentX += keyWidth + keySpacing;
		kb.addKey(currentX , currentY, keyWidth, keyHeight, GLFW.GLFW_KEY_F12);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/15-keySpacing;
		kb.addKey(0, currentY, keyWidth, keyHeight, GLFW.GLFW_KEY_GRAVE_ACCENT);
		currentX = keyWidth+keySpacing;
		currentX = addHorizontalRow(kb, GLFW.GLFW_KEY_1, GLFW.GLFW_KEY_EQUAL, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth*2+5, keyHeight, GLFW.GLFW_KEY_BACKSPACE);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, GLFW.GLFW_KEY_TAB);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, GLFW.GLFW_KEY_Q, GLFW.GLFW_KEY_RIGHT_BRACKET, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth, keyHeight, GLFW.GLFW_KEY_BACKSLASH);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, GLFW.GLFW_KEY_CAPS_LOCK);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_APOSTROPHE, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth*2+keySpacing, keyHeight, GLFW.GLFW_KEY_ENTER);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, GLFW.GLFW_KEY_LEFT_SHIFT);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, GLFW.GLFW_KEY_Z, GLFW.GLFW_KEY_SLASH, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, keyWidth*3+keySpacing*2, keyHeight, GLFW.GLFW_KEY_RIGHT_SHIFT);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/7-keySpacing;
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_LEFT_CONTROL,GLFW.GLFW_KEY_LEFT_SUPER,GLFW.GLFW_KEY_LEFT_ALT,GLFW.GLFW_KEY_SPACE,GLFW.GLFW_KEY_RIGHT_ALT,GLFW.GLFW_KEY_RIGHT_SUPER,GLFW.GLFW_KEY_RIGHT_CONTROL}, 0, currentY, keyWidth, keyHeight, keySpacing);
		
		return kb;
	}
	
	/*private static KeyboardWidget makeQwertyKeyboard(int x, int y, int width, int height) {
		
	}*/
	
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
	private static int addHorizontalRow(KeyboardWidget kb, int startCode, int endCode, int startX, int y, int width, int height, int spacing) {
		int currentX = startX;
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
	private static int addHorizontalRow(KeyboardWidget kb, int[] keys, int startX, int y, int width, int height, int spacing) {
		int currentX = startX;
		for(int k:keys) {
			kb.addKey(currentX, y, width, height, k);
			currentX += width + spacing;
		}
		return startX + (width * (keys.length) + spacing * (keys.length));
	}

}

