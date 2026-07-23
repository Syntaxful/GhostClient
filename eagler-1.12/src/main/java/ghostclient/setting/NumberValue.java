package ghostclient.setting;

/**
 * Numeric setting with min/max/step.
 */
public class NumberValue extends Value<Double> {

    private final double min;
    private final double max;
    private final double step;

    public NumberValue(String name, String description, double value, double min, double max, double step) {
        super(name, description, clamp(value, min, max));
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }

    @Override
    public void setValue(Double value) {
        super.setValue(clamp(value, min, max));
    }

    private static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public int getInt() {
        return (int) (double) getValue();
    }

    public float getFloat() {
        return (float) (double) getValue();
    }
}
