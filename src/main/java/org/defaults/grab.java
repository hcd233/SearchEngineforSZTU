package org.defaults;

import java.io.File;

public class grab { // 调用爬虫类爬取数据，这里不用管
    public static void main(String[] args) throws Exception {

            String url;
            String SAVE_DIR = "./data/GWT";
            File dir = new File(SAVE_DIR);
            if(!dir.exists()&&!dir.isDirectory()) dir.mkdir();
            WebSpider ws = new WebSpider(SAVE_DIR);
            ws.Open_Log();
            if(0==1){
                for(int i=1238;i<1250;i++){
                    for(int p=2100;p<3000;p++){
                        if(p%20==0){
                            System.out.println(i+" "+p);
                            //Thread.sleep(1000);
                        }
                        //bdi url = "https://bdi.sztu.edu.cn/info/"+ i + "/" + p + ".htm";
                        //zd url = "https://sgim.sztu.edu.cn/info/1161/2774.htm";
                        url = "https://sgim.sztu.edu.cn/info/"+i+"/"+p+".htm";
                        ws.run_spider(url,2);
                    }
                }
            }
            else{
                url = "http://nbw.sztu.edu.cn/info/1018/";
                String tail = "43048.htm";
                // bdi url = "https://bdi.sztu.edu.cn/info/1333/5165.htm";
                // zd url = "https://sgim.sztu.edu.cn/info/1162/2696.htm";
                for(int i=0;i<=200;i++){
                    tail = ws.run_spider(url+tail,3);

                }
            }
            ws.Close_Log();
        }
}
