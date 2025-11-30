package dto;

public class CategoryHighlightDTO {
    private final int year;
    private final String product;
    private final String headline;

    public CategoryHighlightDTO(int year, String product, String headline) {
        this.year = year;
        this.product = product;
        this.headline = headline;
    }

    public int getYear() { return year; }
    public String getProduct() { return product; }
    public String getHeadline() { return headline; }
}
