file_path = "../data/graph.txt"
output_path = "../data/processed_graph.txt"
strs = None
with open(file_path, mode='r', encoding="utf-8") as file:
    strs = file.read().replace("\n", ' ')

lstars = strs.split(" ")
lstars = lstars[:-1]
dstars = [eval(i) for i in lstars]
print(dstars, len(dstars))
with open(output_path, mode='a+', encoding='utf-8') as file:
    for i in range(len(dstars)):
        file.write(str(1 / dstars[i])[:6])
        if i % 10 == 9:
            file.write("\n")
        else:
            file.write(" ")
