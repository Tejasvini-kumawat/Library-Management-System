import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowingDAO {

    // Add a new borrowing transaction
    public void addBorrowing(Borrowing borrowing) throws SQLException {
        String query = "INSERT INTO borrowing_transactions (isbn, member_id, borrow_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, borrowing.getBook().getIsbn());
            stmt.setString(2, borrowing.getMember().getMemberId());
            stmt.setDate(3, new java.sql.Date(borrowing.getBorrowDate().getTime()));
            stmt.executeUpdate();
        }
    }

    // Update return date for borrowing
    public

    void returnBook(int transactionId) throws SQLException {
        String query = "UPDATE borrowing_transactions SET return_date = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(new Date().getTime()));
            stmt.setInt(2, transactionId);
            stmt.executeUpdate();
        }
    }

    // Retrieve all borrowing transactions
    public List<Borrowing> getAllBorrowings() throws SQLException {
        List<Borrowing> borrowings = new ArrayList<>();
        String query = "SELECT * FROM borrowing_transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                BookDAO bookDAO = new BookDAO();
                MemberDAO memberDAO = new MemberDAO();
                Book book = bookDAO.getBookByISBN(rs.getString("isbn"));
                Member member = memberDAO.getMemberById(rs.getString("member_id"));
                Borrowing borrowing = new Borrowing(
                        rs.getInt("transaction_id"),
                        book,
                        member,
                        rs.getDate("borrow_date")
                );
                borrowing.setReturnDate(rs.getDate("return_date"));
                borrowings.add(borrowing);
            }
        }
        return borrowings;
    }
}