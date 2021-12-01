import java.io.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Objects;

public class DistributorReader {

    //Define file
    String distributors = "resources/Distributors.xlsx";

    public DistributorReader() {
    }

    //Getting the lowest cost of a candy
    public double returnLowestCost(String candyName) {

        //Assign the biggest integer possible to the cheapest variable
        double cheapest = Integer.MAX_VALUE;


        try {
            //Apache POI
            File distributorsFile = new File(distributors);
            FileInputStream stream = new FileInputStream(distributorsFile);
            XSSFWorkbook workbook = new XSSFWorkbook(stream);

            //Looping through all the sheets
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //looping through all the rows
                for (int j = 1; j < workbook.getSheetAt(i).getLastRowNum()+1; j++) {
                    //gets the candy name from the row
                    String currentCandy = workbook.getSheetAt(i).getRow(j).getCell(0).getStringCellValue();

                    //checks if candy name is equal to the one passed in
                    if (Objects.equals(candyName, currentCandy)) {
                        // If the candy's price is lower than the max value or previous price, it will become the new lowest
                        double currentCost = workbook.getSheetAt(i).getRow(j).getCell(2).getNumericCellValue();
                        if (currentCost < cheapest) cheapest = currentCost;
                    }
                }
            }



        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
        return cheapest;
    }

    // Use jackson object mapper to convert json string into an object
    public static List<CandyOrder> convertJsonString2Objects(String jsonString){
        List<CandyOrder> candyOrders = null;

        try {
            candyOrders = new ObjectMapper().readValue(jsonString, new TypeReference<List<CandyOrder>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return candyOrders;
    }
}
