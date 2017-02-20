package com.example.ireader.view.weight;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MetaItem implements Serializable {
    public String sequence;
    public String fieldCode;
    public String fieldName;
    public String isRequired;
    public int[]  state={0,0,0};//图片上传状态 0成功  负数失败个数  正数为成功个数
    public int index=-1;//下标值
    /**
     文本=text
     数字=number
     日期=date
     下拉框（含枚举值）=dropDown
     图片=image
     文件=file
     省市区=select
     url=验签
     sms=短信
     OCR=识别
     */
    public String fieldType;
    public List<ValuePair> fillValues;
    public String fieldValue;
    public boolean fieldEditable = true;


    /**
     * 自定义属性
     */
    // 控件是否隐藏
    public boolean isVisible = true;

    public MetaItem(){
        fieldCode = "";
        fieldName = "";
        isRequired = "";
        fieldType = "";
        fillValues = new ArrayList<ValuePair>();
        fieldValue = "";
        //tmpValues = new ArrayList<ValuePair>();
    }

    /**
     * 诊断值是否正确
     * */
    public boolean asset(){
        if (isRequired != null && isRequired.equals("true") && TextUtils.isEmpty((CharSequence) fieldValue)){
            return false;
        }
        return true;
    }


    /**
     * 获取安全值
     * */
    public String safeValue(){
        return fieldValue!=null?(String)fieldValue:"";
    }

    @Override
    public String toString() {
        return "MetaItem{" +
                "sequence='" + sequence + '\'' +
                ", fieldCode='" + fieldCode + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", isRequired='" + isRequired + '\'' +
                ", state=" + Arrays.toString(state) +
                ", index=" + index +
                ", fieldType='" + fieldType + '\'' +
                ", fillValues=" + fillValues +
                ", fieldValue='" + fieldValue + '\'' +
                ", fieldEditable=" + fieldEditable +
                ", isVisible=" + isVisible +
                '}';
    }
}
