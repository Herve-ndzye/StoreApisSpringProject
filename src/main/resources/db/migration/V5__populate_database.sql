INSERT INTO categories (name)
VALUES ('Fruits'),
       ('Vegetables'),
       ('Dairy'),
       ('Bakery'),
       ('Beverages'),
       ('Snacks'),
       ('Meat'),
       ('Seafood');
INSERT INTO products (name, price, description, category_id)
VALUES ('Bananas (1kg)', 1.80, 'Fresh ripe Cavendish bananas imported from Uganda.', 1),

       ('Red Apples (1kg)', 2.50, 'Crisp and sweet red apples perfect for snacks or desserts.', 1),

       ('Fresh Spinach (500g)', 1.40, 'Organic fresh spinach leaves ideal for salads and cooking.', 2),

       ('Carrots (1kg)', 1.20, 'Crunchy orange carrots rich in vitamins and great for soups.', 2),

       ('Whole Milk (1L)', 1.10, 'Pasteurized whole cow milk from local dairy farms.', 3),

       ('Cheddar Cheese (200g)', 3.50, 'Aged cheddar cheese block with rich and sharp flavor.', 3),

       ('White Sandwich Bread', 1.60, 'Soft bakery-style white bread perfect for sandwiches.', 4),

       ('Orange Juice (1L)', 2.20, '100% natural orange juice with no added sugar.', 5),

       ('Potato Chips Classic (150g)', 1.75, 'Crispy salted potato chips made from premium potatoes.', 6),

       ('Chicken Breast (1kg)', 5.90, 'Fresh boneless chicken breast packed with protein.', 7);