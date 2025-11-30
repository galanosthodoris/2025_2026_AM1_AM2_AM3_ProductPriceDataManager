package dto;


public class MeasurementDTO {
	private final int year;
	private final String productName;
	private final double value;


	public MeasurementDTO(int year, String productName, double value) {
		this.year = year;
		this.productName = productName;
		this.value = value;
	}


	public int getYear() { return year; }
	public String getProductName() { return productName; }
	public double getValue() { return value; }
}