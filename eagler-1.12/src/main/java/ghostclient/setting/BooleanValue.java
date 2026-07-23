package ghostclient.setting;

/**
 * Boolean on/off setting.
 */
public class BooleanValue extends Value<Boolean> {

    public BooleanValue(String name, String description, boolean value) {
        super(name, description, value);
    }

    public void toggle() {
        setValue(!getValue());
    }
}
