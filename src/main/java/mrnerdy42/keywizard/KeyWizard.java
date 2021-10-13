package mrnerdy42.keywizard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(KeyWizard.MODID)
public class KeyWizard {
	
	public static final String MODID = "keywizard-fabric";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	
	public KeyWizard() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        
        ModLoadingContext.get().registerConfig(Type.CLIENT, KeyWizardConfig.SPEC, "keywizard-client.toml");
	}
	
    public void clientSetup(final FMLClientSetupEvent e) {
    	LOGGER.log(Level.DEBUG, MODID);
    	MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
