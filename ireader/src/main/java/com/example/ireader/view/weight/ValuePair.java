package com.example.ireader.view.weight;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ValuePair implements Serializable {
    public String key;
    public String value;

    public ValuePair (){

    }

    public ValuePair (String key,String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValuePair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
