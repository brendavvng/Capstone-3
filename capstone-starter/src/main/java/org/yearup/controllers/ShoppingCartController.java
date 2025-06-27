package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;
import java.util.Map;

// convert this class to a REST controller
// only logged in users should have access to these actions

@RestController
@CrossOrigin
@PreAuthorize("permitAll()") // this will apply to all methods
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    // creating constructor
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("/cart")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added

    @PostMapping("/cart/products/{productId}")
    public ShoppingCart addToCart(@PathVariable int productId,
                                  @RequestBody Map<String, Integer> body,
                                  Principal principal) {

        try {
            int quantity = body.getOrDefault("quantity", 1);

            // getting the logged in username
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // adding product with specific quantity
            shoppingCartDao.addProduct(userId, productId, quantity);

            // returns updated shopping cart
            return shoppingCartDao.getByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated

    @PutMapping("/cart/products/{productId}")
    public ShoppingCart updateCart(@PathVariable int productId,
                                   @RequestBody Map<String, Integer> body,
                                   Principal principal) {
        try {
            int quantity = body.get("quantity");

            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // checking if product is in user's cart
            ShoppingCart cart = shoppingCartDao.getByUserId(userId);
            if (!cart.contains(productId)) {
                // if product is not in cart, error will pop up
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not in cart.");
            }

            // updates quantity of product in the cart
            shoppingCartDao.update(userId, productId, quantity);

            // returns updating shopping cart
            return shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("/cart")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProductId(Principal principal) {

        // get the currently logged in username
        String userName = principal.getName();
        // find database user by userId
        User user = userDao.getByUserName(userName);
        int userId = user.getId();

        // deletes all cart items for user
        shoppingCartDao.delete(userId);
    }
}
