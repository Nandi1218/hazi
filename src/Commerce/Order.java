package Commerce;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Basic order class. Represents a single order
 * that a vendor makes
 */
public class Order implements Serializable {
    /**The vendor that makes the order */
    Vendor buyer;
    /**The producer that the order is made from */
    Producer seller;
    /**The product that is ordered */
    Product product;
    /**The quantity of the product that is ordered */
    int quantity;
    /**The quantity that has been delivered */
    int deliveredQuantity = 0;
    /**The total price of the order */
    double totalPrice;
    /**The date when the order was made */
    LocalDate date;
    /**The date when the last check was made */
    LocalDate lastCheck;
    /**The date when the order will be delivered */
    LocalDate deliveryDate;
    /**The number of days that the order will be delivered in */
    int daysToDeliver;
    /**Whether the order has been delivered or not */
    boolean delivered = false;

    /**
     * Constructor for the class Order
     * deliveryDate is calculated based on the quantity and the daily production of the producer
     * deliveredQuantity is calculated based on the quantity and the daily production of the producer
     * daysToDeliver is calculated based on the quantity and the daily production of the producer
     * totalPrice is calculated based on the quantity and the price of the producer
     * lastCheck is set to the current date
     * @param buyer
     * @param seller
     * @param product
     * @param quantity
     */

    public Order(Vendor buyer, Producer seller, Product product, int quantity) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = quantity * seller.getPrice();
        this.date = LocalDate.now();
        this.lastCheck = LocalDate.now();
        if (quantity <= seller.getQuantity()) {
            seller.setQuantity(seller.getQuantity() - quantity);
            deliveredQuantity = quantity;
            buyer.addToProductQuantity(product, quantity);
            delivered = true;
            daysToDeliver = 0;
        } else { //if more than the seller has in stock is ordered then the order will be delivered in multiple parts
            daysToDeliver = quantity - seller.getQuantity();
            deliveredQuantity = seller.getQuantity();
            buyer.addToProductQuantity(product, seller.getQuantity());
            seller.setQuantity(0);
            daysToDeliver = daysToDeliver / seller.getDailyProduction() + 1;
        }
        this.deliveryDate = date.plusDays(daysToDeliver);
    }
    /** getter method for the buyer
     * @return the buyer
     */
    public Vendor getBuyer() {
        return buyer;
    }
    /** setter method for the buyer
     * @param buyer the new buyer
     */
    public void setBuyer(Vendor buyer) {
        this.buyer = buyer;
    }
    /** getter method for the seller
     * @return the seller
     */
    public Producer getSeller() {
        return seller;
    }
    /** getter method for the product
     * @return the product
     */
    public Product getProduct() {
        return product;
    }
    /** setter method for the product
     * @param product the new product
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    /** getter method for the quantity
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /** setter method for the quantity
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /** getter method for the total price
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }
    /** setter method for the total price
     * @param totalPrice the new total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    /** getter method for the date
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }
    /** setter method for the delivery date
     * @return the delivery date
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Updates the order
     * If the order has been delivered or the last check was today then nothing happens
     * If the order has not been delivered and the last check was not today then the order is updated
     */
    public void updateOrder() {
        int quantityThisCheck;
        if (delivered || lastCheck.isEqual(LocalDate.now()))
            return;
        if(seller.getQuantity()>0 || !isDelivered()) {
            if (deliveryDate.isAfter(LocalDate.now())) {
                if (quantity < deliveredQuantity + seller.getQuantity()) {
                    quantityThisCheck = quantity - deliveredQuantity;
                    seller.setQuantity(seller.getQuantity() - quantityThisCheck);
                    deliveredQuantity = quantity;
                    buyer.addToProductQuantity(product, quantityThisCheck);
                    delivered = true;
                } else {
                    deliveredQuantity += seller.getQuantity();
                    buyer.addToProductQuantity(product, seller.getQuantity());
                    seller.setQuantity(0);
                }
            } else if (deliveryDate.isEqual(LocalDate.now())) {
                daysToDeliver = 0;
                delivered = true;
            } else if (deliveryDate.isBefore(LocalDate.now())) {
                if (quantity > deliveredQuantity) {
                    quantityThisCheck = quantity - deliveredQuantity;
                    seller.setQuantity(seller.getQuantity() - quantityThisCheck);
                    deliveredQuantity = quantity;
                    buyer.addToProductQuantity(product, quantityThisCheck);
                    delivered = true;
                }
                if (quantity <= deliveredQuantity)
                    delivered = true;
            }
        }
        lastCheck = LocalDate.now();
        daysToDeliver = (quantity - deliveredQuantity) / seller.getDailyProduction() + 1;
    }
    /** getter method for the delivered quantity
     * @return the delivered quantity
     */
    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }
    /** setter method for the delivered quantity
     * @param deliveredQuantity the new delivered quantity
     */

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }
    /** getter method if the order has been delivered
     * @return whether the order has been delivered or not
     */
    public boolean isDelivered() {
        return delivered;
    }
    /** setter method for the order has been delivered
     * @param delivered whether the order has been delivered or not
     */

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    /** getter method for the last check
     * @return the last check
     */
    public LocalDate getLastCheck() {
        return lastCheck;
    }
    /** setter method for the last check
     * @param lastCheck the new last check
     */
    public void setLastCheck(LocalDate lastCheck) {
        this.lastCheck = lastCheck;
    }
    /** getter method for the days to deliver
     * @return the days to deliver
     */
    public int getDaysToDeliver() {
        return daysToDeliver;
    }
}
