package com.example.demoandroidsqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    // CREATE (Insert data)
    public int insertBook(Book book) {
        int generatedId = -1;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();
        String query = "INSERT INTO Books (Name, Description, Price, IsSold) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getDescription());
            stmt.setInt(3, book.getPrice());
            stmt.setBoolean(4, book.isSold());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    generatedId = rs.getInt(1);
                }
                rs.close();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        }
        return generatedId;
    }

    // READ (Get books data)
    public List<Book> getBooks() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();
        List<Book> listBooks = new ArrayList<>();
        String query = "SELECT * FROM Books";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int price = rs.getInt("Price");
                boolean isSold = rs.getBoolean("IsSold");
                Book bookAdded = new Book(id, name, description, price, isSold);
                listBooks.add(bookAdded);
            }
            rs.close();
            connection.close();
            return listBooks;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e);
        }
    }

    // UPDATE (Modify book data)
    public void updateBook(Book bookUpdated) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();
        String query = "UPDATE Books SET Name = ?, Description = ?, Price = ?, IsSold = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, bookUpdated.getName());
            stmt.setString(2, bookUpdated.getDescription());
            stmt.setInt(3, bookUpdated.getPrice());
            stmt.setBoolean(4, bookUpdated.isSold());
            stmt.setInt(5, bookUpdated.getId());
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e);
        }
    }

    // DELETE (Remove a user)
    public void deleteBook(int id) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.CONN();
        String query = "DELETE FROM Books WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e);
        }
    }
}
