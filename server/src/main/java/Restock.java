import java.util.List;
import java.text.DecimalFormat;

public class Restock {
    public String getRestockCost(String jsonOrders) {

        //Converting Json request into a CandyOrder object
        DistributorReader distributor = new DistributorReader();
        List<CandyOrder> candyOrders = distributor.convertJsonString2Objects(jsonOrders);


        double total = 0;

        //looping through each CandyOrder
        for (CandyOrder e : candyOrders) {
            String candyName = e.getName();
            double restockAmount = e.getOrder();

            //Using the lowest cost method to get the candy's lowest cost
            double lowestCost = distributor.returnLowestCost(candyName);
            //multiplying that by the amount ordered
            double restockCost = lowestCost * restockAmount;

            //adding to total
            total += restockCost;

        }
        //Decimal format to 2 spaces
        DecimalFormat d = new DecimalFormat("#.##");

        return d.format(total);
    }
}