package loader;

import model.Product;
import model.Year;
import model.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class DataLoader {

    public static void loadData(String filePath,
                                Map<String, Product> productsByAlias,
                                Map<Integer, Year> yearsByValue) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Read header line
            String header = br.readLine();
            if (header == null) {
                return;  // empty file
            }

            String[] columns = header.split("\t");
            int columnCount = columns.length;

            // map: column index -> Product
            Map<Integer, Product> columnIndexToProduct = new HashMap<>();

            for (int i = 1; i < columnCount - 2; i++) { // skip year (0), last two are top10 & headlines
                String columnName = columns[i].trim(); 

                Product matched = null;
                for (Product p : productsByAlias.values()) {
                    if (p.getName().equals(columnName)) {
                        matched = p;
                        break;
                    }
                }

                if (matched != null) {
                    columnIndexToProduct.put(i, matched);
                }
            }

            // Read data 
            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue; 
                }

                String[] parts = line.split("\t");
                if (parts.length < columnCount) {
                    continue;
                }

                // Year
                int yearValue = Integer.parseInt(parts[0].trim());
                Year year = yearsByValue.get(yearValue);
                if (year == null) {
                    year = new Year(yearValue);
                    yearsByValue.put(yearValue, year);
                }

                // Measurements
                for (Map.Entry<Integer, Product> entry : columnIndexToProduct.entrySet()) {
                    int colIndex = entry.getKey();
                    Product product = entry.getValue();

                    String valStr = parts[colIndex].trim();
                    if (valStr.isEmpty()) {
                        continue; 
                    }

                    double value = Double.parseDouble(valStr);

                    Measurement m = new Measurement(product, year, value);
                    product.addMeasurement(m);
                    year.addMeasurement(m);
                }

                // Top-10 column 
                String top10Field = parts[columnCount - 2].trim();
                
                top10Field = top10Field.replace("\"", ""); //ftiaxnei to oil
                
                if (!top10Field.isEmpty()) {
                    String[] topAliases = top10Field.split(",");
                    for (String a : topAliases) {
                        String alias = a.trim();
                        if (!alias.isEmpty()) {
                            year.getTop10Aliases().add(alias);
                        }
                    }
                }

                // Headlines column 
                String headlineField = parts[columnCount - 1].trim();
                if (!headlineField.isEmpty()) {
                    // Headlines separated by |
                    String[] headlines = headlineField.split("\\|");
                    for (String h : headlines) {
                        String headline = h.trim();
                        if (!headline.isEmpty()) {
                            year.getHeadlines().add(headline);
                        }
                    }
                }
            }

        } catch (IOException e) {
        	
        	 throw new RuntimeException("Failed to load data file: " + filePath, e);
        }
    }
}

