package org.defaults;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "./data/GWT/processed";
        BoolSearchModel bsm = new BoolSearchModel();
        bsm.setProc(path);
        bsm.File2HashMap();

        //System.out.println(bsm.FuzzySearch("奖学金 2022 校长 蔡元哲 陈海鹏"));  // 模糊搜索
        //System.out.println(bsm.FuzzySearch("硕士 研究生 奖学金 公布"));   // 模糊搜索
        //System.out.println(bsm.FuzzySearch("大数据与互联网学院2022 硕士研究生"));
        System.out.println(bsm.FuzzySearch("大赛 报名 获奖通知"));
    }
}