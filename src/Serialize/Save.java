package Serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @param <T>
 */
public class Save<T> {
    /**
     * @param list
     */
    public void save(ArrayList<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/Serialize/" + list.get(0).getClass().getSimpleName() + ".ser"))) {
            oos.writeObject(list);
            System.out.println("Successfully saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
