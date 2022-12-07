package org.defaults;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class WebSpider{ // 爬虫 爬取的数据在data里 分别是 BDI大数据，SQIM中德，GWT公文通
    private final File log;
    private String save_dir;
    private FileWriter logfw;
    public WebSpider(String save_dir){
        this.save_dir = save_dir;
        this.log = new File(this.save_dir+"/log.txt");
    }

    public void setSave_dir(String save_dir) {
        this.save_dir = save_dir;
    }
    public void Open_Log() throws IOException {
        this.logfw = new FileWriter(log,true);
    }
    public void Close_Log() throws IOException {
        this.logfw.close();
    }

    public String run_spider(String url, int mode) throws IOException {
        /*
        mode:1 BDI info
             2 SGIM info
         */
        String head_xp;
        String body_xp;
        if(mode == 1){
            head_xp = "/html/body/div[3]/div[3]/div[2]/div[1]/h1";
            body_xp = "/html/body/div[3]/div[3]/div[2]/div[2]";
        }
        else if(mode==2){
            head_xp = "/html/body/div/div/div[3]/div[1]";
            body_xp = "/html/body/div/div/div[3]/div[4]/div";
        }
        else if(mode==3){
            head_xp = "/html/body/div[4]/div[2]/form/div/h1";
            body_xp = "/html/body/div[4]/div[2]/form/div/div[2]/div";
        }
        else if(mode==4){
            head_xp = "/html/body/div[6]/div[2]/div[2]/form/div/div[1]/h5";
            body_xp = "/html/body/div[6]/div[2]/div[2]/form/div/div[2]/div";
        }
        else if(mode==5){
            head_xp = "/html/body/div[4]/div[2]/div[2]/form/div[1]/div/div[1]/span";
            body_xp = "/html/body/div[4]/div[2]/div[2]/form";

        }
        else if(mode==6){
            head_xp = "/html/body/div[3]/div[2]/div[2]/div/form/div/div[1]";
            body_xp = "/html/body/div[3]/div[2]/div[2]/div/form/div/div[3]";
        }
        else{
            head_xp = "/html/body/div[4]/form/div/h1";
            body_xp = "/html/body/div[4]/form/div";
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpget.setHeader("Accept-Encoding", "gzip, deflate");
        httpget.setHeader("Connection", "keep-alive");
        httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit" +
                "/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.62=80113FAFD94" +
                "3DE87C53BCC8CBB4D65B1");
        httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image" +
                "/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpget.setHeader("Cookie","JSESSIONID=80113FAFD943DE87C53BCC8CBB4D65B1");
        httpget.setHeader("sec-ch-ua-platform","Windows");



        CloseableHttpResponse response = httpclient.execute(httpget);
        if(response.getStatusLine().getStatusCode() == 200){
            String html = EntityUtils.toString(response.getEntity(),"UTF-8");
            //System.out.println(html);
            Document document = Jsoup.parse(html);
            Elements head = document.selectXpath(head_xp);
            String text = head.text().replace('|','_').replace('/','_')
                    .replace('\\','_').replace('<','_').replace('>','_')
                    .replace(':','_').replace('*','_').replace('?','_');
            if(text.length()!=0){
                //System.out.println(head.text());
                FileWriter fw = new FileWriter(new File(save_dir+"/"+text+".json"),false);
                Elements body = document.selectXpath(body_xp);
                //fw.write(text+" ");
                JsonInfo ji = new JsonInfo(url,text,body.text().replace('|','_').replace('/','_')
                        .replace('\\','_').replace('<','_').replace('>','_')
                        .replace(':','_').replace('*','_').replace('?','_'));
                String writejson = JSON.toJSONString(ji);
                System.out.println(writejson);
                fw.write(writejson);
                fw.close();
                Date date = new Date();
                logfw.write("[Log:"+ date.toString() + "] " + url + " " + save_dir+"/"+text+".json\n");
                System.out.println("Save to "+save_dir+"/"+text+".json");
                httpclient.close();
                response.close();
                String href = document.selectXpath("/html/body/div[4]/form/div/div[4]/p[2]/a").attr("href");
                return href;
            }
        }
        httpclient.close();
        response.close();
        return null;
    }
    public static void main(String[] args) throws Exception {

        String url;
        String SAVE_DIR = "./data/GWT";
        File dir = new File(SAVE_DIR);
        if(!dir.exists()&&!dir.isDirectory()) dir.mkdir();
        WebSpider ws = new WebSpider(SAVE_DIR);
        ws.Open_Log();
        if(0==1){
            for(int i=1342;i<=1350;i++){
                for(int p=5000;p<5500;p++){
                    if(p%20==0){
                        System.out.println(i+" "+p);
                        //Thread.sleep(1000);
                    }
                    //url = "https://sfl.sztu.edu.cn/info/" + i + "/" + p + ".htm";
                    //bs url = "https://bs.sztu.edu.cn/info/"+i + "/" + p + ".htm";
                    url = "https://bdi.sztu.edu.cn/info/"+ i + "/" + p + ".htm";
                    //zd url = "https://sgim.sztu.edu.cn/info/1161/2774.htm";
                    //gwt url = "https://sgim.sztu.edu.cn/info/"+i+"/"+p+".htm";
                    //cop url = "https://cop.sztu.edu.cn/info/1025/"+ p + ".htm";
                    ws.run_spider(url,1);
                }
            }
        }
        else{
            //url = "https://sfl.sztu.edu.cn/info/1014/3119.htm";
            // cop url = "https://cop.sztu.edu.cn/info/1025/1092.htm";
            // qsa url = "https://qsa.sztu.edu.cn/info/1086/1373.htm";
            String root = "http://nbw.sztu.edu.cn/info/1021/";
            String tail = "43068.htm";
            while (true){
                tail = ws.run_spider(root+tail,11);
                if(tail == null) break;
            }

            // zd url = "https://sgim.sztu.edu.cn/info/1162/2696.htm";

        }
        ws.Close_Log();
    }
}
