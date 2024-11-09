import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // Add a new member
    public void addMember(Member member) throws SQLException {
        String query = "INSERT INTO members (member_id, name, contact_info) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, member.getMemberId());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getContactInfo());
            stmt.executeUpdate();
        }
    }

    // Update member details
    public void updateMember(Member member) throws SQLException {
        String query = "UPDATE members SET name = ?, contact_info = ? WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getContactInfo());
            stmt.setString(3, member.getMemberId());
            stmt.executeUpdate();
        }
    }

    // Delete a member from the database
    public void deleteMember(String memberId) throws SQLException {
        String query = "DELETE FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, memberId);
            stmt.executeUpdate();
        }
    }

    // Retrieve all members
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM members";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Member member = new Member(
                        rs.getString("member_id"),
                        rs.getString("name"),
                        rs.getString("contact_info")
                );
                members.add(member);
            }
        }
        return members;
    }

    // Get member by ID
    public Member getMemberById(String memberId) throws SQLException {
        String query = "SELECT * FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, memberId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getString("member_id"),
                            rs.getString("name"),
                            rs.getString("contact_info")
                    );
                }
            }
        }
        return null;
    }
}