package org.defaults;
import java.io.*;
import java.util.*;

public class InverseIndex { //倒排索引类
    private final HashMap<String,Data> WordMap;
    private final HashMap<String,String[]> FileHashMap;
    private int size;
    private int total;
    private final String proc;
    public InverseIndex(String pro_path) throws IOException {
        this.size = 0;
        this.total = 0;
        this.proc = pro_path;
        this.FileHashMap = new HashMap<>();
        this.WordMap = new HashMap<>();
        File2HashMap();
    }

    public HashMap<String, Data> getWordMap() {
        return WordMap;
    }
    public HashMap<String, String[]> getFileHashMap() {
        return FileHashMap;
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
    public void DisplayContent(String file,String word){ // 输出相关字段的算法 如...双选会当天，学生根据自己心目中的优先顺序...
                                                                    // TODO 必要可以重写这个算法
        if(file == null)//判断是否没有和关键词相关的文件
            return ;
        String[] strs = FileHashMap.get(file); //查找文件里的内容 返回按照分词的分好的字符数组
        for(int i=0;i< strs.length;i++){ // for 循环匹配关键词
            if(Objects.equals(strs[i], word)){
                int min,max;
                if(i>4) min = i-4; // 判断是否越界<0
                else min = 0;
                if(i< strs.length-5) max = i+4; // 判断是否越界 >= size
                else max = strs.length - 1;
                System.out.print(file+": ");
                if(min!=0)
                    System.out.print("...");
                char c = word.toCharArray()[0];
                if((c<='z'&&c>='a')||c<='Z'&&c>='A'){ // 判断中英文
                    for(int p=min;p<=max;p++){
                        System.out.print(strs[p]+" ");
                    }
                }
                else{
                    for(int p=min;p<=max;p++){
                        System.out.print(strs[p]);
                    }
                }
                if(max != strs.length - 1)
                    System.out.println("...");
                else System.out.println();
                break; //只打印这个文件匹配到的第一个
            }
        }

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
                    DisplayContent(s1,str);
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
        for(File file:proc_files){ // 开始读每一个文件
            BufferedReader br = new BufferedReader(new FileReader(file));
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
        size = this.WordMap.size();
    }
}
