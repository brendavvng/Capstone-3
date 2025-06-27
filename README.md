# 🛍️ Capstone 3: EasyShop E-Commerce API & Web Application

Welcome to EasyShop, a full-stack e-commerce application project designed for users to browse, search, and purchase products online. This application simulates a real-world online shopping experience and includes both backend API development (using Spring Boot and MySQL) and a frontend user interface (HTML/CSS). ⋆ ˚｡⋆୨🍓୧⋆ ˚｡⋆



## 🌐 Project Overview

✔️ Features Implemented:

- 🔍 Product browsing and searching
- 🗂️ Viewing product details
- 📁 Browsing products by category

This project was built to demonstrate CRUD operations, API consumption using Postman, database integration, and frontend rendering with HTML templates.

⚠️ Note: Some features such as the shopping cart and checkout are under development and not fully functional. The ShoppingCart and ShoppingCartItem models are in place, but viewing and adding to cart currently fails due to unresolved backend issues.


## 🧭 Future Features

| Priority | Feature |
|----------|---------|
| 🔒 High  | Fix shopping cart functionality             |
| 🔐 High  | Add user authentication (login/register)    |
| 💳 Medium| Integrate checkout and payment simulation   |
| 💾 Medium| Implement order history and receipts        |
| 💬 Medium| Enable product reviews and star ratings     |
| 🎁 Medium| Add wishlist/favorites                      |
| 🔄 Low   | Add sorting to product listings             |
| 📱 Low   | Mobile-friendly/responsive design           |

## 🔧 Technical Notes & To-Do
- Debug the cart loading issue (likely related to the cart DAO or database table structure)
- Complete addToCart() and viewCart() endpoints
- Ensure foreign key constraints are properly set up for cart and cart items
- Add exception handling for better error responses
- Validate product quantity before cart updates

## 🛠️ Tech Stack
- Backend: Java, Spring Boot, MySQL
- Frontend: HTML, CSS
- Tools: IntelliJ, Git, GitHub, Postman (for API testing)

## Screenshots
Here’s a look at the current state of the EasyShop application:

Note: This is a single-page application. Features like the cart and checkout are still under development.

![Logo](images/screenshots/logo.png)

![Header](Screenshot%202025-06-26%20222652.png)

![Username and log out button](Screenshot%202025-06-26%20222803.png)

![List of Products](Screenshot%202025-06-26%20222736.png)

![Filtering](Screenshot%202025-06-26%20222720.png)
