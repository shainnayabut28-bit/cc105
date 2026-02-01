import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Task {
    int id;
    String name;
    String status;

    Task(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}

public class Main {
    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int nextTaskId = 1;
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {
        loadTasks();

        int choice;
        do {
            System.out.println("\n=== TO DO LIST ===");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> System.out.println("Program ended.");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    static void addTask() {
        sc.nextLine();
        System.out.print("Enter task name: ");
        String name = sc.nextLine();

        Task newTask = new Task(nextTaskId++, name, "Pending");
        tasks.add(newTask);
        saveTasks();

        System.out.println("Task added successfully!");
    }

    static void viewTasks() {
        System.out.println("\n--- TASK LIST ---");

        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (Task t : tasks) {
            System.out.println("ID: " + t.id);
            System.out.println("Task: " + t.name);
            System.out.println("Status: " + t.status);
            System.out.println("----------------");
        }
    }

    static void updateTask() {
        System.out.print("Enter task ID to update: ");
        int searchId = sc.nextInt();

        for (Task t : tasks) {
            if (t.id == searchId) {
                sc.nextLine();
                System.out.print("New task name: ");
                t.name = sc.nextLine();
                System.out.print("Status (Pending/Done): ");
                t.status = sc.nextLine();

                saveTasks();
                System.out.println("Task updated!");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    static void deleteTask() {
        System.out.print("Enter task ID to delete: ");
        int searchId = sc.nextInt();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id == searchId) {
                tasks.remove(i);
                saveTasks();
                System.out.println("Task deleted!");
                return;
            }
        }
        System.out.println("Task not found.");
    }


    static void saveTasks() {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (Task t : tasks) {
                fw.write(t.id + "," + t.name + "," + t.status + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    static void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String status = data[2];

                tasks.add(new Task(id, name, status));
                nextTaskId = Math.max(nextTaskId, id + 1);
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks.");
        }
    }
}
