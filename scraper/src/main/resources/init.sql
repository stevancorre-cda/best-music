-- Tables structures
CREATE TABLE IF NOT EXISTS `genre` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
);

CREATE TABLE `result` (
  `id` int(11) NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` decimal(10,0) NOT NULL,
  `year` year(4) DEFAULT NULL,
  `genreId` int(11) NOT NULL
)

-- Tables indexes
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `name` (`name`) USING HASH;

ALTER TABLE `result`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `resultId` (`id`),
  ADD UNIQUE KEY `genreId` (`genreId`);

-- Tables auto increment
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `result`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

-- Tables constraints
ALTER TABLE `result`
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`genreId`) REFERENCES `genre` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

-- Tables defaults
INSERT INTO `genre` (`id`, `name`) VALUES
    (1, 'Blues'),
    (2, 'Jazz'),
    (3, 'Reggae'),
    (4, 'Funk'),
    (5, 'Electro'),
    (6, 'DubStep'),
    (7, 'Rock'),
    (8, 'Saoul');