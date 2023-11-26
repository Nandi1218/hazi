package Serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/** Class that saves the data to a serialized file
 * @param <T> the type of the data to be saved (Vendor, Producer, Product)
 * @see Load
 */
public class Save<T> {
    /** Method that saves the data to a serialized file
     * @param list the list of the data to be saved
     */
    public boolean save(ArrayList<T> list,String filename){
        if(list.isEmpty()) return false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Successfully saved \t\t " + filename +"!");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
