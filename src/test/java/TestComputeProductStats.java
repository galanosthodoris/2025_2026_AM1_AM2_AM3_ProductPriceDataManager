import controller.MyController;
import dto.ProductStatsDTO;
import java.util.List;

public class TestComputeProductStats {

    public static void main(String[] args) {

        //Happy Day (Data Loaded + Check Logic)
        System.out.println("Test 1: Happy Day (Calculate Stats for all products)");
        try {
            MyController controller = new MyController();
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<ProductStatsDTO> stats = controller.computeProductStats();

            if (stats != null && !stats.isEmpty()) {
                System.out.println("[PASS] Successfully computed stats for " + stats.size() + " products.");

                ProductStatsDTO sample = null;
                for (ProductStatsDTO s : stats) {
                    if (s.getProduct().equals("Oil") || s.getProduct().equals("Gold")) {
                        sample = s;
                        break;
                    }
                }

                if (sample != null) {
                    System.out.println("       Checking logic for: " + sample.getProduct());
                    System.out.println("       Min: " + sample.getMin() + ", Max: " + sample.getMax() + ", Avg: " + sample.getAverage());

                    if (sample.getMin() <= sample.getMax()) {
                        System.out.println("[PASS] Logic check OK: Min <= Max");
                    } else {
                        System.out.println("[FAIL] Logic error: Min (" + sample.getMin() + ") is greater than Max (" + sample.getMax() + ")");
                    }

                    if (sample.getAverage() >= sample.getMin() && sample.getAverage() <= sample.getMax()) {
                        System.out.println("[PASS] Logic check OK: Average is within range.");
                    } else {
                        System.out.println("[FAIL] Logic error: Average is out of bounds.");
                    }
                } else {
                    System.out.println("[WARN] Could not find 'Oil' or 'Gold' to verify specific values.");
                }

            } else {
                System.out.println("[FAIL] Stats list is empty or null.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception during Happy Day: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();

        //Rainy Day (No Data Loaded)
        System.out.println("Test 2: Rainy Day (No Data Loaded)");
        try {
            MyController emptyController = new MyController();

            List<ProductStatsDTO> emptyStats = emptyController.computeProductStats();

            if (emptyStats != null && emptyStats.isEmpty()) {
                System.out.println("[PASS] Correctly returned empty list when no data loaded.");
            } else {
                System.out.println("[FAIL] Should be empty when no data loaded.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected error in empty controller test: " + e.getMessage());
        }
    }
}