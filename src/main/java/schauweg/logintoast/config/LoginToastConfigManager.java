package schauweg.logintoast.config;

import net.fabricmc.loader.api.FabricLoader;
import schauweg.logintoast.Main;
import schauweg.logintoast.client.LoginToastClient;

import java.io.*;

public class LoginToastConfigManager {

    private static File file;
    private static LoginToastConfig config;

    private static void prepareConfigFile() {
        if (file != null) {
            return;
        }
        file = FabricLoader.getInstance().getConfigDir().resolve(Main.MOD_ID+".json").toFile();
    }

    public static LoginToastConfig initializeConfig() {
        if (config != null) {
            return config;
        }

        config = new LoginToastConfig();
        load();

        return config;
    }

    private static void load() {
        prepareConfigFile();

        try {
            if (!file.exists()) {
                save();
            }
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                LoginToastConfig parsed = LoginToastClient.GSON.fromJson(br, LoginToastConfig.class);
                if (parsed != null) {
                    config = parsed;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load Login Toast configuration file; reverting to defaults");
            e.printStackTrace();
        }
    }

    public static void save() {
        prepareConfigFile();

        String jsonString = LoginToastClient.GSON.toJson(config);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            System.err.println("Couldn't save Login Toast configuration file");
            e.printStackTrace();
        }
    }

    public static LoginToastConfig getConfig() {
        if (config == null) {
            config = new LoginToastConfig();
        }
        return config;
    }

}
