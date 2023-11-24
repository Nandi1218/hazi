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
    public void save(ArrayList<T> list) {
        if(list.isEmpty()) return;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/Serialize/" + list.get(0).getClass().getSimpleName() + ".ser"))) {
            oos.writeObject(list);
            System.out.println("Successfully saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
