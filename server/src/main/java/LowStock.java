import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LowStock {
    public static List<Inventory> readExcelFile(String filePath){
        try {
            FileInputStream excelFile = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheet("Randy's Candies");
            Iterator<Row> rows = sheet.iterator();

            List<Inventory> lstInventory = new ArrayList<>();

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

                if(inv.getStock() < inv.getCapacity()/4) {
                    lstInventory.add(inv);
                }
            }

            // Close WorkBook
            workbook.close();

            return lstInventory;
        } catch (IOException e) {
            throw new RuntimeException("Failed -> message = " + e.getMessage());
        }
    }
}
