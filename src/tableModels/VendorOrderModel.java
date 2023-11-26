package tableModels;

import Commerce.Order;
import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is a table model for the order history table.
 * It is used to display the order history of the user.
 */
public class VendorOrderModel extends AbstractTableModel{
/**The list of orders.*/
    List<Order> orders = new ArrayList<Order>();

    /**
     * This method returns the number of rows in the table.
     * @return the number of rows in the table
     */
    @Override
    public int getRowCount() {
        return orders.size();
    }
    /**
     * This method returns the number of columns in the table.
     * @return the number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return 8;
    }

    /**
     * This method returns the value of the cell at the given row and column.
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return the value Object at the specified cell
     */
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

    /**
     * This method returns the class of the column at the given index.
     * @param columnIndex  the column being queried
     * @return  the class of the column at columnIndex
     */
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
    /**
     * This method returns the name of the column at the given index.
     * @param column     the column whose value is to be queried
     * @return the name of the column
     */
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

    /**
     * This method adds an order to the table.
     * @param order the order to be added
     */
    public void addOrder(Order order) {
        if(orders.contains(order))
            return;
        orders.add(order);
        fireTableRowsInserted(orders.size() - 1, orders.size() - 1);
    }
}
