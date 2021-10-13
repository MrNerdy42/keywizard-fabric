package mrnerdy42.keywizard.util;

public enum KeyboardLayout {
	QWERTY("QWERTY"),
	NUMPAD("NUMPAD"),
	AUXILIARY("AUX");
	
	private final String displayName;
	
	private KeyboardLayout(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
