CREATE TABLE IF NOT EXISTS `shopapp`.`reviews` (
  `id` INT NOT NULL,
  `content` NVARCHAR(300) NOT NULL,
  `rating` INT NOT NULL,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  `is_edited` TINYINT NULL,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `product_id`),
  INDEX `fk_reviews_users_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_reviews_products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_reviews_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `shopapp`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reviews_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shopapp`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
