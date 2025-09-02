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
        try (FileOutputStream fos = new FileOutputStream("./tasks.bongo");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
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
            return (TaskList) ois.readObject();
        } catch (FileNotFoundException e) {
            return new TaskList();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            // TODO: Handle corrupted file (delete)?
            return new TaskList();
        }
    }
}