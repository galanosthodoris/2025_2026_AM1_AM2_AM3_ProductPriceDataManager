package controller;

import model.Measurement;
import model.Product;
import model.Year;
import loader.MetaDataLoader;
import loader.DataLoader;

import dto.*;
import java.util.*;
import java.io.*;

public class MyController implements IController {

    private final Map<String, Product> productsByAlias = new HashMap<>();
    private final Map<Integer, Year> yearsByValue = new HashMap<>();

    public MyController() {}

    public int initializeFromIni(String iniPath, String delimiter) throws IOException {
        String dataFile = null;
        String metadataFile = null;

        try (BufferedReader br = new BufferedReader(new FileReader(iniPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("dataFile=")) {
                    dataFile = line.substring("dataFile=".length()).trim();
                }
                if (line.startsWith("metadataFile=")) {
                    metadataFile = line.substring("metadataFile=".length()).trim();
                }
            }
        }

        MetaDataLoader.loadMetadata(metadataFile, productsByAlias);
        DataLoader.loadData(dataFile, productsByAlias, yearsByValue);

        return yearsByValue.size();
    }

    public void loadFile(String path, String delimiter) throws IOException {
        productsByAlias.clear();
        yearsByValue.clear();

        DataLoader.loadData(path, productsByAlias, yearsByValue);
    }

    public List<YearDTO> listYears() {

        List<YearDTO> yearDTOs = new ArrayList<>();

        for (Year year : yearsByValue.values()) {

            List<MeasurementDTO> mList = new ArrayList<>();
            for (Measurement m : year.getMeasurements()) 
            {
                mList.add(new MeasurementDTO(m.getYear().getYear(),m.getProduct().getAlias(), m.getValue()));
            }

            List<String> top = new ArrayList<>(year.getTop10Aliases());

            List<String> news = new ArrayList<>(year.getHeadlines());

            yearDTOs.add(new YearDTO(year.getYear(), mList, top, news));
        }

        return yearDTOs;
    }

    public ProductDTO getProductMeasurements(String productAlias) {

        Product product = productsByAlias.get(productAlias);
        if (product == null) {
            return null;
        }

        List<MeasurementDTO> mList = new ArrayList<>();
        for (Measurement m : product.getMeasurements()) {
            mList.add(new MeasurementDTO(m.getYear().getYear(), productAlias, m.getValue()));
        }

        return new ProductDTO(productAlias, mList);
    }


    
    
    public List<ProductDTO> listProducts() {
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : productsByAlias.values()) {

            List<MeasurementDTO> mList = new ArrayList<>();

            for (Measurement m : product.getMeasurements()) 
                {
                mList.add(new MeasurementDTO(m.getYear().getYear(), product.getAlias(), m.getValue()));
            }

            productDTOs.add(new ProductDTO(product.getAlias(), mList));
        }

