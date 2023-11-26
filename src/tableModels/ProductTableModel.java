package tableModels;

import Commerce.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProductTableModel extends AbstractTableModel{

    List<Product> products;
    public ProductTableModel(List<Product> products){
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

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

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Name";
            case 1 -> "Description";
            case 2 -> "ID";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1 -> String.class;
            case 2 -> Integer.class;
            default -> null;
        };
    }
    public void refresh(){
        fireTableRowsInserted(products.size()-1, products.size()-1);
    }
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
