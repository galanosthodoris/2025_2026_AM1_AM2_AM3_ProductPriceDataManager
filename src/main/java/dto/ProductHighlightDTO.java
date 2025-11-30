package dto;

public class ProductHighlightDTO {
    private final int year;
    private final String headline;

    public ProductHighlightDTO(int year, String headline) {
        this.year = year;
        this.headline = headline;
    }

    public int getYear() { return year; }
    public String getHeadline() { return headline; }
}
