package schauweg.logintoast.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import schauweg.logintoast.config.LoginToastConfigManager;

public class PlayerToast extends DrawableHelper implements Toast {

    private PlayerListEntry entry;

    public PlayerToast(PlayerListEntry entry){
        this.entry = entry;
    }

    @Override
    public Visibility draw(MatrixStack matrixStack, ToastManager manager, long startTime) {

        manager.getGame().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        manager.drawTexture(matrixStack, 0, 0, 0, 0, this.getWidth(), this.getHeight());

        PlayerEntity player = MinecraftClient.getInstance().world.getPlayerByUuid(entry.getProfile().getId());

        int offset = 8;

        manager.getGame().textRenderer.draw(matrixStack, entry.getProfile().getName(), 20+offset,7,16776960);
        manager.getGame().textRenderer.draw(matrixStack, new TranslatableText("logintoast.joined.game"), 20+offset,17,16776960);

        manager.getGame().getTextureManager().bindTexture(entry.getSkinTexture());

        this.drawTexture(matrixStack, offset, 8, 16, 16, 8.0F, 8.0F, 8, 8, 64, 64);

        if (player != null && player.isPartVisible(PlayerModelPart.HAT)) {
            this.drawTexture(matrixStack, offset, 8, 16, 16, 40.0F, 8.0F, 8, 8, 64, 64);
        }

        long time = LoginToastConfigManager.getConfig().getToastDuration() * 1000L;

        return startTime >= time ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }
}
