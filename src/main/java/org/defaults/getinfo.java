package org.defaults;

public class getinfo {
    public static void main(String[] args) throws Exception {
        if(1==1){ // 测试方法
            String path = "./data/BDI/processed";
            BoolSearchModel bsm = new BoolSearchModel(path);
            bsm.FuzzySearch("学院领导");
            //bsm.QueryMode();
        }
    }
}
