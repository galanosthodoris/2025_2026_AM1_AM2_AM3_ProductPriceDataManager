package dto;

public class Top10AppearanceDTO {
    private final String name; // works for both product alias and category
    private final int count;

    public Top10AppearanceDTO(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() { return name; }
    public int getCount() { return count; }
}
