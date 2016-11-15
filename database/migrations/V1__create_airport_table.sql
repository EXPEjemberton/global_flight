CREATE TABLE airport(
  id INT AUTO_INCREMENT,
  code VARCHAR(5) NOT NULL,
  city_id INT NOT NULL,
  longitude DECIMAL(3,15),
  latitude DECIMAL(3,15)
)

CREATE TABLE city(
  id INT AUTO INCREMENT,
  name VARCHAR(77)
)