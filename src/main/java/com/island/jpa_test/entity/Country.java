package com.island.jpa_test.entity;

import javax.persistence.*;

@Entity
@Table(name = "country")

@javax.persistence.TableGenerator(

        name="country_gen",              //生成策略的名称

        table="GENERATOR_TABLE",     //在数据库生成表的名称

        pkColumnName = "pk_key",     //表中第一个字段的字段名 类型为varchar,key

        valueColumnName = "pk_value",    //表中第二个字段的字段名 int ,value

        pkColumnValue="country",     //这个策略中使用该记录的第一个字段的值(key值)

        initialValue = 1,                //这个策略中使用该记录的第二个字段的值(value值)初始化值

        allocationSize=1                 //每次使用数据后累加的数值

)
public class Country {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE,generator="country_gen")
    int country_id;
    @Column
    String country_name;


    public Country() {
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }


}
