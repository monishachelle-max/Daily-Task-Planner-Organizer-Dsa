import java.util.*;

// ================= TASK CLASS =================
class Task {
    int id;
    String name;
    int priority;
    String deadline;
    String status;
    Task next;   // for Linked List

    Task(int id, String name, int priority, String deadline) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.status = "Pending";
        this.next = null;
    }
}

// ================= LINKED LIST =================
class TaskList {
    Task head = null;

    // Insert at end
    void addTask(Task t) {
        if (head == null) {
            head = t;
        } else {
            Task temp = head;
            //Traverse to last node
            while (temp.next != null)
                temp = temp.next;
            temp.next = t;
        }
        System.out.println("Task added successfully.");
    }

    // Linear Search by ID
    Task searchById(int id) {
        Task temp = head;
        while (temp != null) {
            if (temp.id == id)
                return temp;
            temp = temp.next;
        }
        return null;
    }

    // Delete by ID
    void deleteTask(int id) {
        if (head == null) return;

        if (head.id == id) {
            head = head.next;
            System.out.println("Task removed.");
            return;
        }

        Task prev = head;
        Task curr = head.next;

        while (curr != null) {
            if (curr.id == id) {
                prev.next = curr.next;
                System.out.println("Task removed.");
                return;
            }
            prev = curr;
            curr = curr.next;
        }
        System.out.println("Task not found.");
    }

    // Display
    void display() {
        if (head == null) {
            System.out.println("No tasks.");
            return;
        }

        Task temp = head;
        while (temp != null) {
            System.out.println("--------------------");
            System.out.println("ID: " + temp.id);
            System.out.println("Title: " + temp.name);
            System.out.println("Priority: " + temp.priority);
            System.out.println("Deadline: " + temp.deadline);
            System.out.println("Status: " + temp.status);
            temp = temp.next;
        }
    }
}

// ================= STACK USING LINKED LIST =================
class TaskStack {
    Task top = null;

    void push(Task t) {
        t.next = top;
        top = t;
    }

    Task pop() {
        if (top == null) return null;
        Task temp = top;
        top = top.next;
        return temp;
    }

    boolean isEmpty() {
        return top == null;
    }
}

// ================= QUEUE USING LINKED LIST =================
class TaskQueue {
    Task front = null, rear = null;

    void enqueue(Task t) {
        t.next = null;
        if (rear == null) {
            front = rear = t;
        } else {
            rear.next = t;
            rear = t;
        }
    }

    Task dequeue() {
        if (front == null) return null;
        Task temp = front;
        front = front.next;
        if (front == null)
            rear = null;
        return temp;
    }
}
// ================= HASH TABLE (CO4 FEATURE) =================
class TaskHashTable {

    Task[] table = new Task[50];

    int hash(int id) {
        return id % table.length;
    }

    // Insert using Linear Probing
    void insert(Task t) {
        int index = hash(t.id);

        while (table[index] != null) {
            index = (index + 1) % table.length;
        }

        table[index] = t;
    }

    // Search using Hashing
    Task search(int id) {
        int index = hash(id);
        int start = index;

        while (table[index] != null) {

            if (table[index].id == id)
                return table[index];

            index = (index + 1) % table.length;

            if (index == start)
                break;
        }

        return null;
    }

// NEW METHOD: Update status in hash table
   void updateStatus(int id, String newStatus) {
        int index = hash(id);
        int start = index;

        while (table[index] != null) {
            if (table[index].id == id) {
                table[index].status = newStatus;
                //System.out.println("Hash table status updated to: " + newStatus);
                return;
            }
            index = (index + 1) % table.length;
            if (index == start) break;
        }
        //System.out.println("Task not found in hash table!");
    }
}


// ================= MAIN =================
public class Planit {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TaskList list = new TaskList();
        TaskStack stack = new TaskStack();
        TaskQueue queue = new TaskQueue();
        TaskHashTable hash = new TaskHashTable(); // Hashing

        while (true) {

            System.out.println("\n==== DAILY TASK PLANNER AND ORGANIZER====");
            System.out.println("1. Add Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Search Task");
            System.out.println("4. View Schedule");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. Undo Last Completion");
            System.out.println("7. Exit");
            System.out.print( "Choose your option:");
            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Priority: ");
                    int pr = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Deadline: ");
                    String dl = sc.nextLine();

                   // Task newTask = new Task(id, name, pr, dl);
                    Task taskForList = new Task(id, name, pr, dl);
                    Task taskForQueue = new Task(id, name, pr, dl);
                    Task taskForHash = new Task(id, name, pr, dl);
                    list.addTask(taskForList);
                    queue.enqueue(taskForQueue);
                    hash.insert(taskForHash);   // hashing insert
                    break;

                case 2:
                    System.out.print("Enter ID: ");
                    int deleteId = sc.nextInt();
                    list.deleteTask(deleteId);
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int searchId = sc.nextInt();
                    Task found = list.searchById(searchId);
                    if (found != null){
                        System.out.println("Task Found: " + found.name);
                        System.out.println("Priority: " + found.priority);
                        System.out.println("Deadline: " + found.deadline);
                        System.out.println("Status: " + found.status);
                    // Also search in Hash Table for demonstration
                        Task hashFound = hash.search(searchId);
                        if (hashFound != null) {
                            //System.out.println("   ✅ Also found in Hash Table");
                        }
                    } else {
                        System.out.println("❌ Task Not Found");
                    }
                    break;
                case 4:
                    System.out.println("\n📋 CURRENT SCHEDULE:");
                    list.display();
                    break;

                case 5:
                    System.out.print("Enter ID: ");
                    int completeId = sc.nextInt();
                    Task taskToComplete = list.searchById(completeId);
                   if (taskToComplete != null) {
                        // Update status in Linked List
                        taskToComplete.status = "Completed";
                        
                        // Update status in Hash Table
                        hash.updateStatus(completeId, "Completed");
                        
                        // Create a copy for stack (so stack operations don't affect list)
                        Task taskForStack = new Task(
                            taskToComplete.id, 
                            taskToComplete.name, 
                            taskToComplete.priority, 
                            taskToComplete.deadline
                        );
                        taskForStack.status = "Completed";
                        
                        // Push to stack for undo
                        stack.push(taskForStack);
                        System.out.println("✅ Task Marked as Completed!");
                        System.out.println("Status: Completed ← Status changed");
                    } else {
                        System.out.println("❌Task not found!");
                    }
                    break;
                case 6:
                     if (!stack.isEmpty()) {
                        Task undone = stack.pop();
                        
                        // Update status in Linked List
                        Task taskInList = list.searchById(undone.id);
                        if (taskInList != null) {
                            taskInList.status = "Pending";
                        }
                        
                        // Update status in Hash Table
                        hash.updateStatus(undone.id, "Pending");
                        System.out.println("✅ Undo Successful!");
                        System.out.println("   Task ID " + undone.id + " reverted to Pending");
                    } else {
                        System.out.println("⚠️ Nothing to undo!");
                    }
                    break;
                case 7:
                    System.out.println("\n👋 Exiting... Thank you!");
                    System.exit(0);
                    
                default:
                    System.out.println("⚠️ Invalid option! Please choose 1-7.");
                }
            }

    }
}