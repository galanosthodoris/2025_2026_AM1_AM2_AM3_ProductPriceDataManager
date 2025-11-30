package dto;

import java.util.List;

public class ProductDTO {
	private final String name;
	private final List<MeasurementDTO> measurements;

	public ProductDTO(String name, List<MeasurementDTO> measurements) {
		this.name = name;
		this.measurements = measurements;
	}

	public String getName() {
		return name;
	}

	public List<MeasurementDTO> getMeasurements() {
		return measurements;
	}
}