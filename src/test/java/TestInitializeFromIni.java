import controller.MyController;
import java.io.IOException;

public class TestInitializeFromIni {

    public static void main(String[] args) {

        // ==========================================
        // SCENARIO 1: Happy Day (Valid INI File)
        // ==========================================
        System.out.println("--- Test 1: Happy Day (Load Valid INI) ---");
        try {
            MyController controller = new MyController();
            
            // ΠΡΟΣΟΧΗ: Εδώ πρέπει να βάλεις το path για ένα πραγματικό αρχείο INI που δουλεύει
            // Αν το test.ini σου είναι στον φάκελο του project, ίσως χρειαστεί απλά "test.ini"
            String validIniPath = "src/test/resources/test.ini"; 
            
            int yearsLoaded = controller.initializeFromIni(validIniPath, "\t");

            // Έλεγχος: Πρέπει να έχει φορτώσει τουλάχιστον 1 έτος
            if (yearsLoaded > 0) {
                System.out.println("[PASS] Successfully loaded " + yearsLoaded + " years from INI configuration.");
            } else {
                System.out.println("[FAIL] Method ran but returned 0 years. Check if data files pointed by INI are empty or paths are wrong.");
            }

        } catch (IOException e) {
            System.out.println("[FAIL] IOException during Happy Day (File not found?): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected Exception: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();

        // ==========================================
        // SCENARIO 2: Rainy Day (Missing INI File)
        // ==========================================
        System.out.println("--- Test 2: Rainy Day (Load Non-Existent INI) ---");
        try {
            MyController controller = new MyController();
            
            // Δίνουμε ένα αρχείο που σίγουρα δεν υπάρχει
            String invalidPath = "notexist.ini";
            
            controller.initializeFromIni(invalidPath, "\t");

            // Αν φτάσει εδώ, σημαίνει ότι ΔΕΝ πέταξε εξαίρεση -> ΛΑΘΟΣ
            System.out.println("[FAIL] Method should have thrown IOException for missing file, but it didn't.");

        } catch (IOException e) {
            // Αν πιάσουμε IOException, τότε το τεστ ΠΕΤΥΧΕ (το περιμέναμε!)
            System.out.println("[PASS] Correctly threw IOException for missing file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[FAIL] Threw unexpected exception type (expected IOException): " + e.getClass().getSimpleName());
        }
    }
}