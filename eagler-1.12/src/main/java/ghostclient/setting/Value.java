package ghostclient.setting;

import java.util.function.Consumer;

/**
 * Base setting for modules.
 */
public class Value<T> {

    private final String name;
    private final String description;
    private T value;
    private Consumer<T> onChange;

    public Value(String name, String description, T value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (onChange != null) {
            onChange.accept(value);
        }
    }

    public void onChange(Consumer<T> onChange) {
        this.onChange = onChange;
    }
}
