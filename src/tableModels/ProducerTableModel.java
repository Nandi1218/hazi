package tableModels;

import Commerce.Producer;
import Commerce.Vendor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProducerTableModel extends AbstractTableModel{

    List<Producer> producers;
    public ProducerTableModel(List<Producer> producers){
        this.producers = producers;
    }

    @Override
    public int getRowCount() {
        return producers.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producer producer = producers.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> producer.getName();
            case 1 -> producer.getUsername();
            case 2 -> producer.getPassword();
            case 3 -> producer.getQuantity();
            case 4 -> producer.getDailyProduction();
            case 5 -> producer.getPrice();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Name";
            case 1 -> "Username";
            case 2 -> "Password";
            case 3 -> "Quantity";
            case 4 -> "Daily Production";
            case 5 -> "Price";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2 -> String.class;
            case 3, 4 -> Integer.class;
            case 5 -> Double.class;
            default -> null;
        };
    }
    public Producer removeProducer(String name){
        Producer removedProducer = null;
        for (Producer producer : producers) {
            if (producer.getName().equals(name)) {
                removedProducer = producer;
                break;
            }
        }
        if(removedProducer != null){
            producers.remove(removedProducer);
            fireTableRowsDeleted(producers.size(), producers.size());
            return removedProducer;
        }
        return null;
    }

}