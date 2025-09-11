package bongo;

import bongo.task.TaskList;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The Io class handles the saving and loading of the task list to/from a file.
 * It uses serialization to write and read the TaskList object to the filesystem.
 */
public class Io {

    /**
     * Saves the provided TaskList to a file using serialization.
     * The task list is stored in the file "./tasks.bongo".
     *
     * @param tasks The TaskList to be saved.
     * @throws Bongo.BongoException If an I/O error occurs during saving.
     */
    public static void saveTaskList(TaskList tasks) throws Bongo.BongoException {
        assert tasks != null : "The task list to be saved cannot be null";

        try (FileOutputStream fos = new FileOutputStream("./tasks.bongo");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            assert new java.io.File("./tasks.bongo").canWrite() : "Cannot write to the tasks file";

            oos.writeObject(tasks);
        } catch (IOException e) {
            // TODO: split into FileNotFound and IO error cases.
            throw new Bongo.BongoException(e.getMessage());
        }
    }

    /**
     * Loads a TaskList from the file "./tasks.bongo".
     * If the file does not exist or an error occurs during loading, an empty TaskList is returned.
     *
     * @return The loaded TaskList, or a new empty TaskList if loading fails.
     */
    public static TaskList loadTaskList() {
        try (FileInputStream fis = new FileInputStream("./tasks.bongo");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object loadedTaskList = ois.readObject();

            assert loadedTaskList != null : "Loaded task list is null after deserialization";
            assert loadedTaskList instanceof TaskList : "Deserialized object is not of type TaskList";

            return (TaskList) loadedTaskList;
        } catch (FileNotFoundException e) {
            return new TaskList();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            // TODO: Handle corrupted file (delete)?
            return new TaskList();
        }
    }
}
