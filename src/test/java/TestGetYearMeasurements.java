import controller.MyController;
import dto.YearDTO;

public class TestGetYearMeasurements {

    public static void main(String[] args) throws Exception {

        MyController controller = new MyController();

        System.out.println("Test 1 : HAPPY DAY");
        controller.initializeFromIni("src/test/resources/test.ini", "\t");

        YearDTO y = controller.getYearMeasurements(1960);
        if (y != null && y.getMeasurements().size() > 0) {
            System.out.println("[PASS] getYearMeasurements(1960) returned measurements.");
        } else {
            System.out.println("[FAIL] Expected measurements for year 1960.");
        }
        
        System.out.println("Test 2 : RAINY DAY");
        YearDTO missing = controller.getYearMeasurements(9999);

        if (missing == null) {
            System.out.println("[PASS] getYearMeasurements(9999) correctly returned null.");
        } else {
            System.out.println("[FAIL] Expected null for non-existing year.");
        }
    }
}
