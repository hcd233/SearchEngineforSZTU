package org.defaults;
import java.util.ArrayList;

class Data{ // data类 词频为value 相关文件为 files（字符数组
    public Integer freq;
    public ArrayList<String> files;
    public Data(String ori_file){
        this.freq = 1;
        this.files = new ArrayList<>();
        files.add(ori_file);
    }
    public Data(){
        this.freq = 1;
        this.files = new ArrayList<>();
    }
    public String toString(){
        return "val:"+ freq +" files:"+files;
    }
}
