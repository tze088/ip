import java.io.*;
import java.util.ArrayList;

public class IO {
    public static void saveTaskList(ArrayList<Task> tasks) throws Bongo.BongoException {
        try (FileOutputStream fos = new FileOutputStream("./tasks.bongo");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            // TODO: split into FileNotFound and IO error cases.
            throw new Bongo.BongoException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")  // ClassCastException is caught
    public static ArrayList<Task> loadTaskList() {
        try (FileInputStream fis = new FileInputStream("./tasks.bongo");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (ArrayList<Task>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            // TODO: Handle corrupted file (delete)?
            return new ArrayList<>();
        }
    }
}