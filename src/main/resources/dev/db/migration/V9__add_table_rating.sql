CREATE TABLE IF NOT EXISTS `shopapp`.`ratings` (
  `id` INT NOT NULL,
  `rating` INT NOT NULL,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  `total_review` INT DEFAULT 0,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`, `product_id`),
  INDEX `fk_ratings_products_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_ratings_products`
    FOREIGN KEY (`product_id`)
    REFERENCES `shopapp`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
