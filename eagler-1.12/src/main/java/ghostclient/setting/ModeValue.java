package ghostclient.setting;

import java.util.Arrays;
import java.util.List;

/**
 * Multi-option string setting.
 */
public class ModeValue extends Value<String> {

    private final List<String> modes;

    public ModeValue(String name, String description, String value, String... modes) {
        super(name, description, value);
        this.modes = Arrays.asList(modes);
        if (!this.modes.contains(value)) {
            setValue(this.modes.get(0));
        }
    }

    public List<String> getModes() {
        return modes;
    }

    public void cycle() {
        int index = modes.indexOf(getValue());
        setValue(modes.get((index + 1) % modes.size()));
    }
}
