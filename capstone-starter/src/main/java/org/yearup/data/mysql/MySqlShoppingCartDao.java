package org.yearup.data.mysql;

import com.mysql.cj.protocol.Resultset;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    // super constructor
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    // methods
    @Override
    public ShoppingCart getByUserId(int userId) {

        ShoppingCart shopCart = new ShoppingCart();

        String query = """
                SELECT *
                FROM shopping_cart sc
                JOIN Product prod ON sc.productId = prod.productId
                WHERE sc.userId = ?
                """;

        try (Connection connection = getConnection()) {
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userId);

            ResultSet rs = prepStatement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                int quantity = rs.getInt("quantity");

                Product product = new Product(
                        productId,
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("category_id"),
                        rs.getString("description"),
                        rs.getString("color"),
                        rs.getInt("stock"),
                        rs.getBoolean("featured"),
                        rs.getString("image_url")
                );

                ShoppingCartItem item = new ShoppingCartItem(product, quantity);
                shopCart.add(item);

            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return shopCart;
    }

    @Override
    public void addProduct(int userId, int productId, int quantity) {
        // if product is in cart already, update quantity
        // if product not in cart, insert new item starting from 1 for quantity

        // checking if product is already in user cart
        String selectSql = "SELECT quantity FROM shopping_cart WHERE userId = ? AND productId = ?";
        // query to insert a new product to cart
        String insertSql = "INSERT INTO shopping_cart (userId, productId, quantity) VALUES (?, ?, ?)";
        // query to update quantity of existing product in cart
        String updateSql = "UPDATE shopping_cart SET quantity = quantity + ? WHERE userId = ? AND productId = ?";

        try (Connection connection = getConnection()) {
            // try block - prepare and execute the select statement to check for existing product
            PreparedStatement selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, productId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // if product already in cart, update the quantity by adding the new amount
                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, productId);
                updateStmt.executeUpdate();
            } else {
                // if product not in cart, insert it with the specified quantity
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, productId);
                insertStmt.setInt(3, quantity);
                insertStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not add product to cart.");
        }
    }

    @Override
    public void update(int userId, int productId, int quantity) {

        // update cart
        String updateSql = "UPDATE shopping_cart SET quantity = ? WHERE userId = ? AND productId = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(updateSql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update cart item.");
        }

    }

    @Override
    public void delete(int id) {

        // delete items

        String deleteSql = "DELETE FROM shopping_cart WHERE productId = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(deleteSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete cart item.");
        }
    }
}
