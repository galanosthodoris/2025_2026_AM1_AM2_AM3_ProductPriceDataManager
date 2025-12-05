package model;


public class Measurement
{
    private Product product;
    private Year year;
    private double value;

    public Measurement(Product product, Year year, double value) 
    {
        this.product = product;
        this.year = year;
        this.value = value;
    }

    public Product getProduct() 
    {
        return product;
    }

    public Year getYear() 
    {
        return year;
    }

    public double getValue() 
    {
        return value;
    }

}
