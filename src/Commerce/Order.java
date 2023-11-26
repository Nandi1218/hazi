package Commerce;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {
    Vendor buyer;
    Producer seller;
    Product product;
    int quantity;
    int deliveredQuantity = 0;
    double totalPrice;
    LocalDate date;
    LocalDate lastCheck;
    LocalDate deliveryDate;
    int daysToDeliver;
    boolean delivered = false;

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

    public Vendor getBuyer() {
        return buyer;
    }

    public void setBuyer(Vendor buyer) {
        this.buyer = buyer;
    }

    public Producer getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

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

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public LocalDate getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(LocalDate lastCheck) {
        this.lastCheck = lastCheck;
    }

    public int getDaysToDeliver() {
        return daysToDeliver;
    }
}
