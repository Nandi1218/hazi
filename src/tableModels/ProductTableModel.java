package tableModels;

import Commerce.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * This class is a table model for the product table.
 */
public class ProductTableModel extends AbstractTableModel{
    /**The list of products.*/
    List<Product> products;
    /**
     * This is the constructor of the class.
     * @param products The list of products.
     */
    public ProductTableModel(List<Product> products){
        this.products = products;
    }

    /**
     * This method returns the number of rows in the table.
     * @return the number of rows in the table
     */
    @Override
    public int getRowCount() {
        return products.size();
    }

    /**
     *  This method returns the number of columns in the table.
     * @return the number of columns in the table
     */

    @Override
    public int getColumnCount() {
        return 3;
    }
    /**
     * This method returns the value of the cell at the given row and column.
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> product.getName();
            case 1 -> product.getDescription();
            case 2 -> product.getId();
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
            case 1 -> "Description";
            case 2 -> "ID";
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
            case 0, 1 -> String.class;
            case 2 -> Integer.class;
            default -> null;
        };
    }

    /**
     * This method returns whether the cell at rowIndex and columnIndex is editable.
     * @param rowIndex  the row being queried
     * @param columnIndex the column being queried
     * @return true if the cell is editable
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1;
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
                products.get(rowIndex).setName((String) value);
            }
        } else if (columnIndex == 1) {
            if (value instanceof String) {
                products.get(rowIndex).setDescription((String) value);
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * This method refreshes the table.
     * It is used when a new product is added.
     */
    public void refresh(){
        fireTableRowsInserted(products.size()-1, products.size()-1);
    }

    /**
     * This method removes a product from the table.
     * @param name The name of the product to be removed.
     */
    public void removeProduct(String name){
        Product removedProduct = null;
        for (Product product : products) {
            if (product.getName().equals(name)) {
                removedProduct = product;
                break;
            }
        }
        if(removedProduct == null){
            return;
        }
        products.remove(removedProduct);
        fireTableRowsDeleted(products.size(), products.size());
    }

}
