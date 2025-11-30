package clients.cli;

import controller.ControllerFactory;
import controller.IController;
import dto.ProductDTO;
import dto.YearDTO;

import java.io.IOException;
import java.util.Scanner;

public class CliDemo {
    public static void main(String[] args) {
        IController controller = ControllerFactory.createController();

        try (Scanner sc = new Scanner(System.in)) {
			System.out.println("=== CLI Demo ===");
			System.out.print("Enter path to config.ini (or press Enter for default src/main/resources/config/config.ini): ");
			String iniPath = sc.nextLine().trim();
			if (iniPath.isEmpty()) {
			    iniPath = "./src/main/resources/config/config.ini";
			}

			try {
				int numRows = 0;
			    numRows = controller.initializeFromIni(iniPath, "\t"); // default delimiter = tab
			    System.out.println("Loaded " + numRows + " rows. Data loaded successfully!");

			    while (true) {
			        System.out.println("\nMenu:");
			        System.out.println("1. Show measurements of a year");
			        System.out.println("2. Show measurements of a product");
			        System.out.println("3. Show product measurements in a year range");
			        System.out.println("0. Exit");
			        System.out.print("Choice: ");
			        int choice = Integer.parseInt(sc.nextLine().trim());
			        if (choice == 0) break;

			        switch (choice) {
			            case 1:
			                System.out.print("Enter year: ");
			                int y = Integer.parseInt(sc.nextLine().trim());
			                YearDTO yd = controller.getYearMeasurements(y);
			                System.out.println("Year " + y + " data:");
			                yd.getMeasurements().forEach(m ->
			                        System.out.printf("  %s -> %.2f%n", m.getProductName(), m.getValue()));
			                if (!yd.getTop10Aliases().isEmpty()) {
			                    System.out.println("Top 10 products: " + yd.getTop10Aliases());
			                }
			                if (!yd.getTop10Headlines().isEmpty()) {
			                    System.out.println("Headlines: " + yd.getTop10Headlines());
			                }
			                break;
			            case 2:
			                System.out.print("Enter product: ");
			                String prod = sc.nextLine().trim();
			                ProductDTO pd = controller.getProductMeasurements(prod);
			                pd.getMeasurements().forEach(m ->
			                        System.out.printf("%d: %.2f%n", m.getYear(), m.getValue()));
			                break;
			            case 3:
			                System.out.print("Enter product: ");
			                String prod2 = sc.nextLine().trim();
			                System.out.print("Min year: ");
			                int minY = Integer.parseInt(sc.nextLine().trim());
			                System.out.print("Max year: ");
			                int maxY = Integer.parseInt(sc.nextLine().trim());
			                ProductDTO pd2 = controller.filterProductMeasurements(prod2, minY, maxY);
			                pd2.getMeasurements().forEach(m ->
			                        System.out.printf("%d: %.2f%n", m.getYear(), m.getValue()));
			                break;
			        }
			    }
			} catch (IOException e) {
			    System.err.println("Error loading data: " + e.getMessage());
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
