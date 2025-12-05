import controller.MyController;
import dto.Top10AppearanceDTO;
import java.util.List;

public class TestComputeTop10CategoryAppearances {

    public static void main(String[] args) {

        // ==========================================
        // SCENARIO 1: Happy Day (Data Loaded + Check Logic & Sorting)
        // ==========================================
        System.out.println("--- Test 1: Happy Day (Aggregating Categories & Sorting) ---");
        try {
            MyController controller = new MyController();
            // Βεβαιώσου για το σωστό path
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            List<Top10AppearanceDTO> results = controller.computeTop10CategoryAppearances();

            if (results != null && !results.isEmpty()) {
                System.out.println("[PASS] Successfully computed appearances for " + results.size() + " categories.");

                // Έλεγχος Ταξινόμησης:
                // 1. Φθίνουσα σειρά πλήθους (Count Descending)
                // 2. Αύξουσα αλφαβητική σειρά Ονόματος (Name Ascending) σε περίπτωση ισοβαθμίας
                boolean isSortedCorrectly = true;
                
                for (int i = 0; i < results.size() - 1; i++) {
                    Top10AppearanceDTO current = results.get(i);
                    Top10AppearanceDTO next = results.get(i + 1);

                    // Κανόνας 1: Το επόμενο δεν πρέπει να έχει μεγαλύτερο count
                    if (current.getCount() < next.getCount()) {
                        isSortedCorrectly = false;
                        System.out.println("[FAIL] Sorting Count Error! " + current.getName() + " (" + current.getCount() + ") came before " + next.getName() + " (" + next.getCount() + ")");
                        break;
                    }
                    
                    // Κανόνας 2: Αν έχουν ίδιο count, πρέπει να είναι αλφαβητικά
                    if (current.getCount() == next.getCount()) {
                        if (current.getName().compareTo(next.getName()) > 0) {
                            isSortedCorrectly = false;
                            System.out.println("[FAIL] Sorting Name Error! " + current.getName() + " came before " + next.getName() + " (should be alphabetical)");
                            break;
                        }
                    }
                }

                if (isSortedCorrectly) {
                    System.out.println("[PASS] List is correctly sorted (Count DESC, Name ASC).");
                }

                // Εκτύπωση των Top 3 Κατηγοριών
                System.out.println("       Top 3 Categories:");
                for (int i = 0; i < Math.min(3, results.size()); i++) {
                    System.out.println("       " + (i + 1) + ". " + results.get(i).getName() + " - Total Appearances: " + results.get(i).getCount());
                }

            } else {
                System.out.println("[FAIL] Category list is empty. Check metadata mappings.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception during Happy Day: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();

        // ==========================================
        // SCENARIO 2: Rainy Day (No Data Loaded)
        // ==========================================
        System.out.println("--- Test 2: Rainy Day (No Data Loaded) ---");
        try {
            MyController emptyController = new MyController();

            List<Top10AppearanceDTO> emptyResults = emptyController.computeTop10CategoryAppearances();

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