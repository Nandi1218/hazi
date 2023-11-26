package tableModels;

import Commerce.Order;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a table model for the order history table.
 */
public class OrderHistoryTableModel extends AbstractTableModel{
        /**The list of orders.*/
        List<Order> orders = new ArrayList<Order>();

    /**
     * This method returns the number of rows in the table.
     * @return
     */
        @Override
        public int getRowCount() {
            return orders.size();
        }

    /**
     * This method returns the number of columns in the table.
     * @return
     */
        @Override
        public int getColumnCount() {
            return 6;
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
                case 0 -> order.getBuyer().getName();
                case 1 -> order.getSeller().getName();
                case 2 -> order.getProduct().getName();
                case 3 -> order.getQuantity();
                case 4 -> order.getTotalPrice();
                case 5 -> order.getDate();
                default -> null;
            };
        }

    /**
     *  This method returns the name of the column at the given index.
     * @param column  the column being queried
     * @return the name of the column
     */
        @Override
        public String getColumnName(int column) {
            return switch (column) {
                case 0 -> "Buyer";
                case 1 -> "Seller";
                case 2 -> "Product";
                case 3 -> "Quantity";
                case 4 -> "Total Price";
                case 5 -> "Date";
                default -> null;
            };
        }

    /**
     *  This method returns the class of the column at the given index.
     * @param columnIndex  the column being queried
     * @return
     */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0, 1, 2 -> String.class;
                case 3 -> Integer.class;
                case 4 -> Double.class;
                case 5 -> LocalDate.class;
                default -> null;
            };
        }

    /**
     * This method adds an order to the list of orders.
     * @param order   the order to be added
     */
        public void addOrder(Order order){
            if(orders.contains(order))
                return;
            orders.add(order);
            fireTableRowsInserted(orders.size()-1, orders.size() -1);
            System.out.println("Added "+orders.size());
        }

}
