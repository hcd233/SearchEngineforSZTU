import os
import spacy
import string

#string.punctuation中包含英文的标点，我们将其放在待去除变量remove中
#函数需要三个参数，前两个表示字符的映射，我们是不需要的。
remove = str.maketrans('','',string.punctuation)

nlp = spacy.load("en_core_web_sm")

#目标文件夹
text_path1 = r'./fruits-before/'
text_path2 = r'./fruits-after/'
# 拿到文件夹下面的所有文件
text_list = os.listdir(text_path1)
for path in text_list:
    with open(text_path1 + path,'r') as f:
        result = f.read().lower()
        # 去除标点符号
        result = result.translate(remove)

        #去除停用词
        sw_spacy = nlp.Defaults.stop_words
        words = [word for word in result.split() if word.lower() not in sw_spacy]
        new_text = " ".join(words)

        #词形还原
        arr=""
        result = nlp(new_text, config={"mode":"rule"})
        
        for token in result:
            arr += token.lemma_ + " "

        with open(text_path2 +path,'w') as w:
            w.write(arr)
