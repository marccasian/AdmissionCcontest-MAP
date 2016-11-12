total = 0

with open("lines.txt", "r") as f:
  for line in f:
    l = line.split('-')
    print('->'.join(l))
    total += int(l[1])

print('Total linii=',total)