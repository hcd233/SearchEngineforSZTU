package org.defaults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Kgram extends BoolSearchModel{
    public Kgram(String pro_path) throws IOException {
        super();
    }

    public  ArrayList<String> get_gram(String input, int n) {
        StringBuilder set= new StringBuilder();
        StringBuilder gram = new StringBuilder();
        set.append("*".repeat(Math.max(0, n - 1)));
        ArrayList<String> result = new ArrayList<>();
            input = set+ input + set;
            input=input.replaceAll(" ","");
            //System.out.println(input);
            for (int i=0 ; i < input.length()-n+1; i++) {
                for(int j=i;j<i+n;j++) {
                    gram.append(input.toCharArray()[j]);
                }
                gram= new StringBuilder(gram.toString().replaceAll("\\*", ""));
                result.add(gram.toString());
                gram = new StringBuilder();
            }
        return result;
    }
    public  ArrayList<String> get_gram(String input) {
        StringBuilder set= new StringBuilder();
        StringBuilder gram = new StringBuilder();
        set.append("*".repeat(2 - 1));
        ArrayList<String> result = new ArrayList<>();
        input =  set+input + set;
      //  System.out.println(input);
        for (int i=0 ; i < input.length()-2+1; i++) {
            for(int j=i;j<i+2;j++) {
                gram.append(input.toCharArray()[j]);
            }
            gram= new StringBuilder(gram.toString().replaceAll("\\*", ""));
            result.add(gram.toString());
            gram = new StringBuilder();
        }
        return result;
    }
    public  ArrayList<String> wildcard(String in, int n) throws IOException {
        ArrayList<String> input=get_gram(in,n);
        ArrayList<String> input1=new ArrayList<>();
        for(String str:input){
            input1.add(str.replaceAll("\\*",""));
            input1.add("|");
        }
        input1.remove(input1.size() - 1);
        //System.out.println(input1);
        return BoolSearch(input1.toArray(new String[0]));
    }
    
    public static void main(String[] arg) throws Exception {
        String pro_path="./data/BDI/process-before";
        String after_path="./data/BDI/processed";
        WildCardMap wildCardMap=null;
        Kgram kg = new Kgram(pro_path);
        Kgram kg1 = new Kgram(after_path);
        ArrayList<HashMap<String,ArrayList<String>>>result_list=new ArrayList<>();
        String input ="陈巧";
        input=input.replaceAll("\\*","");
        int n=input.length();
        ArrayList<String> inputs=kg.get_gram(input,n);//分词前 词组前后字
        System.out.println(inputs);
        for(String str:inputs){
            System.out.println(str);
            wildCardMap=new WildCardMap(pro_path,str);
            HashMap<String,ArrayList<String>>result=wildCardMap.getWildcardmap();
            result_list.add(result);
        }
        assert wildCardMap != null;
        ArrayList<String> words=null;
        for(HashMap<String,ArrayList<String>> map1:result_list){
            //System.out.println(result_list);
           for (Map.Entry<String,ArrayList<String>> map : map1.entrySet()) {
               words=map.getValue();
               //System.out.println(words);
               for(String str:words){
                   ArrayList<String> words_kgram=kg1.get_gram(str,n);

                   System.out.println(words_kgram);
                   for(String s:words_kgram){
                       System.out.println(s);
                       System.out.println(kg1.getWordMap());
                       if(kg1.getWordMap().containsKey(s)){
                           System.out.println(kg1.getWordMap().get(s).files);
                       }
                   }
               }
           }
        }

    }

    public String fuzzySearch(String str1,String str2) {
           return str2;
    }
}
