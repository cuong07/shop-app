CREATE DATABASE shopapp;

USE shopapp;

CREATE TABLE
    users(
        id INT PRIMARY KEY AUTO_INCREMENT,
        fullname VARCHAR(100) DEFAULT '',
        phone_number VARCHAR(10) NOT NULL,
        address VARCHAR(200) DEFAULT '',
        password VARCHAR(100) NOT NULL,
        created_at DATETIME,
        updated_at DATETIME,
        is_active TINYINT(1),
        date_of_birth DATE,
        facebook_account_id INT DEFAULT 0,
        google_account_id INT DEFAULT 0
    );

ALTER TABLE users ADD COLUMN role_id INT;

CREATE TABLE
    roles(
        id INT PRIMARY KEY,
        name VARCHAR(20) NOT NULL
    );

ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

CREATE TABLE
    tokens (
        id INT PRIMARY KEY AUTO_INCREMENT,
        token VARCHAR(255) UNIQUE NOT NULL,
        token_type VARCHAR(50) NOT NULL,
        expiration_date DATETIME,
        revoked TINYINT(1) NOT NULL,
        expired TINYINT(1) NOT NULL,
        user_id INT,
        -- FOREIGN KEY 
        FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE
    social_accounts (
        id INT PRIMARY KEY AUTO_INCREMENT,
        provider VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
        provider_id VARCHAR(50) NOT NULL,
        email VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
        name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
        user_id INT,
        FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE
    categories (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
    );

CREATE TABLE
    products (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(350),
        price FLOAT NOT NULL CHECK(price >= 0),
        thumbnail VARCHAR(400) DEFAULT '',
        description LONGTEXT,
        careated_at TIMESTAMP,
        updated_at TIMESTAMP,
        category_id INT,
        FOREIGN KEY (category_id) REFERENCES categories(id)
    );
ALTER TABLE products CHANGE careated_at create_at TIMESTAMP;
ALTER TABLE products CHANGE create_at created_at TIMESTAMP;

CREATE TABLE
    orders (
        id INT PRIMARY KEY AUTO_INCREMENT,
        user_id INT,
        FOREIGN KEY (user_id) REFERENCES users(id),
        fullname VARCHAR(100) DEFAULT '' COMMENT 'Tên có thể khác với tên của người dùng',
        email VARCHAR(100) DEFAULT '',
        phone_number VARCHAR(10) NOT NULL,
        address VARCHAR(200) NOT NULL,
        note VARCHAR(200) DEFAULT '',
        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        status VARCHAR(20),
        total_money FLOAT CHECK(total_money >= 0)
    );

ALTER TABLE orders ADD COLUMN shipping_method VARCHAR(100);

ALTER TABLE orders ADD COLUMN shipping_address VARCHAR(255);

ALTER TABLE orders ADD COLUMN shipping_date DATE;

ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(100);

ALTER TABLE orders ADD COLUMN payment_method VARCHAR(100);

ALTER TABLE orders ADD COLUMN active TINYINT(1);
-- 27/12/23
ALTER TABLE orders DROP COLUMN address;

ALTER TABLE
    orders MODIFY COLUMN status ENUM(
        'pending',
        'processing',
        'shopped',
        'delivered',
        'cancelled'
    ) COMMENT 'Trạng thái đơn hàng';

CREATE TABLE
    order_details(
        id INT PRIMARY KEY AUTO_INCREMENT,
        order_id INT,
        FOREIGN KEY (order_id) REFERENCES orders(id),
        product_id INT,
        FOREIGN KEY (product_id) REFERENCES products(id),
        price FLOAT CHECk(price >= 0),
        number_of_products INT CHECK(number_of_products > 0),
        total_money FLOAT CHECK(total_money >= 0),
        color VARCHAR(20) DEFAULT ''
    );

CREATE TABLE product_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_product_images_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
);

--UPDATE roles SET name = "ADMIN" WHERE id = 1;
--UPDATE roles SET name = "USER" WHERE id = 2;

ALTER TABLE product_images ADD COLUMN image_url LONGTEXT;

-- UPDATE TABLE "order_details"

-- ADD CART 19 - 12

CREATE TABLE carts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    note VARCHAR(300) DEFAULT '',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE cart_details (
     id INT PRIMARY KEY AUTO_INCREMENT,
     cart_id INT,
     product_id INT,
     number_of_products INT CHECK(number_of_products > 0),
     price DECIMAL(10, 2) CHECk(price >= 0),
     total_money DECIMAL(10, 2) CHECk(total_money >= 0),
     color VARCHAR(50),
     created_at TIMESTAMP,
     updated_at TIMESTAMP,
     CONSTRAINT fk_cart_items_cart_id FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
     CONSTRAINT fk_cart_items_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
ALTER TABLE carts ADD COLUMN  is_active TINYINT(1);


-- ADDRESS  27/12/23
CREATE TABLE user_address (
    id INT PRIMARY KEY AUTO_INCREMENT,
    address_one VARCHAR(255) NOT NULL,
    address_second VARCHAR(255) DEFAULT '',
    city VARCHAR(100) DEFAULT '',
    province VARCHAR(100) DEFAULT '',
    country VARCHAR(100) DEFAULT '',
    user_id INT,
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users(id)
);
--ALTER TABLE user_address ADD COLUMN user_id INT;
--ALTER TABLE user_address ADD CONSTRAINT fk_users_address FOREIGN KEY (user_id) REFERENCES users(id);
--ALTER TABLE user_address DROP FOREIGN KEY fk_address_user;
--ALTER TABLE user_address DROP COLUMN user_id;
--ALTER TABLE users ADD COLUMN address_id INT, ADD CONSTRAINT fk_users_address FOREIGN KEY (address_id) REFERENCES user_address(id);
--ALTER TABLE users DROP FOREIGN KEY fk_users_address;
--ALTER TABLE users DROP COLUMN address_id;