package tableModels;

import Commerce.Producer;
import Commerce.Vendor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * This class is a table model for the vendor table.
 */
public class ProducerTableModel extends AbstractTableModel{
    /**The list of vendors. */
    List<Producer> producers;

    /**
     * This is the constructor of the class.
     * @param producers The list of vendors.
     */
    public ProducerTableModel(List<Producer> producers){
        this.producers = producers;
    }

    /**
     * This method returns the number of rows in the table.
     * @return the number of rows in the table
     */
    @Override
    public int getRowCount() {
        return producers.size();
    }

    /**
     * This method returns the number of columns in the table.
     * @return the number of columns in the table
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

    /**
     * This method returns the name of the column at the given index.
     * @param column  the column being queried
     * @return the name of the column
     */
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

    /**
     * This method returns the class of the column at the given index.
     * @param columnIndex  the column being queried
     * @return the class of the column at columnIndex
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2 -> String.class;
            case 3, 4 -> Integer.class;
            case 5 -> Double.class;
            default -> null;
        };
    }

    /**
     * This method adds a vendor to the table.
     * @param rowIndex  the row being queried
     * @param columnIndex the column being queried
     * @return true if the cell is editable
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1|| columnIndex == 2|| columnIndex == 3|| columnIndex == 4|| columnIndex == 5;
    }

    /**
     *  This method sets the value of the cell at the given row and column.
     * @param value   value to assign to cell
     * @param rowIndex   row of cell
     * @param columnIndex  column of cell
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (value instanceof String) {
                producers.get(rowIndex).setName((String) value);
            }
        } else if (columnIndex == 1) {
            if (value instanceof String) {
                producers.get(rowIndex).setUsername((String) value);
            }
        }else if (columnIndex == 2) {
            if (value instanceof String) {
                producers.get(rowIndex).setPassword((String) value);
            }
        }else if (columnIndex == 3) {
            if (value instanceof Integer) {
                producers.get(rowIndex).setQuantity((Integer) value);
            }
        }
        else if (columnIndex == 4) {
            if (value instanceof Integer) {
                producers.get(rowIndex).setDailyProduction((Integer) value);
            }
        }
        else if (columnIndex == 5) {
            if (value instanceof Double) {
                producers.get(rowIndex).setPrice((Double) value);
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     *  This method removes a producer from the table.
     * @param name  name of producer to remove
     * @return removed producer
     */
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