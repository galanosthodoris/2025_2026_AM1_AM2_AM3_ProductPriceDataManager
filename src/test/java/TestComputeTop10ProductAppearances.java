import controller.MyController;
import dto.Top10AppearanceDTO;
import java.util.List;

public class TestComputeTop10ProductAppearances {

    public static void main(String[] args) {

        //Happy Day (Data Loaded + Check Sorting)
        System.out.println("Test 1: Happy Day (Count Appearances & Verify Sorting)");
        try {
            MyController controller = new MyController();
            
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<Top10AppearanceDTO> results = controller.computeTop10ProductAppearances();

            if (results != null && !results.isEmpty()) {
                System.out.println("[PASS] Successfully computed appearances for " + results.size() + " products.");

                boolean isSortedCorrectly = true;
                for (int i = 0; i < results.size() - 1; i++) {
                    Top10AppearanceDTO current = results.get(i);
                    Top10AppearanceDTO next = results.get(i + 1);

                    if (current.getCount() < next.getCount()) {
                        isSortedCorrectly = false;
                        System.out.println("[FAIL] Sorting Error! " + current.getName() + " (" + current.getCount() + ") came before " + next.getName() + " (" + next.getCount() + ")");
                        break;
                    }
                }

                if (isSortedCorrectly) {
                    System.out.println("[PASS] List is correctly sorted by count (Descending).");
                }

                System.out.println("       Top 3 Products:");
                for (int i = 0; i < Math.min(3, results.size()); i++) {
                    System.out.println("       " + (i + 1) + ". " + results.get(i).getName() + " - Appearances: " + results.get(i).getCount());
                }

            } else {
                System.out.println("[FAIL] List is empty. Something went wrong with loading or counting.");
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

            List<Top10AppearanceDTO> emptyResults = emptyController.computeTop10ProductAppearances();

            if (emptyResults != null && emptyResults.isEmpty()) {
                System.out.println("[PASS] Correctly returned empty list when no data loaded.");
            } else {
                System.out.println("[FAIL] Should be empty when no data loaded.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected error in empty controller test: " + e.getMessage());
        }
    }
}