        return productDTOs;
    }

    public YearDTO getYearMeasurements(int year)
    {

        Year yr = yearsByValue.get(year);
        if (yr == null) {
            return null;
        }

        List<MeasurementDTO> mList = new ArrayList<>();
        for (Measurement m : yr.getMeasurements()) {
            mList.add(new MeasurementDTO(m.getYear().getYear(), m.getProduct().getAlias(), m.getValue()));
        }

        List<String> top = new ArrayList<>(yr.getTop10Aliases());

        List<String> news = new ArrayList<>(yr.getHeadlines());

        return new YearDTO(yr.getYear(), mList, top, news);
    }

    public ProductDTO filterProductMeasurements(String productAlias, int minYear, int maxYear) {

        Product product = productsByAlias.get(productAlias);
        if (product == null) {
            return null;
        }

        List<MeasurementDTO> mList = new ArrayList<>();
        for (Measurement m : product.getMeasurements()) {
            int yearValue = m.getYear().getYear();
            if (yearValue >= minYear && yearValue <= maxYear) {
                mList.add(new MeasurementDTO(yearValue, productAlias, m.getValue()));
            }
        }

        return new ProductDTO(productAlias, mList);
    }
    
    public List<ProductHighlightDTO> reportProductHighlights(String productAlias) {

        List<ProductHighlightDTO> results = new ArrayList<>();

        for (Year y : yearsByValue.values()) {
            List<String> top10 = y.getTop10Aliases();
            List<String> headlines = y.getHeadlines();

            // 1. Find the index of the product in the Top 10 list
            int index = top10.indexOf(productAlias);

            // 2. Check if the product was found AND if a corresponding headline exists
            // (We check 'index < headlines.size()' to prevent crashes if the file is malformed)
            if (index != -1 && index < headlines.size()) {
                
                // 3. Get ONLY the headline that matches this product's position
                String specificHeadline = headlines.get(index);
                
                results.add(new ProductHighlightDTO(y.getYear(), specificHeadline));
            }
        }

        results.sort(Comparator.comparingInt(ProductHighlightDTO::getYear));
        return results;
    }

    public List<CategoryHighlightDTO> reportCategoryHighlights(String category) {

        List<CategoryHighlightDTO> list = new ArrayList<>();

        for (Year y : yearsByValue.values()) {
            List<String> top10 = y.getTop10Aliases();
            List<String> headlines = y.getHeadlines();

            // Iterate by index so we can match the Product to its specific Headline
            for (int i = 0; i < top10.size(); i++) {
                
                String alias = top10.get(i);
                Product p = productsByAlias.get(alias);

                // Check if this specific product belongs to the requested category
                if (p != null && p.getCategory().equals(category)) {

                    // Safety check: make sure a headline exists at this index
                    if (i < headlines.size()) {
                        String specificHeadline = headlines.get(i);
                        list.add(new CategoryHighlightDTO(y.getYear(), alias, specificHeadline));
                    }
                }
            }
        }

        list.sort(Comparator.comparingInt(CategoryHighlightDTO::getYear));
        return list;
    }

    
    public List<ProductStatsDTO> computeProductStats() {

        List<ProductStatsDTO> statsList = new ArrayList<>();

        for (Product product : productsByAlias.values()) {

            List<Measurement> ms = product.getMeasurements();
            if (ms.isEmpty()) {
                continue;
            }

            double min = Double.MAX_VALUE;
            double max = -Double.MAX_VALUE;
            double sum = 0;
            int count = 0;

            double lastValue = 0;
            int lastYear = -1;

            for (Measurement m : ms) {
                double value = m.getValue();
                int year = m.getYear().getYear();

                if (value < min) min = value;
                if (value > max) max = value;
                sum += value;
                count++;

                if (year > lastYear) {
                    lastYear = year;
                    lastValue = value;
                }
            }

            double avg = sum / count;

            statsList.add(
                new ProductStatsDTO(product.getAlias(), min, avg, max, lastValue)
            );
        }

        return statsList;
    }

    public List<Top10AppearanceDTO> computeTop10ProductAppearances() {

        Map<String, Integer> appearanceCount = new HashMap<>();

        for (Year y : yearsByValue.values()) {
            for (String alias : y.getTop10Aliases()) {
                appearanceCount.put(alias, appearanceCount.getOrDefault(alias, 0) + 1);
            }
        }

        List<Top10AppearanceDTO> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : appearanceCount.entrySet()) {
            result.add(new Top10AppearanceDTO(entry.getKey(), entry.getValue()));
        }

        result.sort(Comparator.comparingInt(Top10AppearanceDTO::getCount).reversed().thenComparing(Top10AppearanceDTO::getName));

        return result;
    }

    public List<Top10AppearanceDTO> computeTop10CategoryAppearances() {

        Map<String, Integer> categoryCount = new HashMap<>();

        for (Year y : yearsByValue.values()) {
            for (String alias : y.getTop10Aliases()) {
                Product p = productsByAlias.get(alias);
                if (p != null) {
                    String category = p.getCategory();
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }

        List<Top10AppearanceDTO> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            result.add(new Top10AppearanceDTO(entry.getKey(), entry.getValue()));
        }

        result.sort(Comparator.comparingInt(Top10AppearanceDTO::getCount).reversed().thenComparing(Top10AppearanceDTO::getName));

        return result;
    }

    public List<YearDTO> reportAllYearsAllProductPrices() {

        List<YearDTO> yearDTOs = new ArrayList<>();

        for (Year year : yearsByValue.values()) {

            List<MeasurementDTO> mList = new ArrayList<>();
            for (Measurement m : year.getMeasurements()) 
            {
                mList.add(new MeasurementDTO(m.getYear().getYear(),m.getProduct().getAlias(), m.getValue()));
            }

            List<String> top = new ArrayList<>(year.getTop10Aliases());

            List<String> news = new ArrayList<>(year.getHeadlines());

            yearDTOs.add(new YearDTO(year.getYear(), mList, top, news));
        }

        return yearDTOs;
    }
}
