import os


class Word_Set:
    def __init__(self) -> None:
        self.__sets: dict[dict] = {}

    def __repr__(self) -> str:
        return str(self.__sets)

    def sort_set(self, reverse: bool = True) -> None:
        self.__sets = dict(sorted(self.__sets.items(), key=lambda x: x[1], reverse=reverse))

    def append(self, word) -> None:
        if isinstance(word, dict):
            if word in self.__sets:
                self.__sets[word] += 1
            else:
                self.__sets[word] = 1
        elif isinstance(word, list) or isinstance(word, tuple):
            for wd in word:
                if wd in self.__sets:
                    self.__sets[wd] += 1
                else:
                    self.__sets[wd] = 1

    def total(self) -> int:
        return sum(self.__sets.values())


if __name__ == '__main__':
    mode = "EN"
    if mode == "EN":
        ori = "../data/EN/fruits-before/"
        ori_files = os.listdir(ori)
        proc = "../data/EN/fruits-after/"
        proc_files = os.listdir(proc)
    else:
        ori = "../data/ZH/水果_分词前/"
        ori_files = os.listdir(ori)
        proc = "../data/ZH/水果_分词后/"
        proc_files = os.listdir(proc)

    print(proc_files)
    for pf in proc_files[:1]:
        files = proc + '/' + pf
        with open(files, mode='r', encoding='utf-8') as data:
            word_set = Word_Set()
            data_list = data.read().split(' ')
        print(data_list[:5])
        word_set.append(data_list)
        word_set.sort_set()
        print(word_set.total())
        print(word_set)
