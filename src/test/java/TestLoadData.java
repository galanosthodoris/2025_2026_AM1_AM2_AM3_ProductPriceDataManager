import java.util.stream.Collectors;

import controller.MyController;
import dto.ProductDTO;

public class TestLoadData {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        // Happy Day
        System.out.println("Test 1 : HAPPY DAY");
        try {
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            if (controller.listYears().size() > 0) {
                System.out.println("[PASS] Data loaded, years found: " + controller.listYears().size());
            } else {
                System.out.println("[FAIL] Data loaded but no years were found.");
            }

            boolean foundMeasurements = false;

            for (ProductDTO p : controller.listProducts().stream()
        .map(dto -> controller.getProductMeasurements(dto.getName()))
        .collect(Collectors.toList())) {

                if (p != null && p.getMeasurements().size() > 0) {
                    foundMeasurements = true;
                    break;
                }
            }

            if (foundMeasurements)
                System.out.println("[PASS] Some product has measurements.");
            else
                System.out.println("[FAIL] No measurements found.");

        } catch (Exception e) {
            System.out.println("[FAIL] Should have loaded sample data: " + e.getMessage());
        }

        // Rainy Day
        System.out.println("Test 2 : RAINY DAY");
        try {
            controller.initializeFromIni("src/test/resources/nonexistent.ini", "\t");
            System.out.println("[FAIL] Should NOT load fake data.");
        } catch (Exception e) {
            System.out.println("[PASS] Correctly failed on fake data.");
        }
    }
}
