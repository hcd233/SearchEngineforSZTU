package org.defaults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

class DictArray { // Dict数组，实现Append方法去重添加
    private ArrayList<Dict> dict_array;
    private int size;
    DictArray() {
        this.dict_array = new ArrayList<Dict>();
        this.size = 0;
    }
    public void Append(String str, String file){
        int idx = query(str);
        if(idx!=-1){
            Dict tmp = dict_array.get(idx);
            tmp.data.value++;
            if(!tmp.data.files.contains(file)){
                tmp.data.files.add(file);
            }
        }
        else {
            Dict tmp = new Dict(str,file);
            dict_array.add(tmp);
        }
        size++;
    }
    public void Append(String [] strs, String file){
        for(String str:strs){
            Append(str,file);
        }
    }
    public int query(String str){
        for(int i=0;i<dict_array.size();i++){
            if (Objects.equals(dict_array.get(i).key, str)){
                return i;
            }
        }
        return -1;
    }
    public Integer get_size(){
        return size;
    }
    public HashMap<String, Data> toHashMap(){
        HashMap<String, Data> hashmap = new HashMap<>();
        for(Dict dict:dict_array){
            hashmap.put(dict.key,dict.data);
        }
        return hashmap;
    }

}
class Dict{ // 结构体 关键词 + data类
    public String key;
    public Data data;
    public Dict(String key){
        this.key = key;
        this.data = new Data();
    }
    public Dict(String key,String ori_file){
        this.key = key;
        this.data = new Data(ori_file);
    }

    @Override
    public String toString(){
        return this.key + ":" + this.data.value + ":" + this.data.files;
    }
}
class Data{ // data类 词频为value 相关文件为 files（字符数组
    public Integer value;
    public ArrayList<String> files;
    public Data(String ori_file){
        this.value = 1;
        this.files = new ArrayList<>();
        files.add(ori_file);
    }
    public Data(){
        this.value = 1;
        this.files = new ArrayList<>();
    }
    public String toString(){
        return "val:"+value+" files:"+files;
    }
}
