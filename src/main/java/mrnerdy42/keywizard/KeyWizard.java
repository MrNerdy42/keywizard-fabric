package mrnerdy42.keywizard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;

public class KeyWizard implements ClientModInitializer {
	
	public static final String MODID = "keywizard-fabric";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Override
	public void onInitializeClient() {
		LOGGER.log(Level.DEBUG, MODID);
		
	}
}
