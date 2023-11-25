package tableModels;

import Commerce.Order;
import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendorOrderModel extends AbstractTableModel{

    List<Order> orders = new ArrayList<Order>();

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> order.isDelivered();
            case 1 -> order.getSeller().getName();
            case 2 -> order.getProduct().getName();
            case 3 -> order.getQuantity();
            case 4 -> order.getTotalPrice();
            case 5 -> order.getDeliveryDate();
            case 6 -> order.getDaysToDeliver();
            case 7 -> order.getDeliveredQuantity();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Boolean.class;
            case 1, 2 -> String.class;
            case 3,6,7 -> Integer.class;
            case 4 -> Double.class;
            case 5 -> LocalDate.class;
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Delivered";
            case 1 -> "Seller";
            case 2 -> "Product";
            case 3 -> "Quantity";
            case 4 -> "Total Price";
            case 5 -> "Delivery Date";
            case 6 -> "Days to Deliver";
            case 7 -> "Delivered Quantity";
            default -> null;
        };
    }
    public void addOrder(Order order) {
        if(orders.contains(order))
            return;
        orders.add(order);
        fireTableRowsInserted(orders.size() - 1, orders.size() - 1);
    }
}
