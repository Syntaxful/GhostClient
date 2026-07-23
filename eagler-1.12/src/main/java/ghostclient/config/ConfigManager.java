package ghostclient.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ghostclient.GhostClient;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.ModeValue;
import ghostclient.setting.NumberValue;
import ghostclient.setting.Value;
import net.lax1dude.eaglercraft.EagRuntime;

/**
 * Saves and loads GhostClient module states, keybinds, and settings via JSON.
 */
public class ConfigManager {

    private static final String CONFIG_KEY = "ghostclient_config";

    public static void save() {
        try {
            JSONObject root = new JSONObject();
            JSONArray modules = new JSONArray();
            for (Module module : GhostClient.MODULES.getAll()) {
                JSONObject mod = new JSONObject();
                mod.put("name", module.getName());
                mod.put("enabled", module.isEnabled());
                mod.put("keybind", module.getKeybind());
                JSONObject settings = new JSONObject();
                for (Value<?> setting : module.getSettings()) {
                    settings.put(setting.getName(), String.valueOf(setting.getValue()));
                }
                mod.put("settings", settings);
                modules.put(mod);
            }
            root.put("modules", modules);
            root.put("version", GhostClient.VERSION);

            byte[] data = root.toString().getBytes(StandardCharsets.UTF_8);
            EagRuntime.setStorage(CONFIG_KEY, data);
        } catch (Throwable t) {
            System.err.println("[GhostClient] Failed to save config: " + t.getMessage());
        }
    }

    public static void load() {
        try {
            byte[] data = EagRuntime.getStorage(CONFIG_KEY);
            if (data == null) {
                System.out.println("[GhostClient] No config found, using defaults.");
                return;
            }
            String json = new String(data, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(json);
            JSONArray modules = root.getJSONArray("modules");
            for (int i = 0; i < modules.length(); i++) {
                JSONObject mod = modules.getJSONObject(i);
                String name = mod.getString("name");
                Module module = GhostClient.MODULES.getByName(name);
                if (module == null) continue;

                module.setKeybind(mod.optInt("keybind", 0));
                if (mod.has("enabled")) {
                    module.setEnabled(mod.getBoolean("enabled"));
                }

                JSONObject settings = mod.optJSONObject("settings");
                if (settings != null) {
                    for (Value<?> value : module.getSettings()) {
                        String key = value.getName();
                        if (!settings.has(key)) continue;
                        String raw = settings.getString(key);
                        applySetting(value, raw);
                    }
                }
            }
            System.out.println("[GhostClient] Config loaded.");
        } catch (Throwable t) {
            System.err.println("[GhostClient] Failed to load config: " + t.getMessage());
        }
    }

    private static void applySetting(Value<?> value, String raw) {
        try {
            if (value instanceof BooleanValue) {
                ((BooleanValue) value).setValue(Boolean.parseBoolean(raw));
            } else if (value instanceof NumberValue) {
                ((NumberValue) value).setValue(Double.parseDouble(raw));
            } else if (value instanceof ModeValue) {
                ((ModeValue) value).setValue(raw);
            }
        } catch (Throwable t) {
            System.err.println("[GhostClient] Failed to apply setting " + value.getName() + ": " + t.getMessage());
        }
    }
}
