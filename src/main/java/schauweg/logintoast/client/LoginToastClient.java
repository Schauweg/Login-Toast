package schauweg.logintoast.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import schauweg.logintoast.Main;
import schauweg.logintoast.config.LoginToastConfigManager;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class LoginToastClient implements ClientModInitializer {

    public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(Main.PLAYER_LOGGED_IN,
                (packetContext, attachedData) -> {

                    if (LoginToastConfigManager.getConfig().showToast()){

                        UUID uuid = attachedData.readUuid();

                        packetContext.getTaskQueue().execute(() -> {

                            ClientPlayNetworkHandler clientPlayNetworkHandler = MinecraftClient.getInstance().player.networkHandler;
                            PlayerListEntry entry = clientPlayNetworkHandler.getPlayerListEntry(uuid);

                            if (entry != null)
                                MinecraftClient.getInstance().getToastManager().add(new PlayerToast(entry));
                        });
                    }
                });
            LoginToastConfigManager.initializeConfig();
    }
}
