package tableModels;

import Commerce.Order;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryTableModel extends AbstractTableModel{

        List<Order> orders = new ArrayList<Order>();

        @Override
        public int getRowCount() {
            return orders.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

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
        public void addOrder(Order order){
            if(orders.contains(order))
                return;
            orders.add(order);
            fireTableRowsInserted(orders.size()-1, orders.size() -1);
            System.out.println("Added "+orders.size());
        }

}
