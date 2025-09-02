package bongo;

import bongo.task.TaskList;

import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Io {
    public static void saveTaskList(TaskList tasks) throws Bongo.BongoException {
        try (FileOutputStream fos = new FileOutputStream("./tasks.bongo");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            // TODO: split into FileNotFound and IO error cases.
            throw new Bongo.BongoException(e.getMessage());
        }
    }

    // ClassCastException is caught
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