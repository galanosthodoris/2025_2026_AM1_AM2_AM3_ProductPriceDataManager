import controller.MyController;
import dto.YearDTO;

public class TestListYears {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        // Happy Day: load valid INI 
        System.out.println("Test 1 : HAPPY DAY");
        try {
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            java.util.List<YearDTO> years = controller.listYears();

            if (years != null && !years.isEmpty()) {
                System.out.println("[PASS] listYears() returned " + years.size() + " years.");
            } else {
                System.out.println("[FAIL] listYears() returned empty list.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Should NOT have thrown exception: " + e.getMessage());
        }

        // Rainy Day: no load performed
        
        System.out.println("Test 1 : RAINT DAY");
        
        try {
            MyController emptyController = new MyController();

            java.util.List<YearDTO> empty = emptyController.listYears();

            if (empty == null || empty.isEmpty()) {
                System.out.println("[PASS] listYears() correctly empty when no data loaded.");
            } else {
                System.out.println("[FAIL] listYears() should be empty when no data loaded.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected error in rainy-day test: " + e.getMessage());
        }
    }
}
