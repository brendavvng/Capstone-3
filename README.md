# 🛍️ Capstone 3: EasyShop E-Commerce API & Web Application

Welcome to EasyShop, a full-stack e-commerce application project designed for users to browse, search, and purchase products online. This application simulates a real-world online shopping experience and includes both backend API development (using Spring Boot and MySQL) and a frontend user interface (HTML/CSS).


## 🌐 Project Overview

✔️ Features Implemented:

- 🔍 Product browsing and searching
- 🗂️ Viewing product details
- 📁 Browsing products by category

This project was built to demonstrate CRUD operations, API consumption using Postman, database integration, and frontend rendering with HTML templates.

⚠️ Note: Some features such as the shopping cart and checkout are under development and not fully functional. The ShoppingCart and ShoppingCartItem models are in place, but viewing and adding to cart currently fails due to unresolved backend issues.

 ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ୨♡୧ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈ ┈

## 🧭 Future Features:
Priority	+     Feature
- 🔒 High	    Shopping cart + checkout
- 🔐 High	    User login and authentication
- 💾 Medium	    Order history for users
- 💬 Medium	    Product reviews and ratings
- 🎁 Medium	    Wishlist or favorites
- 💳 Low	    Payment gateway integration (e.g. Stripe)
- 📱 Low	    Mobile-friendly/responsive design


## 🔧 Next Steps / Known Issues
- Debug the cart loading issue (likely related to the cart DAO or database table structure)
- Complete addToCart() and viewCart() endpoints
- Ensure foreign key constraints are properly set up for cart and cart items
- Add exception handling for better error responses

## 🛠️ Tech Stack
- Backend: Java, Spring Boot, MySQL
- Frontend: HTML, CSS
- Testing: Postman (for API testing)
- Tools: IntelliJ, Git, GitHub
