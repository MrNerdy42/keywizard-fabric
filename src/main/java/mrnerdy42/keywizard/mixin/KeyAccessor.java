package mrnerdy42.keywizard.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.util.InputUtil;

@Mixin(InputUtil.Key.class)
public interface KeyAccessor {
	 @Accessor
	 InputUtil.Type getType();
}