package org.defaults;

import com.alibaba.fastjson2.JSON;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class tokenizer { // 分词器
    private void tokenize_file(String file_path,String save_path) throws IOException {
        // 分词 将file_path文件分词并且保存到save_path文件里面 。私有封装
        File dir = new File(save_path);
        if(!dir.exists()&&!dir.isDirectory()) dir.mkdir();
        File file = new File(file_path);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        String str = br.readLine();
        System.out.println(str);
        JsonInfo jsonInfo = JSON.parseObject(str,JsonInfo.class);
        str = jsonInfo.getContent();
        StringReader sr = new StringReader(str);
        IKSegmenter ik = new IKSegmenter(sr,true);
        Lexeme lex;
        StringBuilder output_str = new StringBuilder();
        while((lex=ik.next())!=null){
            //System.out.print(lex.getLexemeText()+" ");
            output_str.append(lex.getLexemeText()).append(" ");
        }
        File output_file = new File(save_path+"/_"+file.getName().replace(".json",".txt"));
        if(!output_file.exists()){
            output_file.createNewFile();
        }
        FileWriter fw = new FileWriter(output_file,true);
        fw.write(new String(output_str.toString().getBytes(StandardCharsets.UTF_8)));
        fw.close();
        System.out.println(output_str);
    }
    public void batch_tokenize_files(String file_dir) throws IOException {
        // 批量文件分词方法 输入文件夹路径file_dir 调用私有方法将里面所有文件分词 保存到 file_dir/processed文件夹里
        File target_dir = new File(file_dir);
        File [] batch_files = target_dir.listFiles();
        assert batch_files != null;
        File save_dir = new File(file_dir+"/processed");
        if(!save_dir.exists()&&!save_dir.isDirectory()) save_dir.mkdir();
        for( File file:batch_files){
            tokenize_file(file.getPath(),save_dir.getPath());
        }
    }
    public static void main(String[] args) throws IOException {
        // 示例
        tokenizer tkz = new tokenizer();
        tkz.batch_tokenize_files("./data/BDI");
    }
}
