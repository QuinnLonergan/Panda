import java.io.FileInputStream;

import static spark.Spark.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) {

        //This is required to allow GET and POST requests with the header 'content-type'
        options("/*",
                (request, response) -> {
                    response.header("Access-Control-Allow-Headers",
                            "content-type");

                    response.header("Access-Control-Allow-Methods",
                            "GET, POST");


                    return "OK";
                });

        //This is required to allow the React app to communicate with this API
        before((request, response) -> response.header("Access-Control-Allow-Origin", "http://localhost:3000"));

        //added inventory path to show full inventory
        get("/inventory", (req, res) -> {

            // Read Excel File into list Objects
            List<Inventory> inventoriesFull = readExcelFileFull("resources/Inventory.xlsx");

            return(convertObjects2JsonString(inventoriesFull));
        });

        //path for low stock
        get("/low-stock", (request, response) -> {
            LowStock lowStock = new LowStock();

            // Read Excel File into list objects
            List<Inventory> inventoriesLow = lowStock.readExcelFile("resources/Inventory.xlsx");

            return (convertObjects2JsonString(inventoriesLow));
        });

        //path for restock cost
        post("/restock-cost", (request, response) -> {

            Restock restock = new Restock();
            double restockCost = Double.parseDouble(restock.getRestockCost(request.body()));

            //make LowestPrice object
            LowestPrice lowestPrice = new LowestPrice();
            lowestPrice.setPrice(restockCost);

            return convertDouble2JsonString(lowestPrice);
        });


    }

    private static List<Inventory> readExcelFileFull(String filePath){
        try {
            FileInputStream excelFile = new FileInputStream((filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheet("Randy's Candies");
            Iterator<Row> rows = sheet.iterator();

            List<Inventory> lstInventory = new ArrayList<Inventory>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Inventory inv = new Inventory();

                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    if(cellIndex==0) { // Name
                        inv.setName(currentCell.getStringCellValue());
                    } else if(cellIndex==1) { // Stock
                        inv.setStock((int) currentCell.getNumericCellValue());
                    } else if(cellIndex==2) { // Capacity
                        inv.setCapacity((int) currentCell.getNumericCellValue());
                    } else if(cellIndex==3) { // Id
                        inv.setId(String.valueOf(currentCell.getNumericCellValue()));
                    }

                    cellIndex++;
                }

                    lstInventory.add(inv);

            }

            workbook.close();

            return lstInventory;
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }


    // Use jackson object mapper to convert object into json string
    private static String convertObjects2JsonString(List<Inventory> inventories) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(inventories);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    // Use jackson object mapper to convert double into json string
    private static String convertDouble2JsonString(LowestPrice lowestPrices) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(lowestPrices);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
