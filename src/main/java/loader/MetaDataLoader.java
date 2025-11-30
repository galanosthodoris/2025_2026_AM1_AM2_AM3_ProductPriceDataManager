package loader;

import model.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class MetaDataLoader 
{
    public static void loadMetadata(String filePath, Map<String, Product> products)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
        while ((line = br.readLine()) != null)
        {
            String delimiter = "\t";
            String parts[] = line.split(delimiter);
            String fullName = parts[0].trim();
            String alias = parts[1].trim();
            String category = parts[2].trim();
            Product p = new Product(fullName, alias, category);
            products.put(alias, p);
        }  
        }  
        catch(IOException e)
        {
        	System.out.println("Current Working Directory: " + new java.io.File(".").getAbsolutePath());
            System.err.println("Error reading metadata file: " + e.getMessage());
        }
    }
}
