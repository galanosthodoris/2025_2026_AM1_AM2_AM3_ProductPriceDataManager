package controller;

import dto.ProductDTO;
import dto.ProductHighlightDTO;
import dto.ProductStatsDTO;
import dto.Top10AppearanceDTO;
import dto.YearDTO;
import dto.CategoryHighlightDTO;

import java.io.IOException;
import java.util.List;

/**
 * Facade interface for accessing and querying product price data.
 * <p>
 * The interface provides methods for loading data and metadata, 
 * retrieving measurements, computing statistics, and reporting highlights.
 * Implementations should handle parsing of structured data files and 
 * maintain data in memory for efficient access.
 * </p>
 */
public interface IController {

    /**
     * Initializes the controller by reading an INI-style configuration file.
     * <p>
     * The INI file must contain two properties:
     * <ul>
     *     <li>dataFile: path to the data file containing yearly product prices</li>
     *     <li>metadataFile: path to the metadata file describing each product column</li>
     * </ul>
     * This method loads both the metadata and the data into memory.
     *
     * @param iniPath   path to the INI file
     * @param delimiter the delimiter used in the data file (e.g., "\t" for TSV or "," for CSV)
     * @return the number of years successfully loaded from the data file
     * @throws IOException if the INI file, metadata file, or data file cannot be read
     */
    int initializeFromIni(String iniPath, String delimiter) throws IOException;

    /**
     * Loads a data file directly, bypassing the INI configuration.
     * <p>
     * This method is primarily intended for CLI demos or test purposes.
     * Each column header is used as the product alias if no metadata is provided.
     *
     * @param path      path to the data file
     * @param delimiter the delimiter used in the data file
     * @throws IOException if the file cannot be read
     */
    void loadFile(String path, String delimiter) throws IOException;

    /**
     * Returns a list of all years available in the data.
     *
     * @return a list of YearDTO objects representing all years
     */
    List<YearDTO> listYears();

    /**
     * Returns a list of all products available in the data.
     *
     * @return a list of ProductDTO objects representing all products
     */
    List<ProductDTO> listProducts();

    /**
     * Returns all measurements for a given year.
     *
     * @param year the year to query
     * @return a YearDTO containing measurements, top-10 aliases, and headlines for the year
     */
    YearDTO getYearMeasurements(int year);

    /**
     * Returns all measurements for a specific product across all years.
     *
     * @param productName the product alias to query
     * @return a ProductDTO containing all measurements for the product
     */
    ProductDTO getProductMeasurements(String productName);

    /**
     * Returns measurements for a specific product filtered by a year range.
     *
     * @param productName the product alias to query
     * @param minYear     minimum year (inclusive)
     * @param maxYear     maximum year (inclusive)
     * @return a ProductDTO containing measurements within the specified year range
     */
    ProductDTO filterProductMeasurements(String productName, int minYear, int maxYear);

    /**
     * Reports highlights for a specific product.
     * <p>
     * Highlights correspond to the years when the product appeared in the top-10 
     * list, along with the associated news headline.
     *
     * @param productAlias the alias of the product
     * @return a list of ProductHighlightDTO with <year, headline> pairs sorted by year
     */
    List<ProductHighlightDTO> reportProductHighlights(String productAlias);

    /**
     * Reports highlights for a specific product category.
     * <p>
     * Highlights include all top-10 appearances of products belonging to the category,
     * along with the corresponding news headline.
     *
     * @param category the name of the product category
     * @return a list of CategoryHighlightDTO with <year, product, headline> triples sorted by year
     */
    List<CategoryHighlightDTO> reportCategoryHighlights(String category);

    /**
     * Computes basic statistics for all products.
     * <p>
     * Statistics include minimum value, average, maximum value, and last available value.
     *
     * @return a list of ProductStatsDTO representing statistics for each product
     */
    List<ProductStatsDTO> computeProductStats();

    /**
     * Computes the number of top-10 appearances for each product.
     *
     * @return a list of Top10AppearanceDTO containing product aliases and their counts
     */
    List<Top10AppearanceDTO> computeTop10ProductAppearances();

    /**
     * Computes the number of top-10 appearances for each product category.
     * <p>
     * Multiple products in the same category appearing in the same year are counted individually.
     *
     * @return a list of Top10AppearanceDTO containing category names and their counts
     */
    List<Top10AppearanceDTO> computeTop10CategoryAppearances();

    /**
     * Returns all product prices for all years.
     * <p>
     * Effectively reconstructs the original dataset with <year, product> values.
     *
     * @return a list of YearDTO objects containing measurements for all products and years
     */
    List<YearDTO> reportAllYearsAllProductPrices();
}
