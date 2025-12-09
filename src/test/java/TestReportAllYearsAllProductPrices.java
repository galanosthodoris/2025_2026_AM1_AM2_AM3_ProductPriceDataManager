import controller.MyController;
import dto.YearDTO;
import dto.MeasurementDTO;
import java.util.List;

public class TestReportAllYearsAllProductPrices {

    public static void main(String[] args) {

        // Happy Day (Data Loaded + Verify Full Dataset)
        System.out.println("Test 1: Happy Day (Retrieve All Years & Data)");
        try {
            MyController controller = new MyController();
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<YearDTO> allYears = controller.reportAllYearsAllProductPrices();

            if (allYears != null && !allYears.isEmpty()) {
                System.out.println("[PASS] Successfully retrieved data for " + allYears.size() + " years.");

                YearDTO firstYear = allYears.get(0);
                System.out.println("       Sample Year: " + firstYear.getYear());
                
                List<MeasurementDTO> measurements = firstYear.getMeasurements();
                if (measurements != null && !measurements.isEmpty()) {
                    System.out.println("[PASS] Year " + firstYear.getYear() + " contains " + measurements.size() + " measurements.");
                } else {
                    System.out.println("[FAIL] Year " + firstYear.getYear() + " has NO measurements (empty list).");
                }

                if (firstYear.getTop10Headlines() != null && !firstYear.getTop10Headlines().isEmpty()) {
                    System.out.println("[PASS] Year " + firstYear.getYear() + " contains headlines.");
                } else {
                    System.out.println("[WARN] Year " + firstYear.getYear() + " has NO headlines. (Might be expected for some years)");
                }

            } else {
                System.out.println("[FAIL] Returned list is empty or null.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception during Happy Day: " + e.getMessage());
            e.printStackTrace();
        }

        // Rainy Day (No Data Loaded)
        System.out.println("Test 2: Rainy Day (No Data Loaded)");
        try {
            MyController emptyController = new MyController();

            List<YearDTO> emptyList = emptyController.reportAllYearsAllProductPrices();

            if (emptyList != null && emptyList.isEmpty()) {
                System.out.println("[PASS] Correctly returned empty list when no data loaded.");
            } else {
                System.out.println("[FAIL] Should be empty when no data loaded.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected error in empty controller test: " + e.getMessage());
        }
    }
}