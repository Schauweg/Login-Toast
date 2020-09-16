package schauweg.logintoast.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import schauweg.logintoast.config.LoginToastConfigManager;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private List<ChatHudLine<Text>> messages;

    @Shadow protected abstract void removeMessage(int messageId);

    @Inject(at = @At("TAIL"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V")
    public void onAddMessage(Text text, int messageId, int timestamp, boolean bl, CallbackInfo ci){
        if (!LoginToastConfigManager.getConfig().showChatMessage() && text instanceof TranslatableText) {
            TranslatableText transText = (TranslatableText) text;
            if (transText.getKey().equals("multiplayer.player.joined")) {
                removeMessage(0);
            }
        }
    }

}
