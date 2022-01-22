package mrnerdy42.keywizard.gui;

import org.lwjgl.glfw.GLFW;

public class KeyboardWidgetBuilder {
	
	
	public static KeyboardWidget StandardKeyboard(KeyWizardScreen keyWizardScreen, float anchorX, float anchorY, float width, float height) {
		KeyboardWidget kb = new KeyboardWidget(keyWizardScreen, anchorX, anchorY);

		float currentX = 0;
		float currentY = 0;
		
		float keySpacing = 5;
		float nudge = 0;
		float keyWidth = width/12-keySpacing;
		float keyHeight = height/6-keySpacing; 
	
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_F1, GLFW.GLFW_KEY_F2, GLFW.GLFW_KEY_F3, GLFW.GLFW_KEY_F4, GLFW.GLFW_KEY_F5, GLFW.GLFW_KEY_F6, GLFW.GLFW_KEY_F7, GLFW.GLFW_KEY_F8, GLFW.GLFW_KEY_F9, GLFW.GLFW_KEY_F10, GLFW.GLFW_KEY_F11, GLFW.GLFW_KEY_F12}, 0, currentY, keyWidth, keyHeight, keySpacing);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/15-keySpacing;
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_GRAVE_ACCENT, GLFW.GLFW_KEY_1, GLFW.GLFW_KEY_2, GLFW.GLFW_KEY_3, GLFW.GLFW_KEY_4, GLFW.GLFW_KEY_5, GLFW.GLFW_KEY_6, GLFW.GLFW_KEY_7, GLFW.GLFW_KEY_8, GLFW.GLFW_KEY_9, GLFW.GLFW_KEY_0, GLFW.GLFW_KEY_MINUS, GLFW.GLFW_KEY_EQUAL}, 0, currentY, keyWidth, keyHeight, keySpacing);
		currentX = kb.addKey(currentX, currentY, (keyWidth*2+keySpacing)+nudge, keyHeight, keySpacing, GLFW.GLFW_KEY_BACKSPACE);
		
		currentY += keyHeight + keySpacing;
		currentX = kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, keySpacing, GLFW.GLFW_KEY_TAB);
		//currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_Q, GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_E, GLFW.GLFW_KEY_R, GLFW.GLFW_KEY_T, GLFW.GLFW_KEY_Y, GLFW.GLFW_KEY_U, GLFW.GLFW_KEY_I, GLFW.GLFW_KEY_O, GLFW.GLFW_KEY_P, GLFW.GLFW_KEY_LEFT_BRACKET, GLFW.GLFW_KEY_RIGHT_BRACKET}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentX = kb.addKey(currentX, currentY, keyWidth+nudge, keyHeight, keySpacing, GLFW.GLFW_KEY_BACKSLASH);
		
		currentY += keyHeight + keySpacing;
		currentX = kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, keySpacing, GLFW.GLFW_KEY_CAPS_LOCK);
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_F, GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J, GLFW.GLFW_KEY_K, GLFW.GLFW_KEY_L, GLFW.GLFW_KEY_SEMICOLON, GLFW.GLFW_KEY_APOSTROPHE}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX, currentY, (keyWidth*2+keySpacing)+nudge, keyHeight, keySpacing, GLFW.GLFW_KEY_ENTER);
		
		currentY += keyHeight + keySpacing;
		currentX = kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, keySpacing, GLFW.GLFW_KEY_LEFT_SHIFT);
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_Z, GLFW.GLFW_KEY_X, GLFW.GLFW_KEY_C, GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B, GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M, GLFW.GLFW_KEY_COMMA, GLFW.GLFW_KEY_PERIOD, GLFW.GLFW_KEY_SLASH}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentX = kb.addKey(currentX, currentY, (keyWidth*3+keySpacing*2)+nudge, keyHeight, keySpacing, GLFW.GLFW_KEY_RIGHT_SHIFT);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width / 7 - keySpacing;
		//currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_LEFT_CONTROL,GLFW.GLFW_KEY_LEFT_SUPER,GLFW.GLFW_KEY_LEFT_ALT}, 0, currentY, keyWidth, keyHeight, keySpacing);
		//currentX = kb.addKey(currentX, currentY, keyWidth-5, keyHeight, keySpacing, GLFW.GLFW_KEY_SPACE);
		//currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_RIGHT_ALT,GLFW.GLFW_KEY_RIGHT_SUPER,GLFW.GLFW_KEY_RIGHT_CONTROL}, currentX, currentY, keyWidth, keyHeight, keySpacing);
		currentX = addHorizontalRow(kb, new int[] {GLFW.GLFW_KEY_LEFT_CONTROL,GLFW.GLFW_KEY_LEFT_SUPER,GLFW.GLFW_KEY_LEFT_ALT, GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_RIGHT_ALT,GLFW.GLFW_KEY_RIGHT_SUPER,GLFW.GLFW_KEY_RIGHT_CONTROL}, 0, currentY, keyWidth, keyHeight, keySpacing);

		
		return kb;
	}
	
	/*private static KeyboardWidget makeQwertyKeyboard(int x, int y, int width, int height) {
		
	}*/
	
	/*
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
	 * /
	private static int addHorizontalRow(KeyboardWidget kb, int startCode, int endCode, int startX, int y, int width, int height, int spacing) {
		int currentX = startX;
		for(int i=startCode; i<=endCode; i++) {
			currentX = kb.addKey(currentX, y, width, height, spacing, i);
		}
		return currentX;//startX + (width * (endCode-startCode + 1) + spacing * ((endCode-startCode)+1));
	}
	*/
	
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
	private static float addHorizontalRow(KeyboardWidget kb, int[] keys, float startX, float y, float width, float height, float spacing) {
		float currentX = startX;
		for(int k:keys) {
			currentX = kb.addKey(currentX, y, width, height, spacing, k);
		}
		return currentX;//startX + (width * (keys.length) + spacing * (keys.length));
	}

}

