import controller.MyController;
import java.io.IOException;

public class TestInitializeFromIni {

    public static void main(String[] args) {

        //Happy Day (Valid INI File)
        System.out.println("Test 1: Happy Day (Load Valid INI)");
        try {
            MyController controller = new MyController();
            

            String validIniPath = "src/test/resources/test.ini"; 
            
            int yearsLoaded = controller.initializeFromIni(validIniPath, "\t");

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

        // Rainy Day (Missing INI File)
        System.out.println("Test 2: Rainy Day (Load Non-Existent INI)");
        try {
            MyController controller = new MyController();
            
            String invalidPath = "notexist.ini";
            
            controller.initializeFromIni(invalidPath, "\t");

            System.out.println("[FAIL] Method should have thrown IOException for missing file, but it didn't.");

        } catch (IOException e) {
        	
            System.out.println("[PASS] Correctly threw IOException for missing file: " + e.getMessage());
            
        } catch (Exception e) {
        	
            System.out.println("[FAIL] Threw unexpected exception type (expected IOException): " + e.getClass().getSimpleName());
        
        }
    }
}