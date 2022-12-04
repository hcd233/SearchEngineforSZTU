package org.defaults;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.*;
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
        String head_xp = null;
        String body_xp = null;
        if(mode == 1){
            head_xp = "/html/body/div[3]/div[3]/div[2]/div[1]/h1";
            body_xp = "/html/body/div[3]/div[3]/div[2]/div[2]/div";
        }
        else if(mode==2){
            head_xp = "/html/body/div/div/div[3]/div[1]";
            body_xp = "/html/body/div/div/div[3]/div[4]/div";
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
            String text = head.text();
            if(text.length()!=0){
                //System.out.println(head.text());
                FileWriter fw = new FileWriter(new File(save_dir+"/"+text+".txt"),false);
                Elements body = document.selectXpath(body_xp);
                //fw.write(text+" ");
                fw.write(body.text());
                fw.close();
                Date date = new Date();
                logfw.write("[Log:"+ date.toString() + "] " + url + " " + save_dir+"/"+text+".txt\n");
                System.out.println("Save to "+save_dir+"/"+text+".txt");
                String next_url = document.selectXpath("/html/body/div[4]/form/div/div[4]/p[2]/a").attr("href");
                httpclient.close();
                response.close();
                return next_url;

            }
        }
        httpclient.close();
        response.close();
        return null;
    }
}