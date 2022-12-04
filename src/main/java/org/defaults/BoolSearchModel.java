package org.defaults;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;
public class BoolSearchModel extends InverseIndex{ // 继承自倒排索引类，这个子类只有新建的算法还有重写的算法
    private final Stack<ArrayList<String>> NumStack; // 操作数栈
    private final Stack<String> OperStack;// 运算符栈
    public BoolSearchModel(String pro_path) throws IOException { //构造方法 输入txt所在的文件夹进行倒排索引，初始化栈
        super(pro_path);
        this.OperStack = new Stack<>();
        this.NumStack = new Stack<>();
    }
    private ArrayList<String> AND(ArrayList<String> wordarray1,ArrayList<String> wordarray2){ // 与运算 私有封装
        if(wordarray1==null||wordarray2==null){
            return null;
        }
        ArrayList<String> res = new ArrayList<>(wordarray1);
        res.retainAll(wordarray2);
        return res;
    }
    private ArrayList<String> OR(ArrayList<String> wordarray1,ArrayList<String> wordarray2){ // 或运算 私有封装
        if(wordarray1==null&&wordarray2==null){
            return null;
        }
        else if(wordarray1 == null){
            return wordarray2;
        }
        else if(wordarray2 == null){
            return wordarray1;
        }
        ArrayList<String> res = new ArrayList<>(wordarray1);
        res.removeAll(wordarray2);
        res.addAll(wordarray2);
        return res;
    }
    public ArrayList<String> Calculate(ArrayList<String>arr1, ArrayList<String>arr2, String operator){ // 集成与或运算，公共函数可调用
        assert Objects.equals(operator, "&")|Objects.equals(operator, "|");
        if(Objects.equals(operator, "&")){
            return AND(arr1,arr2);
        }
        else{
            return OR(arr1,arr2);
        }
    }

    @Override

    public ArrayList<String> QueryArray(String word) { // 查询关键词word包含的文件 返回字符数组
        /*TODO B-树模糊搜索*/
        Data res = getWordMap().get(word);
        if(res == null){
            // System.out.println("No result of "+word);
            return null;
        }
        else{
            return res.files;
        }
    }
    @Override
    public void QueryMode() throws Exception { // 查询模式，通过控制台输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Press Q To Exit QueryMode.");
        while(true){
            str = br.readLine();
            String[]strs = str.split(" ");
            // System.out.println(Arrays.toString(strs));
            if(strs.length==1){
                if(Objects.equals(strs[0], "q") || Objects.equals(strs[0], "Q"))
                    break;
                ExactSearch(strs[0]);
            }
            else{
                ArrayList<String> result = BoolSearch(strs);
                System.out.println(result);
            }
        }
    }
    public ArrayList<String> BoolSearch(String[]strings) { // 布尔搜索算法，输入是空格分割的表达式{ 如 A & ( B | C )}转换成字符数组
        NumStack.clear();
        OperStack.clear();
        for(String str:strings){
            if(Objects.equals(str, "&") || Objects.equals(str, "|" )|| Objects.equals(str, "(")){
                OperStack.push(str);
            }
            else if(Objects.equals(str, ")")){
                while(!Objects.equals(OperStack.peek(), "(")){
                    assert !NumStack.empty()&&!OperStack.empty():"Invalid Expression";
                    String oper = OperStack.peek();
                    OperStack.pop();
                    ArrayList<String> arr1,arr2;
                    arr1 = NumStack.peek();
                    NumStack.pop();
                    arr2 = NumStack.peek();
                    NumStack.pop();
                    ArrayList<String> res = Calculate(arr1,arr2,oper);
                    NumStack.push(res);
                }
                OperStack.pop();
            }
            else{
                NumStack.push(QueryArray(str));
            }
        }
        while(!OperStack.empty()){
            String oper = OperStack.pop();
            ArrayList<String> arr1,arr2;
            if(NumStack.size()<2)
                return null;
            arr1 = NumStack.pop();
            arr2 = NumStack.pop();
            ArrayList<String> res = Calculate(arr1,arr2,oper);
            NumStack.push(res);
        }
        return NumStack.peek();
    }
    private ArrayList<String> FuzzyInpStr(String str) throws IOException {
        //将输入的关键词str 进行模糊化处理 如蔡元哲 = 蔡 + 元 + 哲 + 蔡元 + 元哲 + 蔡元哲...
        //TODO 模糊化关键词算法，我实现了个最基础的，效果不佳，需要在这里重写搜索算法
        //System.out.println();
        StringReader sr = new StringReader(str);
        IKSegmenter ik = new IKSegmenter(sr,true);
        Lexeme lex;
        ArrayList<String> res = new ArrayList<>();
        while((lex=ik.next())!=null){
            //System.out.print(lex.getLexemeText()+" ");
            res.add(lex.getLexemeText());
        }
        return res;
    }
    public void ExactSearch(String str) throws IOException { // 精确搜索，不需要改动
        ArrayList<String> strarr = FuzzyInpStr(str);
        StringBuilder strb = new StringBuilder();
        for(int i=0;i<strarr.size();i++){
            strb.append(strarr.get(i));
            if(i!=strarr.size()-1)
                strb.append(" & ");
        }
        ArrayList<String> bls = BoolSearch(strb.toString().split(" "));
        System.out.println(bls);
        for(String s:bls){
            DisplayContent(s,strarr.get((strarr.size() -1)/2));
        }
    }
    public void FuzzySearch(String str) throws IOException { //
        // TODO 模糊搜索算法 下面是最基础的，效果不佳
        // TODO 必要可以重写输出的算法，我写的是DisplayContent();
        ArrayList<String> strarr = FuzzyInpStr(str);
        StringBuilder strb = new StringBuilder();
        for(int i=0;i<strarr.size();i++){
            strb.append(strarr.get(i));
            if(i!=strarr.size()-1)
                strb.append(" | ");
        }
        ArrayList<String> bls = BoolSearch(strb.toString().split(" "));
        System.out.println(bls);
        for(String s:strarr){
            for(String file:bls){
                DisplayContent(file,s);
            }
        }
    }
}
