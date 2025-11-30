package model;
import java.util.List;
import java.util.ArrayList;

public class Year 
{
    private int year;
    private List<Measurement> measurements;  
    private List<String> top10Aliases;
    private List<String> headlines;
    
    public Year(int year) 
    {
        this.year = year;
        this.measurements = new ArrayList<>();
        this.top10Aliases = new ArrayList<>();
        this.headlines = new ArrayList<>();
    }

    public int getYear() 
    {
        return year;
    }

    public List<Measurement> getMeasurements() 
    {
        return measurements;
    }

    public List<String> getTop10Aliases() 
    {
        return top10Aliases;
    }

    public List<String> getHeadlines() 
    {
        return headlines;
    }

    public void addMeasurement(Measurement measurement) 
    {
        this.measurements.add(measurement);
    }

}