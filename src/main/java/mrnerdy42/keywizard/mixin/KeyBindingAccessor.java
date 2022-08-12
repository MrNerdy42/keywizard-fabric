package mrnerdy42.keywizard.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
	 @Accessor
	 InputUtil.KeyCode getKeyCode();
	
	 @Accessor("keyCategories")
	 static Set<String> getKeyCategories() {
		throw new AssertionError();
	 };
}