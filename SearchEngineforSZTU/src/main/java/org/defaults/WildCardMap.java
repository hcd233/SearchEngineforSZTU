package org.defaults;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class WildCardMap {
    public HashMap<String,ArrayList<String>>WCmap=new HashMap<>();
    public HashMap<String, ArrayList<String>> getWildcardmap() {
        return WCmap;
    }
    public String key;
    public WildCardMap(String pro_path,String s) throws Exception {
        WCmap.put(s,Get_WildcardMap(pro_path,s,s.length()));
    }
    public ArrayList<String> Get_WildcardMap(String pro_path, String thing, int num) throws Exception {
        File proc_file = new File(pro_path);
        File[] proc_files = proc_file.listFiles();
        assert proc_files != null;

        ArrayList<String> result= new ArrayList<>();
        for(File file:proc_files){
            // System.out.println(rl);
            BufferedReader reader=new BufferedReader(new FileReader(file));
           // System.out.println(file);
            String s;
            while((s=reader.readLine())!=null){
                //System.out.println(s);
                // System.out.println(index);
                while(s.contains(thing)){
                    int start=-1;
                    int end=-1;
                    int index=s.indexOf(thing);
                    if(index!=-1){
                        //System.out.println(index);
                        start=index-1;
                        end=index+num+1;
                        if(end>s.length()) {
                            end = s.length();
                        }
                        }if(start<0){
                            start=0;
                        }

                    if(start!=end){
                        result.add(s.substring(start,end));
                    }
                    s=s.substring(end);
                }
            }
        }
        return result;
    }
    public static void main(String[]args) throws Exception {
        String pro_path="./data/BDI/process-before";
        String input="蔡元哲";
        System.out.println(input);
        WildCardMap wildCardMap=new WildCardMap(pro_path,input);
        System.out.println(wildCardMap.getWildcardmap().get(input));
    }

}
