import csv

f = open('../world_cities_source.csv')
s = open('../airport_cities.csv')
o = open('../populated_airport_cities.csv', 'a')

counter = 0
airport_cities = []

csv_s = csv.reader(s)
for row in csv_s:
  airport_cities.append(row[0].lower())

csv_f = csv.reader(f)
for row in csv_f:
  if("United States" in row[5] and row[0].lower() in airport_cities):
    str = "{0},{1},{2},{3},{4},{5}".format(counter, row[0], row[8], row[2], row[3], row[4])
    o.write(str + "\n")
    counter += 1