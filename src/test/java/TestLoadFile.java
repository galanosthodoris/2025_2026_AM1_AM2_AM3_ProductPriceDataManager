import controller.MyController;
import dto.YearDTO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestLoadFile {

    public static void main(String[] args) {

        // Τυπώνουμε πού "ψάχνει" η Java τα αρχεία για να είμαστε σίγουροι
        System.out.println("DEBUG: Working Directory = " + System.getProperty("user.dir"));

        // ==========================================
        // SCENARIO 1: Happy Day (Reload Valid Data)
        // ==========================================
        System.out.println("--- Test 1: Happy Day (Load File Manually) ---");
        try {
            MyController controller = new MyController();
            
            // ΛΥΣΗ: Βάζουμε ΣΚΕΤΑ τα ονόματα, υποθέτοντας ότι είναι στο root του project
            String iniPath = "test.ini"; 
            String dataPath = "data.tsv"; 

            // Έλεγχος αν το αρχείο υπάρχει ΠΡΙΝ το δώσουμε στον Controller (για να δούμε αν φταιει το path)
            File f = new File(iniPath);
            if (!f.exists()) {
                System.out.println("[ERROR] Το αρχείο " + iniPath + " ΔΕΝ βρέθηκε στον φάκελο του project!");
                System.out.println("        Παρακαλώ σύρε το test.ini μέσα στον φάκελο του project στο Eclipse.");
                return; // Σταματάμε εδώ
            }

            // Φόρτωση αρχική
            controller.initializeFromIni(iniPath, "\t");
            System.out.println("Initial load complete.");

            // Φόρτωση ξανά του data file
            controller.loadFile(dataPath, "\t");

            List<YearDTO> years = controller.listYears();
            if (years != null && !years.isEmpty()) {
                System.out.println("[PASS] Successfully re-loaded " + years.size() + " years via loadFile().");
            } else {
                System.out.println("[FAIL] loadFile() resulted in empty dataset.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Happy Day Failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();

        // ==========================================
        // SCENARIO 2: Rainy Day (File Not Found)
        // ==========================================
        System.out.println("--- Test 2: Rainy Day (Load Missing File) ---");
        try {
            MyController controller = new MyController();
            // Φόρτωση αρχική (πρέπει να πετύχει για να τρέξει το τεστ)
            try {
                controller.initializeFromIni("test.ini", "\t"); 
            } catch (Exception e) {
                System.out.println("[WARN] Skipping Setup for Rainy Day because init failed.");
            }
            
            // Προσπάθεια φόρτωσης ανύπαρκτου αρχείου
            controller.loadFile("ghost_data.tsv", "\t");

            System.out.println("[FAIL] Method did not throw IOException.");

        } catch (IOException e) {
            System.out.println("[PASS] Correctly threw IOException: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("[PASS] Caught RuntimeException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected Exception: " + e.getClass().getSimpleName());
        }
    }
}