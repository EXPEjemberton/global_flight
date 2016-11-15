import csv

f = open('../us_airports.csv')
o = open('../airport_cities.csv', 'a')

city_list = []

csv_f = csv.reader(f)
for row in csv_f:
  if(row[2] not in city_list):
    city_list.append(row[2])

for city in sorted(city_list):
  o.write(city + "\n")