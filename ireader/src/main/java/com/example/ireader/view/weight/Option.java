package com.example.ireader.view.weight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/6/16.
 *
 */
public class Option implements Serializable {

    public String key;
    public String value;

    public Option(String key,String value){
        this.key = key;
        this.value = value;
    }

    public static List<Option> getEducationPropertyOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("A","全日制统招"));
        options.add(new Option("B","成人自考"));
        options.add(new Option("C","其他"));
        return options;
    }

    public static List<Option> getEducationOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("A","博士及以上"));
        options.add(new Option("B","硕士"));
        options.add(new Option("C","本科"));
        options.add(new Option("D","大专"));
        options.add(new Option("E","中专及技校"));
        options.add(new Option("F","高中"));
        options.add(new Option("G","初中及以下"));
        return options;
    }

    public static List<Option> getAddrTypeOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("H","家庭地址"));
        options.add(new Option("C","单位地址"));
        return options;
    }

    /**
     * 行业类型
     * @return options
     */
    public static List<Option> getCategoryOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("A","政府机关/社会团体"));
        options.add(new Option("B","军事/公检法"));
        options.add(new Option("C","教育/科研/传媒/体育"));
        options.add(new Option("D","医疗卫生"));
        options.add(new Option("E","水电煤/邮电通信/烟草等公共事业单位"));
        options.add(new Option("F","运输/物流仓储"));
        options.add(new Option("G","建筑业/室内外装饰"));
        options.add(new Option("H","制造业/轻工/机械机电"));
        options.add(new Option("I","开采冶炼/化工"));
        options.add(new Option("J","银行/保险/公募基金等金融业"));
        options.add(new Option("K","互联网金融/小贷/典当/贵金属"));
        options.add(new Option("L","互联网/其他高新技术行业"));
        options.add(new Option("M","注册会计师/律师/设计等专业事务所"));
        options.add(new Option("N","商业/贸易/批发零售"));
        options.add(new Option("O","餐饮/酒店/旅游/娱乐等服务业"));
        options.add(new Option("P","无业/自由职业/农业/其他"));
        options.add(new Option("Q","家庭主妇"));
        options.add(new Option("R","在校大学生"));
        return options;
    }

    /**
     * 公司性质
     * @return options
     */
    public static List<Option> getIndustryStructureOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("A","个体户"));
        options.add(new Option("B","股份制"));
        options.add(new Option("C","政府部分"));
        options.add(new Option("D","私有企业"));
        options.add(new Option("E","跨国公司"));
        return options;
    }

    /**
     * 企业类型
     * @return options
     */
    public static List<Option> getCompanyStructureOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("A","政府机关事业单位/财富500强企业/境内上市公司"));
        options.add(new Option("B","国有独资"));
        options.add(new Option("C","境外上市公司/民营大型集团公司"));
        options.add(new Option("D","民营小微企业"));
        options.add(new Option("E","个体/其他"));
        return options;
    }

    /**
     * 职业类型
     * @return options
     */
    public static List<Option> getProfessionStructureOptions(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("A","企业高管/副处级以上官员"));
        options.add(new Option("B","企业中层管理人员/技术专家"));
        options.add(new Option("C","企业基层管理人员/公务员"));
        options.add(new Option("D","办公室职员/专员"));
        options.add(new Option("E","外包职员/外勤/销售员/客服坐席/文员"));
        options.add(new Option("F","一线工人/生产线管理/服务人员"));
        options.add(new Option("G","学生/主妇/无业/自由业/其他"));
        return options;
    }
}
