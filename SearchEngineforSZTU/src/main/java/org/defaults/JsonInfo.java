package org.defaults;

public class JsonInfo{
    private String content;
    private String url;
    private String title;
    public int count;
    public JsonInfo(String url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString(){
        return url + " " + title + " " + content + "\n";
    }
}
