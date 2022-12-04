import os
import shutil
path = "P:\program_file\program\JAVA\default_maven\data\_ZH"
pro_dir = "P:\program_file\program\JAVA\default_maven\data\_ZH/processed"
subpath = os.listdir(path)
os.makedirs(pro_dir)
print(subpath)
for sp in subpath:
    file_path = path + '/' + sp
    content = None
    with open(file_path,mode='r',encoding='gbk') as data:
        content = data.read()
    clist = content.split(" ")
    while("" in clist):
        clist.remove("")
    print(clist)
    strs = ""
    for i in clist:
        strs+=i
        if(i!=len(clist)-1):
            strs+=" "
    with open(pro_dir+'/_'+sp,mode='a+',encoding='gbk') as data:
        data.write(strs)
