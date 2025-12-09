import controller.MyController;
import dto.YearDTO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestLoadFile {
		
	// den doulevei to test swsta

    public static void main(String[] args) {


        // Happy Day (Reload Valid Data)
        System.out.println("Test 1: Happy Day (Load File Manually)");
        try {
            MyController controller = new MyController();
            
            String iniPath = "test.ini"; 
            String dataPath = "data.tsv"; 

            File f = new File(iniPath);
            if (!f.exists()) {
                System.out.println("[ERROR] File not found");
                return; 
            }

            controller.initializeFromIni(iniPath, "\t");
            System.out.println("Initial load complete.");

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

        // Rainy Day (File Not Found)
        System.out.println("Test 2: Rainy Day (Load Missing File)");
        try {
            MyController controller = new MyController();
            try {
                controller.initializeFromIni("test.ini", "\t"); 
            } catch (Exception e) {
                System.out.println("[WARN] Skipping Setup for Rainy Day because init failed.");
            }
            
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