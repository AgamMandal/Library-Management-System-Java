import java.io.*;
import java.util.*;

// Book class
class Book {
    String id;
    String title;
    String author;
    boolean isIssued;

    Book(String id, String title, String author, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
    }

    // Convert object to string for file storage
    public String toFileString() {
        return id + "," + title + "," + author + "," + isIssued;
    }
}

public class LibrarySystem {

    static final String FILE_NAME = "library.txt";

    // 🔹 Load books from file
    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();

        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return books;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                books.add(new Book(
                        data[0],
                        data[1],
                        data[2],
                        Boolean.parseBoolean(data[3])
                ));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading books.");
        }

        return books;
    }

    // 🔹 Save books to file
    public static void saveBooks(ArrayList<Book> books) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Book b : books) {
                bw.write(b.toFileString());
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving books.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Book> books = loadBooks();

        while (true) {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // 🔹 Add Book
                case 1:
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();

                    books.add(new Book(id, title, author, false));
                    saveBooks(books);

                    System.out.println("Book added!");
                    break;

                // 🔹 View Books
                case 2:
                    for (Book b : books) {
                        System.out.println(
                                b.id + " | " +
                                b.title + " | " +
                                b.author + " | " +
                                (b.isIssued ? "Issued" : "Available")
                        );
                    }
                    break;

                // 🔹 Issue Book
                case 3:
                    System.out.print("Enter Book ID: ");
                    String issueId = sc.nextLine();

                    boolean foundIssue = false;

                    for (Book b : books) {
                        if (b.id.equals(issueId)) {
                            if (!b.isIssued) {
                                b.isIssued = true;
                                System.out.println("Book issued!");
                            } else {
                                System.out.println("Already issued!");
                            }
                            foundIssue = true;
                            break;
                        }
                    }

                    if (!foundIssue) {
                        System.out.println("Book not found!");
                    }

                    saveBooks(books);
                    break;

                // 🔹 Return Book
                case 4:
                    System.out.print("Enter Book ID: ");
                    String returnId = sc.nextLine();

                    boolean foundReturn = false;

                    for (Book b : books) {
                        if (b.id.equals(returnId)) {
                            if (b.isIssued) {
                                b.isIssued = false;
                                System.out.println("Book returned!");
                            } else {
                                System.out.println("Book was not issued!");
                            }
                            foundReturn = true;
                            break;
                        }
                    }

                    if (!foundReturn) {
                        System.out.println("Book not found!");
                    }

                    saveBooks(books);
                    break;

                // 🔹 Search Book
                case 5:
                    System.out.print("Enter title to search: ");
                    String search = sc.nextLine().toLowerCase();

                    for (Book b : books) {
                        if (b.title.toLowerCase().contains(search)) {
                            System.out.println(
                                    b.id + " | " +
                                    b.title + " | " +
                                    b.author + " | " +
                                    (b.isIssued ? "Issued" : "Available")
                            );
                        }
                    }
                    break;

                // 🔹 Exit
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}