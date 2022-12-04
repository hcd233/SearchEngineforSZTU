package org.defaults;

public class getinfo {
    public static void main(String[] args) throws Exception {
        String path = "./data/BDI/processed";
        BoolSearchModel bsm = new BoolSearchModel(path);

        //bsm.QueryMode();
        //bsm.FuzzySearch("求解凸优化问题的方法");   // 模糊搜索
        bsm.ExactSearch("求解凸优化问题的方法"); // 精确匹配
    }
}
