package mrnerdy42.keywizard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mrnerdy42.keywizard.handlers.ClientEventHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KeyWizard.MODID)
public class KeyWizard {
	
	public static final String MODID = "keywizard";
	
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
