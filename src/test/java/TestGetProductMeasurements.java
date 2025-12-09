import controller.MyController;
import dto.ProductDTO;

public class TestGetProductMeasurements {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        System.out.println("Test 1 : HAPPY DAY");

        //HAPPY DAY 
        try {
            controller.initializeFromIni("src/test/resources/test.ini", "\t");

            ProductDTO oil = controller.getProductMeasurements("Oil");

            if (oil != null && oil.getMeasurements().size() > 0) {
                System.out.println("[PASS] Retrieved measurements for Oil. Count = "
                                   + oil.getMeasurements().size());
            } else {
                System.out.println("[FAIL] Oil should have measurements but returned null/empty.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Should have loaded data: " + e.getMessage());
        }


        System.out.println("Test 2 : RAINY DAY");
        try {
            ProductDTO fake = controller.getProductMeasurements("NotExist");

            if (fake == null) {
                System.out.println("[PASS] Correctly returned null for nonexistent product.");
            } else {
                System.out.println("[FAIL] Expected null for nonexistent product.");
            }

        } catch (Exception e) {
            System.out.println("[FAIL] Exception in rainy test: " + e.getMessage());
        }
    }
}
