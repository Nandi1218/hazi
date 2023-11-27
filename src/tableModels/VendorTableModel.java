package tableModels;

import Commerce.Vendor;

import javax.swing.table.AbstractTableModel;
import java.util.List;
/**
 * This class is a table model for the vendor table.
 * It is used to display the vendors in a table.
 */
public class VendorTableModel extends AbstractTableModel{
    /**The list of vendors.*/
    List<Vendor> vendors;
    /**
     * This is the constructor of the class.
     * @param vendors The list of vendors.
     */
    public VendorTableModel(List<Vendor> vendors){
        this.vendors = vendors;
    }

    /**
     * This method returns the number of rows in the table.
     * @return the number of rows in the table
     */
    @Override
    public int getRowCount() {
        return vendors.size();
    }

    /**
     * This method returns the number of columns in the table.
     * @return the number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * This method returns the value of the cell at the given row and column.
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vendor vendor = vendors.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> vendor.getName();
            case 1 -> vendor.getMoney();
            case 2 -> vendor.getUsername();
            case 3 -> vendor.getPassword();
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
            case 0 -> "Name";
            case 1 -> "Money";
            case 2 -> "Username";
            case 3 -> "Password";
            default -> null;
        };
    }
    /**
     * This method returns the class of the column at the given index.
     * @param columnIndex     the column whose value is to be queried
     * @return the class of the column
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1|| columnIndex == 2|| columnIndex == 3;
    }
    /**
     * This method sets the value of the cell at the given row and column.
     * @param value        the new value
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (value instanceof String) {
                vendors.get(rowIndex).setName((String) value);
            }
        } else if (columnIndex == 1) {
            if (value instanceof Double) {
                vendors.get(rowIndex).setMoney((Double) value);
            }
        }else if (columnIndex == 2) {
            if (value instanceof String) {
                vendors.get(rowIndex).setUsername((String) value);
            }
        }else if (columnIndex == 3) {
            if (value instanceof String) {
                vendors.get(rowIndex).setPassword((String) value);
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    /**
     * This method returns the class of the column at the given index.
     * @param columnIndex     the column whose value is to be queried
     * @return the class of the column
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 2, 3 -> String.class;
            case 1 -> Double.class;
            default -> null;
        };
    }

    /**
     * This method removes the vendor with the given name from the list of vendors.
     * @param name The name of the vendor to be removed.
     * @return The removed vendor.
     */
    public Vendor removeVendor(String name){
        Vendor removedVendor = null;
        for (Vendor vendor : vendors) {
            if (vendor.getName().equals(name)) {
                removedVendor = vendor;
                break;
            }
        }
        if(removedVendor != null){
            vendors.remove(removedVendor);
            fireTableRowsDeleted(vendors.size(), vendors.size());
            return removedVendor;
        }
        return null;
    }

}