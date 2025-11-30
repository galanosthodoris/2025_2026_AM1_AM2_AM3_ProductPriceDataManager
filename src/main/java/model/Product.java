package model;
import java.util.ArrayList;
import java.util.List;

public class Product
{
    private String fullName;
    private String alias;
    private String category;
    private List<Measurement> measurements;

    public Product(String fullName, String alias, String category) {
    this.fullName = fullName;
    this.alias = alias;
    this.category = category;
    this.measurements = new ArrayList<>();
    }


    public String getName()
    {
        return fullName;
    }

    public String getAlias()
    {
        return alias;
    }

    public String getCategory()
    {
        return category;
    }

    public List<Measurement> getMeasurements()
    {
        return measurements;
    }

    public void addMeasurement(Measurement measurement)
    {
        this.measurements.add(measurement);
    }

}