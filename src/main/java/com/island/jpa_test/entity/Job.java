package com.island.jpa_test.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@javax.persistence.TableGenerator(

        name = "job_gen",              //生成策略的名称

        table = "GENERATOR_TABLE",     //在数据库生成表的名称

        pkColumnName = "pk_key",     //表中第一个字段的字段名 类型为varchar,key

        valueColumnName = "pk_value",    //表中第二个字段的字段名 int ,value

        pkColumnValue = "job",     //这个策略中使用该记录的第一个字段的值(key值)

        initialValue = 0,                //这个策略中使用该记录的第二个字段的值(value值)初始化值

        allocationSize = 1                 //每次使用数据后累加的数值

)
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "job_gen")
    int id;
    @Column
    String name;
    @Column
    String info;
    @ManyToMany(mappedBy = "jobList")
    List<Person> peopleList;

    public Job() {
    }

    public List<Person> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<Person> peopleList) {
        this.peopleList = peopleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
