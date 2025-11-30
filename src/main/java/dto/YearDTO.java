package dto;


import java.util.List;


public class YearDTO {
	private final int year;
	private final List<MeasurementDTO> measurements;
	private final List<String> top10Aliases;
	private final List<String> top10Headlines;


	public YearDTO(int year, List<MeasurementDTO> measurements, List<String> top10Aliases, List<String> top10Headlines) {
		this.year = year;
		this.measurements = measurements;
		this.top10Aliases = top10Aliases;
		this.top10Headlines = top10Headlines;
	}


	public int getYear() { return year; }
	public List<MeasurementDTO> getMeasurements() { return measurements; }
	public List<String> getTop10Aliases() { return top10Aliases; }
	public List<String> getTop10Headlines() { return top10Headlines; }
}