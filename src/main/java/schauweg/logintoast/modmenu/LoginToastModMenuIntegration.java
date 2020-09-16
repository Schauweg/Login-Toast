package schauweg.logintoast.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.TranslatableText;
import schauweg.logintoast.config.LoginToastConfig;
import schauweg.logintoast.config.LoginToastConfigManager;

public class LoginToastModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {

            LoginToastConfig config = LoginToastConfigManager.getConfig();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(new TranslatableText("logintoast.config.menu"));

            ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("logintoast.config.general"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("logintoast.config.option.showtoast"), config.showToast())
                    .setDefaultValue(true) // Recommended: Used when user click "Reset"
                    .setTooltip(new TranslatableText("logintoast.config.option.showtoast.description")) // Optional: Shown when the user hover over this option
                    .setSaveConsumer(newValue -> config.setShowToast(newValue)) // Recommended: Called when user save the config
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("logintoast.config.option.showchatmessage"), config.showChatMessage())
                    .setDefaultValue(false) // Recommended: Used when user click "Reset"
                    .setTooltip(new TranslatableText("logintoast.config.option.showchatmessage.description")) // Optional: Shown when the user hover over this option
                    .setSaveConsumer(newValue -> config.setShowchatMessage(newValue)) // Recommended: Called when user save the config
                    .build());

            general.addEntry(entryBuilder.startIntSlider(new TranslatableText("logintoast.config.option.toastduration"), config.getToastDuration(),1, 5)
                    .setDefaultValue(5) // Recommended: Used when user click "Reset"
                    .setTooltip(new TranslatableText("logintoast.config.option.toastduration.description")) // Optional: Shown when the user hover over this option
                    .setSaveConsumer(newValue -> config.setToastDuration(newValue)) // Recommended: Called when user save the config
                    .build());

            builder.setSavingRunnable(() -> {
                LoginToastConfigManager.save();
            });

            return builder.build();
        };
    }
}
