package org.defaults;

import com.alibaba.fastjson.JSON;

public class test {


    public static void main(String[] args) {
        test t1 = new test();
        t1.test2();
    }
    public void test2(){
        String str = "1145141919810";
        System.out.println(str.indexOf("4513"));
        System.out.println(str.substring(-1));
    }
    public void test1(){
        Book b1 = new Book(1002,"红楼梦","贾宝玉与林黛玉与鱼鱼鱼鱼鱼鱼");
        String s = JSON.toJSONString(b1);
        System.out.println(s);
        Book b2 = JSON.parseObject(s,Book.class);
        System.out.println(b2.getChara()+b2.getId()+b2.getName());
    }
}
class Book{
    private int id;
    private String name;
    private String chara;
    public Book(int id,String name,String chara) {
        this.id = id;
        this.name = name;
        this.chara = chara;
    }

    public int getId() {
        return id;
    }

    public String getChara() {
        return chara;
    }

    public String getName() {
        return name;
    }

    public void setChara(String chara) {
        this.chara = chara;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}