package org.defaults;
import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InverseIndex { //倒排索引类
    /**
     * Data:{freq,file_list}
     */
    private static HashMap<String,Data> WordMap = new HashMap<>();
    private int size;
    private int total;
    private String proc;
    public InverseIndex() {
        this.size = 0;
        this.total = 0;
        this.proc = null;
        WordMap = new HashMap<>();

    }
    void setProc(String proc){
        this.proc = proc;
    }
    public  HashMap<String, Data> getWordMap() {
        return WordMap;
    }
    public ArrayList<String> QueryArray(String word){
        Data res = WordMap.get(word);
        if(res == null){
            System.out.println("No result of "+word);
            return null;
        }
        else{
            return res.files;
        }

    }
    public double QueryFrequency(String word){
        Data res = WordMap.get(word);
        if(res == null){
            return -1;
        }
        else{
            return res.freq *1.0/total;
        }
    }
    public JsonInfo GetFileContent(String file, String word) throws IOException {
        // 输出相关字段的算法 如...双选会当天，学生根据自己心目中的优先顺序...
        int min,max;
        FileInputStream fis = new FileInputStream(proc.replace("processed","raw")+"/"+file.substring(1).replace(".txt",".json"));
        //System.out.println(proc.replace("processed","raw")+"/"+file.substring(1).replace(".txt",".json"));
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String jsoninfo = br.readLine();


        JsonInfo ji= JSON.parseObject(jsoninfo,JsonInfo.class);
        String content = ji.getContent();

        //System.out.println(content);
        int idx = content.indexOf(word);
        if(idx!=-1){
            int WordNum = 30;
            if(idx>WordNum/2) min = idx-WordNum/2; // 判断是否越界<0
            else min = 0;
            if(idx< content.length()-1-(WordNum-idx+min)) max = idx+(WordNum-idx+min); // 判断是否越界 >= size
            else {
                max = content.length() - 1;
            }
            StringBuilder sb = new StringBuilder();
            if(min!=0)
                sb.append("...");
            sb.append(content, min, max);
            if(max != content.length() - 1)
                sb.append("...");
            return new JsonInfo(ji.getUrl(), ji.getTitle(), sb.toString());
        }
        else{
            return null;
        }
    }
    public JsonInfo GetFileContent(String file, ArrayList<String>words) throws IOException {
        JsonInfo res = new JsonInfo("","","");
        for(int i=0;i<words.size();i++){
            if(i==0){
                JsonInfo tmp = GetFileContent(file, words.get(i));
                if(Objects.equals(res.getContent(), "")&& tmp!=null){
                    res = tmp;
                    res.count = 1;
                }
            }
            else{
                JsonInfo tmp = GetFileContent(file, words.get(i));
                if(tmp!=null){
                    res.setContent(res.getContent()+"  "+tmp.getContent());
                    res.count++;
                }
            }
        }
        return res;
    }
    public void QueryMode() throws Exception { //查询模式，在布尔检索重写了，这里没用到
        Scanner inp = new Scanner(System.in);
        String str;
        System.out.println("Press Q To Exit QueryMode.");
        while(true){
            str = inp.next();
            if(Objects.equals(str, "Q") || Objects.equals(str, "q"))
                break;
            ArrayList<String> q1 = QueryArray(str);
            double q2 = QueryFrequency(str);
            if(q1!=null){
                System.out.print("Contained:"+q1+" ");
                System.out.println("frequency:"+String.format("%.6f",q2));
                for(String s1:q1){
                    GetFileContent(s1,str);
                }
            }

        }
    }
    public void File2HashMap() throws IOException { //读取文件转化为字典

        File proc_file = new File(proc); // 读取目标文件夹
        File[] proc_files = proc_file.listFiles(); // 列出全部文件
        // System.out.println(Arrays.toString(proc_files));
        assert proc_files != null; //防止文件夹为空
        //文件读取
        //System.out.println(Arrays.toString(proc_files));
        HashMap<String,String[]> FileHashMap = new HashMap<>();
        for(File file:proc_files){ // 开始读每一个文件
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String[] rl = br.readLine().split(" "); // 文件内容分词
            // System.out.println(rl);
            FileHashMap.put(file.getName(),rl); // 将文件名 和 内容 放入哈希表
        }
        Set<String> key = FileHashMap.keySet(); // 获取全部文件名；
        for(String file:key){ // 统计总共的词数 建立倒排索引
            String[] temp = FileHashMap.get(file);
            for(String str:temp){
                if(WordMap.containsKey(str))
                {
                    Data tmp = WordMap.get(str);
                    tmp.freq++;
                    if(!tmp.files.contains(file)){
                        tmp.files.add(file);
                    }
                    WordMap.replace(str,tmp);
                }
                else {
                    Data tmp = new Data(file);
                    WordMap.put(str,tmp);
                } // 建立关键词和文件的索引 Append是自定义的方法
                total++;
            }
        }
        size = WordMap.size();
        //System.out.println(WordMap);
    }


}
class Data{ // data类 词频为value 相关文件为 files（字符数组
    public Integer freq;

    public final ArrayList<String> files;
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