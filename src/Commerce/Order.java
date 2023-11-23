package Commerce;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    Vendor buyer;
    Producer seller;
    Product product;
    int quantity;
    double totalPrice;
    Date date;
    int daysToDeliver;

    public Order(Vendor buyer, Producer seller, Product product, int quantity) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = quantity * seller.getPrice();
        this.date = new Date();
        if(quantity<=seller.getQuantity()){
            seller.setQuantity(seller.getQuantity()-quantity);
            daysToDeliver = 0;
        }
        else{
            daysToDeliver = seller.getQuantity()-quantity;
            daysToDeliver = daysToDeliver/seller.getDailyProduction()+1;
            seller.setQuantity(0);
        }
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

    public void setSeller(Producer seller) {
        this.seller = seller;
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

    public Date getDate() {
        return date;
    }

}
