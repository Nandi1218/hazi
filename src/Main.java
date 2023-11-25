import Commerce.*;
import Serialize.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    /** The save object for the Product class*/
    static Save<Product> saveProduct = new Save<>();
    /** The save object for the Vendor class*/
    static Save<Producer> saveProducer = new Save<>();
    /** The save object for the Producer class*/
    public static Save<Vendor> saveVendor = new Save<>();
    /** The save object for the Order class*/
    public static Save<Order> saveOrder = new Save<>();
    /** The list of all the products*/
    public static ArrayList<Product> products =Load.loadProduct();
    /** The list of all the vendors*/
    public static ArrayList<Vendor> vendors =Load.loadVendor();
    /** The list of all the producers*/
    public static ArrayList<Producer> producers =Load.loadProducer();
    /** The list of all the orders*/
    public static ArrayList<Order> orders =Load.loadOrder();
    /**
     * The main method of the program
     * Creates the GUI object and makes it visible
     * @param args the arguments of the main method
     */
    public static void main(String[] args) {
        if(!orders.isEmpty())
            if(!orders.get(0).getLastCheck().isEqual(LocalDate.now())){
                for (Producer producer : producers) {
                    producer.updateProducerQuantity(orders.get(0).getLastCheck());
                }
                for (Order order : orders) {
                    order.updateOrder();
                }
            }
        for (Order order :
                orders) {
            System.out.println(order.getSeller().getName()+" "+order.getBuyer().getName()+" "+order.getProduct().getName()+" "+order.getQuantity()+" "+order.getTotalPrice()+" "+order.getDate()+" "+order.getDeliveryDate()+"\t "+order.getDaysToDeliver());
        }
/*
        products.add(new Product("Carrot", "Long orange thingy"));
        products.add(new Product("Apple", "Red and crunchy"));
        products.add(new Product("Banana", "Yellow and sweet"));
        products.add(new Product("Laptop", "Powerful computing device"));
        products.add(new Product("Coffee Mug", "Ceramic mug for hot beverages"));
        products.add(new Product("Notebook", "Blank paper for writing"));
        products.add(new Product("Sunglasses", "UV protection for your eyes"));
        products.add(new Product("Running Shoes", "Comfortable footwear for running"));
        products.add(new Product("Headphones", "Audio devices for immersive sound"));
        products.add(new Product("Backpack", "Storage for your belongings"));
        products.add(new Product("Desk Lamp", "Lighting for your workspace"));
        products.add(new Product("Smartphone", "Mobile communication device"));
        products.add(new Product("Gaming Console", "Entertainment system for games"));
        products.add(new Product("Sweater", "Warm clothing for cold weather"));
        products.add(new Product("Watch", "Timekeeping accessory"));
        products.add(new Product("Camera", "Device for capturing photos and videos"));
        products.add(new Product("Plant", "Green decoration for your home"));
        products.add(new Product("Umbrella", "Protection from rain"));
        products.add(new Product("Chocolate", "Sweet treat for indulgence"));
        products.add(new Product("Hiking Boots", "Sturdy footwear for hiking"));
        products.add(new Product("Socks", "Clothing for your feet"));
        vendors.add(new Vendor("vendorA", "password1", "Amazing Vendor", 1200.0));
        vendors.add(new Vendor("vendorB", "password2", "Best Deals Vendor", 1500.0));
        vendors.add(new Vendor("vendorC", "password3", "Super Saver Vendor", 1100.0));
        vendors.add(new Vendor("vendorD", "password4", "Quick Buy Vendor", 1300.0));
        vendors.add(new Vendor("vendorE", "password5", "Discount Delight Vendor", 1400.0));
        vendors.add(new Vendor("vendorF", "password6", "Smart Shopper Vendor", 1600.0));
        vendors.add(new Vendor("vendorG", "password7", "Eco-Friendly Emporium", 1800.0));
        vendors.add(new Vendor("vendorH", "password8", "Gourmet Goods Galore", 1700.0));
        vendors.add(new Vendor("vendorI", "password9", "Tech Trends Hub", 1900.0));
        vendors.add(new Vendor("vendorJ", "password10", "Fashion Forward Finds", 2000.0));
        vendors.add(new Vendor("vendorK", "password11", "Home Essentials Haven", 2100.0));
        vendors.add(new Vendor("vendorL", "password12", "Outdoor Adventure Outfitters", 2200.0));
        vendors.add(new Vendor("vendorM", "password13", "Pet Paradise Emporium", 2300.0));
        vendors.add(new Vendor("vendorN", "password14", "Fitness Fanatics Finds", 2400.0));
        vendors.add(new Vendor("vendorO", "password15", "Artistic Inspirations Depot", 2500.0));
        vendors.add(new Vendor("vendorP", "password16", "Bookworm's Boutique", 2600.0));
        vendors.add(new Vendor("vendorQ", "password17", "Travel Treasures Trunk", 2700.0));
        vendors.add(new Vendor("vendorR", "password18", "Kids' Kingdom Corner", 2800.0));
        vendors.add(new Vendor("vendorS", "password19", "Health & Wellness Wonders", 2900.0));
        vendors.add(new Vendor("vendorT", "password20", "DIY Dreamland", 3000.0));
        producers.add(new Producer("jakeUser", "password123", "Jake's Produce Co.", products.get(3), new ArrayList<>(), 199.99, 30, 230));
        producers.add(new Producer("greenHarvest", "passGreen", "Green Harvest Farms", products.get(8), new ArrayList<>(), 149.99, 40, 180));
        producers.add(new Producer("freshPicks", "passFresh", "Fresh Picks Supply", products.get(12), new ArrayList<>(), 179.99, 25, 200));
        producers.add(new Producer("sunriseFarms", "passSunrise", "Sunrise Farms Ltd.", products.get(1), new ArrayList<>(), 219.99, 35, 250));
        producers.add(new Producer("organicBounty", "passOrganic", "Organic Bounty Growers", products.get(16), new ArrayList<>(), 159.99, 28, 210));
        producers.add(new Producer("goldenFields", "passGolden", "Golden Fields Agriculture", products.get(6), new ArrayList<>(), 129.99, 33, 190));
        producers.add(new Producer("bountifulHarvest", "passBountiful", "Bountiful Harvest Co.", products.get(11), new ArrayList<>(), 189.99, 27, 220));
        producers.add(new Producer("pureProduce", "passPure", "Pure Produce Supply", products.get(4), new ArrayList<>(), 169.99, 32, 240));
        producers.add(new Producer("greenThumbProduce", "passGreenThumb", "Green Thumb Produce", products.get(9), new ArrayList<>(), 199.99, 29, 215));
        producers.add(new Producer("ripeHarvest", "passRipe", "Ripe Harvest Farms", products.get(14), new ArrayList<>(), 149.99, 31, 205));
        producers.add(new Producer("sweetNectarFarms", "passSweetNectar", "Sweet Nectar Farms Ltd.", products.get(2), new ArrayList<>(), 179.99, 34, 225));
        producers.add(new Producer("harmonyHarvest", "passHarmony", "Harmony Harvest Co.", products.get(17), new ArrayList<>(), 219.99, 26, 235));
        producers.add(new Producer("freshFare", "passFreshFare", "Fresh Fare Supply", products.get(7), new ArrayList<>(), 159.99, 39, 195));
        producers.add(new Producer("natureNurtureFarms", "passNatureNurture", "Nature Nurture Farms", products.get(13), new ArrayList<>(), 129.99, 36, 185));
        producers.add(new Producer("ripeReadyProduce", "passRipeReady", "Ripe & Ready Produce", products.get(5), new ArrayList<>(), 189.99, 33, 255));
        producers.add(new Producer("purelyOrganic", "passPurelyOrganic", "Purely Organic Growers", products.get(10), new ArrayList<>(), 169.99, 28, 210));
        producers.add(new Producer("sunnyFieldsProduce", "passSunnyFields", "Sunny Fields Produce", products.get(15), new ArrayList<>(), 199.99, 32, 245));
        producers.add(new Producer("freshnessFarms", "passFreshness", "Freshness Farms Ltd.", products.get(18), new ArrayList<>(), 149.99, 30, 200));
        producers.add(new Producer("crispCrops", "passCrispCrops", "Crisp Crops Supply", products.get(0), new ArrayList<>(), 179.99, 35, 230));
        producers.add(new Producer("greenGroves", "passGreenGroves", "Green Groves Agriculture", products.get(19), new ArrayList<>(), 219.99, 27, 220));
        */

        GUI gui = new GUI();
        gui.setVisible(true);

    }
}