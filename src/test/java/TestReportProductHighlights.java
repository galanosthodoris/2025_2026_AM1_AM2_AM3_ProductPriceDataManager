import controller.MyController;
import dto.ProductHighlightDTO;
import java.util.List;

public class TestReportProductHighlights {

    public static void main(String[] args) {

        // Happy Day (Data Loaded + Valid Product)
        System.out.println("Test 1: Happy Day (Search for 'Oil') ");
        try {
            MyController controller = new MyController();

            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<ProductHighlightDTO> results = controller.reportProductHighlights("Oil");

            if (results != null && !results.isEmpty()) {
                System.out.println("[PASS] Found " + results.size() + " highlights for Oil.");
                
                System.out.println("       Sample: " + results.get(0).getYear() + " - " + results.get(0).getHeadline());
            } else {
                System.out.println("[FAIL] Returned empty list for 'Oil'.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception during Happy Day: " + e.getMessage());
            e.printStackTrace();
        }


        // Rainy Day (Data Loaded + Non-existent Product)
        System.out.println("Test 2: Rainy Day (Search for 'Unicorn')");
        try {
            MyController controller = new MyController();
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<ProductHighlightDTO> results = controller.reportProductHighlights("Unicorn");

            if (results != null && results.isEmpty()) {
                System.out.println("[PASS] Correctly returned empty list for non-existent product.");
            } else {
                System.out.println("[FAIL] Expected empty list, but got size: " + (results == null ? "null" : results.size()));
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception during Rainy Day 1: " + e.getMessage());
        }


        // Rainy Day (No Data Loaded)
        System.out.println("Test 3: Rainy Day (No Data Loaded)");
        try {
            MyController emptyController = new MyController(); 

            List<ProductHighlightDTO> emptyResults = emptyController.reportProductHighlights("Oil");

            if (emptyResults != null && emptyResults.isEmpty()) {
                System.out.println("[PASS] Correctly returned empty list when controller is empty.");
            } else {
                System.out.println("[FAIL] Should be empty when no data loaded.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected error in empty controller test: " + e.getMessage());
        }
    }
}