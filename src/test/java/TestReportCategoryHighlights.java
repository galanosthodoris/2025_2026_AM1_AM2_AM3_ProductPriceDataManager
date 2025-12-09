import controller.MyController;
import dto.CategoryHighlightDTO;
import java.util.List;

public class TestReportCategoryHighlights {

    public static void main(String[] args) {

        // Happy Day (Data Loaded + Valid Category)
        System.out.println("Test 1: Happy Day (Search for Category 'PreciousMetals') ");
        try {
            MyController controller = new MyController();

            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<CategoryHighlightDTO> results = controller.reportCategoryHighlights("PreciousMetals");

            if (results != null && !results.isEmpty()) {
                System.out.println("[PASS] Found " + results.size() + " highlights for category 'PreciousMetals'.");
                
                CategoryHighlightDTO sample = results.get(0);
                System.out.println("       Sample: [" + sample.getYear() + "] " + sample.getProduct() + " -> " + sample.getHeadline());
            } else {
                System.out.println("[FAIL] Returned empty list for 'PreciousMetals'. Check metadata mapping.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception during Happy Day: " + e.getMessage());
            e.printStackTrace();
        }

        // Rainy Day (Data Loaded + Non-existent Category)
        System.out.println("Test 2: Rainy Day (Search for 'Papoutsia')");
        try {
            MyController controller = new MyController();
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<CategoryHighlightDTO> results = controller.reportCategoryHighlights("Papoutsia");

            if (results != null && results.isEmpty()) {
                System.out.println("[PASS] Correctly returned empty list for non-existent category.");
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

            List<CategoryHighlightDTO> emptyResults = emptyController.reportCategoryHighlights("Energy");

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