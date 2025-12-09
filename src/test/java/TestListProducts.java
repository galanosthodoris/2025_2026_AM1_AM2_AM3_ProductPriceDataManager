import controller.MyController;
import dto.ProductDTO;

public class TestListProducts {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        System.out.println("Test 1 : HAPPY DAY");

        // HAPPY DAY
        try {
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            java.util.List<ProductDTO> products = controller.listProducts();

            if (products != null && products.size() > 0) {
                System.out.println("[PASS] listProducts() returned " + products.size() + " products.");
            } else {
                System.out.println("[FAIL] listProducts() returned 0 products.");
            }

            boolean aliasesOk = true;
            for (ProductDTO p : products) {
                if (p.getName() == null || p.getName().trim().isEmpty()) {
                    aliasesOk = false;
                    break;
                }
            }

            if (aliasesOk) {
                System.out.println("[PASS] Each product has a valid alias.");
            } else {
                System.out.println("[FAIL] Some products have invalid alias.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception when loading valid data: " + e.getMessage());
        }

        // RAINY DAY 
        System.out.println("Test 2 : RAINY DAY");
        try {
            controller.loadFile("nonexistent.tsv", "\t");
            System.out.println("[FAIL] Expected failure when loading nonexistent file.");
        } catch (Exception e) {
            System.out.println("[PASS] Correctly failed on nonexistent data file.");
        }
    }
}
