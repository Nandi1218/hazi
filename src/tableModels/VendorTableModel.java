package tableModels;

import Commerce.Vendor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VendorTableModel extends AbstractTableModel{

    List<Vendor> vendors;
    public VendorTableModel(List<Vendor> vendors){
        this.vendors = vendors;
    }

    @Override
    public int getRowCount() {
        return vendors.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 2, 3 -> String.class;
            case 1 -> Double.class;
            default -> null;
        };
    }
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