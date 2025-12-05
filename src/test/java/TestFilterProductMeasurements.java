import controller.MyController;
import dto.ProductDTO;

public class TestFilterProductMeasurements {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        // Load INI that points to sample metadata + data
        controller.initializeFromIni("src/test/resources/test.ini", "\t");

        // --- HAPPY DAY ---
        System.out.println("---- HAPPY DAY ----");

        ProductDTO filtered = controller.filterProductMeasurements("Oil", 1960, 1961);

        if (filtered == null) {
            System.out.println("[FAIL] Expected product Oil, got null.");
        } else {
            if (filtered.getMeasurements().size() > 0) {
                System.out.println("[PASS] filterProductMeasurements() returned "
                                   + filtered.getMeasurements().size()
                                   + " measurements for Oil between 1960-1961.");
            } else {
                System.out.println("[FAIL] No measurements returned.");
            }
        }

        // --- RAINY DAY 1: product does not exist ---
        System.out.println("---- RAINY DAY (no such product) ----");

        ProductDTO badAlias = controller.filterProductMeasurements("NotARealProduct", 1900, 2000);

        if (badAlias == null) {
            System.out.println("[PASS] Correctly returned null for nonexistent product.");
        } else {
            System.out.println("[FAIL] Should have returned null for nonexistent product.");
        }

        // --- RAINY DAY 2: year range with no results ---
        System.out.println("---- RAINY DAY (empty range) ----");

        ProductDTO emptyRange = controller.filterProductMeasurements("Oil", 3000, 4000);

        if (emptyRange.getMeasurements().isEmpty()) {
            System.out.println("[PASS] No measurements found for future years.");
        } else {
            System.out.println("[FAIL] Expected zero measurements.");
        }
    }
}
