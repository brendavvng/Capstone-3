package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    // adds product to cart
    ShoppingCart addProduct(int userId, int productId);

    // updates product cart
    void update(int userId, int productId, int quantity);

    // clears product from cart
    void delete(int userId);
}
