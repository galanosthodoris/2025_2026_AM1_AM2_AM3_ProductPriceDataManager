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

        List<YearDTO> yearDTOs = new ArrayList<>();  // lista apo ta dto

        for (Year year : yearsByValue.values()) {

            List<MeasurementDTO> mList = new ArrayList<>();//lista apo measurments
            
            for (Measurement m : year.getMeasurements()) 
            {
                mList.add(new MeasurementDTO(m.getYear().getYear(),m.getProduct().getAlias(), m.getValue()));// gemizoume thn lista twn measurment
            }

           List<String> top = new ArrayList<>(year.getTop10Aliases());

           List<String> news = new ArrayList<>(year.getHeadlines());

           yearDTOs.add(new YearDTO(year.getYear(), mList, top, news));//gemizoume thn lista apo ta yeardto k ta epistrefoume
        }

        return yearDTOs;
    }

    public ProductDTO getProductMeasurements(String productAlias) {

        Product product = productsByAlias.get(productAlias);// dimiourgei ta antikeimena Product
        
        if (product == null) { // elenhos an yparxei
            return null;
        }

        List<MeasurementDTO> mList = new ArrayList<>();// lista apo measurmentsdto
        for (Measurement m : product.getMeasurements()) {
            mList.add(new MeasurementDTO(m.getYear().getYear(), productAlias, m.getValue()));//gemizoume thn lista 
        }

        return new ProductDTO(productAlias, mList);//dimiourgei to ProductDTO k to epistefei
    }


    
    
    public List<ProductDTO> listProducts() {
    	
        List<ProductDTO> productDTOs = new ArrayList<>();//san thn listYear alla gia ta Products anti gia Years

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

        Year yr = yearsByValue.get(year);//dinoume thn xronia pou theloyme
        if (yr == null) {//check
            return null;
        }

        List<MeasurementDTO> mList = new ArrayList<>();//lista me tis times kathe xronias
        for (Measurement m : yr.getMeasurements()) {
            mList.add(new MeasurementDTO(m.getYear().getYear(), m.getProduct().getAlias(), m.getValue()));//mpainoun oi times sth lista
        }

        List<String> top = new ArrayList<>(yr.getTop10Aliases());//lista apo ta top10

        List<String> news = new ArrayList<>(yr.getHeadlines());//lista ta headlines

        return new YearDTO(yr.getYear(), mList, top, news);//arxikopoish kai epistrofh
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
                mList.add(new MeasurementDTO(yearValue, productAlias, m.getValue())); //vazei sth lista an einai sth xronia
            }
        }

        return new ProductDTO(productAlias, mList);
    }
    
    public List<ProductHighlightDTO> reportProductHighlights(String productAlias) {

        List<ProductHighlightDTO> results = new ArrayList<>();// lista apo ProductHIghlightDTO

        for (Year y : yearsByValue.values()) {
            List<String> top10 = y.getTop10Aliases();//lista ola ta top10aliases kathe xrono
            List<String> headlines = y.getHeadlines();//lista ola ta headlines kathe xrono

            int index = top10.indexOf(productAlias); // thesi tou headline


            if (index != -1 && index < headlines.size()) {
                
                String specificHeadline = headlines.get(index);//vres to headline toy kathe index
                
                results.add(new ProductHighlightDTO(y.getYear(), specificHeadline));//gemise thn lista apo ProductHIghlightDTO
            }
        }

        return results;
    }

    public List<CategoryHighlightDTO> reportCategoryHighlights(String category) {

        List<CategoryHighlightDTO> list = new ArrayList<>();//ista ta dtos

        for (Year y : yearsByValue.values()) {//ana xronia
            List<String> top10 = y.getTop10Aliases();
            List<String> headlines = y.getHeadlines();

            for (int i = 0; i < top10.size(); i++) {// psaxnoume sta highlights
                
                String alias = top10.get(i);
                Product p = productsByAlias.get(alias);

                if (p != null && p.getCategory().equals(category)) {//an yparxei category gia ayto kan einai aytopou theloume

                        String specificHeadline = headlines.get(i);//to headline pou theloume
                        list.add(new CategoryHighlightDTO(y.getYear(), alias, specificHeadline));//prosthese sth lista ayto pou theloume
                }
            }
        }

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

            for (Measurement m : ms) {//trexoume ola ta measurments k vgazoume ta statistika
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

        Map<String, Integer> appearanceCount = new HashMap<>();//map gia product k pose fores emfanizetai

        for (Year y : yearsByValue.values()) {//for gia ola ta xronia
            for (String alias : y.getTop10Aliases()) {//for sta top 10
                appearanceCount.put(alias, appearanceCount.getOrDefault(alias, 0) + 1);//aujish oti vrei
            }
        }

        List<Top10AppearanceDTO> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : appearanceCount.entrySet()) {
            result.add(new Top10AppearanceDTO(entry.getKey(), entry.getValue()));
        }
        //sortarei thn lista
        result.sort(Comparator.comparingInt(Top10AppearanceDTO::getCount).reversed().thenComparing(Top10AppearanceDTO::getName));

        return result;
    }

    public List<Top10AppearanceDTO> computeTop10CategoryAppearances() {

        Map<String, Integer> categoryCount = new HashMap<>();// san thn prohgoumrnh alla gia categories

        for (Year y : yearsByValue.values()) {
            for (String alias : y.getTop10Aliases()) {
                Product p = productsByAlias.get(alias);
                if (p != null) {//check an exei category to product
                    String category = p.getCategory();
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }

        List<Top10AppearanceDTO> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            result.add(new Top10AppearanceDTO(entry.getKey(), entry.getValue()));
        }
        // sortarei thn lista
        result.sort(Comparator.comparingInt(Top10AppearanceDTO::getCount).reversed().thenComparing(Top10AppearanceDTO::getName));

        return result;
    }

    public List<YearDTO> reportAllYearsAllProductPrices() {
    	//dto pou exei sxedon ta panta
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
