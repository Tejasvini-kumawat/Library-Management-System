// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementApp {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        BorrowingDAO borrowingDAO = new BorrowingDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View All Books");
            System.out.println("6. View All Members");
            System.out.println("7. View Borrowing Transactions");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (option) {
                    case 1:
                        System.out.print("Enter ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        bookDAO.addBook(new Book(isbn, title, author, true));
                        System.out.println("Book added successfully.");
                        break;

                    case 2:
                        System.out.print("Enter Member ID: ");
                        String memberId = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Contact Info: ");
                        String contactInfo = scanner.nextLine();
                        memberDAO.addMember(new Member(memberId, name, contactInfo));
                        System.out.println("Member added successfully.");
                        break;

                    case 3:
                        System.out.print("Enter ISBN: ");
                        String borrowIsbn = scanner.nextLine();
                        System.out.print("Enter Member ID: ");
                        String borrowMemberId = scanner.nextLine();
                        Book book = bookDAO.getBookByISBN(borrowIsbn);
                        Member member = memberDAO.getMemberById(borrowMemberId);
                        if (book != null && member != null && book.isAvailable()) {
                            borrowingDAO.addBorrowing(new Borrowing(0, book, member, new java.util.Date()));
                            book.setAvailable(false);  // Mark the book as borrowed
                            bookDAO.updateBook(book);
                            System.out.println("Book borrowed successfully.");
                        } else {
                            System.out.println("Invalid ISBN, Member ID or Book not available.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Transaction ID: ");
                        int transactionId = scanner.nextInt();
                        borrowingDAO.returnBook(transactionId);
                        System.out.println("Book returned successfully.");
                        break;

                    case 5:
                        List<Book> books = bookDAO.getAllBooks();
                        for (Book b : books) {
                            System.out.println(b);
                        }
                        break;

                    case 6:
                        List<Member> members = memberDAO.getAllMembers();
                        for (Member m : members) {
                            System.out.println(m);
                        }
                        break;

                    case 7:
                        List<Borrowing> borrowings = borrowingDAO.getAllBorrowings();
                        for (Borrowing b : borrowings) {
                            System.out.println(b);
                        }
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}