package schauweg.logintoast.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import schauweg.logintoast.Main;

import java.util.stream.Stream;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Shadow @Final private MinecraftServer server;

    @Inject( at = @At("TAIL"), method = "onPlayerConnect")
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci){
        Stream<ServerPlayerEntity> watchingPlayers = PlayerStream.all(server);

        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeUuid(player.getUuid());


        watchingPlayers.forEach( playerEntity -> {
            if (!playerEntity.getUuid().equals(player.getUuid()))
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(playerEntity, Main.PLAYER_LOGGED_IN, passedData);
        });

    }

}
