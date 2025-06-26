package org.yearup.data.mysql;

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
                FROM ShoppingCartItem sci
                JOIN Product prod ON sci.productId = prod.productId
                WHERE sci.userId = ?
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
    public ShoppingCart addProduct(int userId, int productId) {
        // if product is in cart already, update quantity
        // if product not in cart, insert new item starting from 1 for quantity

        return getByUserId(userId);
    }

    @Override
    public void update(int userId, int productId, int quantity) {

        // update cart
    }

    @Override
    public void delete(int id) {

        // delete items
    }
}
