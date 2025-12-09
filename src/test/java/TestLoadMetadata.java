import controller.MyController;

public class TestLoadMetadata {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        // HAPPY DAY: Correct INI 
        System.out.println("Test 1 : HAPY DAY ");
        try {
            controller.initializeFromIni("src/test/resources/test.ini", "\t");
            System.out.println("[PASS] INI + metadata loaded successfully.");
        } catch (Exception e) {
            System.out.println("[FAIL] Should have loaded correctly: " + e.getMessage());
        }

        // RAINY DAY: wrong INI path
        System.out.println("Test 2 : RAINY DAY");
        try {
            controller.initializeFromIni("src/test/resources/does_not_exist.ini", "\t");
            System.out.println("[FAIL] Should NOT load incorrect INI.");
        } catch (Exception e) {
            System.out.println("[PASS] Correctly failed on missing INI.");
        }
    }
}