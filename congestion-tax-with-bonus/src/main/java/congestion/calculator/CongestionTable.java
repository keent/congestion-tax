package congestion.table;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import congestion.calculator.CongestionTaxCalculator;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CongestionTable {

    private static Map<String, String> congestionTable = new HashMap<>();

    private static CongestionTable instance;

    public static final String JSONFILENAME = "congestionTable.json";

    private CongestionTable(String jsonFileName) {
        initializeCongestionTable(jsonFileName);
    }
    public static CongestionTable getInstance() {
        if (instance == null) {
            instance = new CongestionTable(JSONFILENAME);
        }
        return instance;
    }

    private static void initializeCongestionTable(String jsonFileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
//            File jsonFile = new File(jsonFileName);

            ClassLoader classLoader = CongestionTable.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(jsonFileName);

            // Read JSON file and parse
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Access the congestionTable array
            JsonNode congestionTableNode = rootNode.get("congestionTableStockholm");

            // Process each entry in the congestionTable
            for (JsonNode entry : congestionTableNode) {
                String time = entry.get("time").asText();
                String amount = entry.get("amount").asText();
                congestionTable.put(time, amount);
                System.out.println("Time: " + time + ", Amount: " + amount);
            }
        } catch (IOException e) {
            System.out.println("Error reading congestion table.");
            e.printStackTrace();
        }
    }

    public int getTollFee(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = dateFormat.format(date);

        if (2013 != date.getYear() + 1900) return 0; // Fixed, added scope
        if (CongestionTaxCalculator.isTollFreeDate(date)) return 0;

        // Check if the formattedTime falls within any of the time ranges in the congestionTable
        for (String timeRange : congestionTable.keySet()) {
            if (isTimeInRange(formattedTime, timeRange)) {

                return Integer.parseInt(congestionTable.get(timeRange));
            }
        }

        return 0;
    }

    private boolean isTimeInRange(String timeToCheck, String timeRange) {
        String[] parts = timeRange.split("–");
        String startTime = parts[0].trim();
        String endTime = parts[1].trim();

        return (timeToCheck.compareTo(startTime) >= 0) && (timeToCheck.compareTo(endTime) <= 0);
    }

}
