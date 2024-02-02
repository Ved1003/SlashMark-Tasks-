import java.util.ArrayList;
import java.util.Scanner;

public class TaskListApplication {

    private static ArrayList<String> taskList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    listTasks();
                    break;
                case 4:
                    System.out.println("Exiting the Task List Application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== Task List Application =====");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. List Tasks");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }

    private static void addTask() {
        System.out.print("Enter the task: ");
        scanner.nextLine(); // Consume the newline character
        String task = scanner.nextLine();
        taskList.add(task);
        System.out.println("Task added successfully!");
    }

    private static void removeTask() {
        if (taskList.isEmpty()) {
            System.out.println("Task list is empty. No task to remove.");
        } else {
            System.out.print("Enter the task number to remove: ");
            int taskNumber = getUserChoice();

            if (taskNumber >= 1 && taskNumber <= taskList.size()) {
                String removedTask = taskList.remove(taskNumber - 1);
                System.out.println("Task '" + removedTask + "' removed successfully!");
            } else {
                System.out.println("Invalid task number. Please enter a valid task number.");
            }
        }
    }

    private static void listTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Task list is empty.");
        } else {
            System.out.println("===== Task List =====");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i));
            }
        }
    }
}
