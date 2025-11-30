package dto;

public class ProductStatsDTO {
    private final String product;
    private final double min;
    private final double average;
    private final double max;
    private final double lastValue;

    public ProductStatsDTO(String product, double min, double average, double max, double lastValue) {
        this.product = product;
        this.min = min;
        this.average = average;
        this.max = max;
        this.lastValue = lastValue;
    }

    public String getProduct() { return product; }
    public double getMin() { return min; }
    public double getAverage() { return average; }
    public double getMax() { return max; }
    public double getLastValue() { return lastValue; }
}
