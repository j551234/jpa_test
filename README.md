###### tags: `spring`

# Spring data jpa

## spring.jpa.hibernate.ddl-auto

* create：
 每次加載hibernate時(啟動）都刪除上一次的生成的表，然後根據你的model類再重來生成新表，這表明沒有任何改變也要執行，導致導致數據庫表數據丟失的一個重要原因。
* create-drop ：
 每次加載hibernate時根據模型類生成表，但是sessionFactory一關閉，表就自動刪除。
* update：
 最常用的屬性，第一次加載hibernate時根據模型類會自動建立起表的結構（前提是先建立好數據庫），以後加載hibernate時根據模型類自動更新表結構，即使表結構改變了但表中的行繼續存在不會刪除以前的行。要注意是當部署到服務器後，表結構是不會被馬上啟動的，是要等應用第一次運行後會。
* validate ：
 每次加載休眠時，驗證創建數據庫表結構，創建新數據庫表時，都會進行新表比較，不會創建新表，但會插入新值。

## Id 
在pk的生成方式，可以藉由GeneratedValue來自動生成
  @Id
  @GeneratedValue(strategy)
  策略可以選擇 
  |GenerationType|生成方式|
  |------|------|
  |indentify |資料庫生成，autoicrement，某些資料庫不支援 |
  | table| 自行定義並存放於資料庫table|
  |auto|會自行決定最適合的方式，會在db產生hibernate_sequence table並共用順序|

--- 

 table 範例：
```java=
@Entity
@javax.persistence.TableGenerator(

        name="job_gen",              //pk 生成策略名稱 ，下面要引用

        table="GENERATOR_TABLE",     // 在db 紀錄所有pk現在值的table

        pkColumnName = "pk_key",     // pk 名稱欄位

        valueColumnName = "pk_value",    //  pk 值欄位

        pkColumnValue="job",     // 紀錄 generator 在 pk table 中的欄位名稱

        initialValue = 0,                //pk 開始值

        allocationSize=1                 // 每次pk增加值

)
public class Job {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE,generator="job_gen")
    int id;
    @Column
    String name;
    @Column
    String info;
    ......geter setter............
}

```

---

## One to One
 Ex:一個人(Person)只能有一組帳號(Account)
 Person
```java=
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column
    String name;
    @Column
    String info;

    // one me just have one country
    @OneToOne(cascade = CascadeType.ALL)
    // front is column will create in person table , next is the foreign key in country table
    @JoinColumn(name = "gg_account_id", referencedColumnName = "account_id")
    Account account;

......geter setter............
}

```
---
在負責建立關系的entity上面放
＠OneToOne
可設定關聯操作會在負責建立關聯的table新增對應的欄位
@JoinColumn(name = "gg_account_id", referencedColumnName = "account_id")
join column 可設定關聯欄位資訊，name為table(person)
中生成的foreign key 名稱,referencedColumnName則為關聯的table(account)裡要對應的欄位，若不設定會以pk作為關聯

---

 Account
```java=
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    int account_id;
    @Column
    String password;
    @Column
    String description;
// get attributer in person.account to mapping account object
    @OneToOne(mappedBy = "account")
    Person person;

.....getter setter........
}


```
---
若要建立雙向關聯則要在被關聯的entity加上
＠OneToOne(mappedBy ="account")
Person person;
代表會以 person裡面的account物件去做mapping
即可從account關聯至person物件



## OneToMany , ManyToOne
Ex: 一個國家擁有很多城市
Country:

```java=
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "country_gen")
    int country_id;
    @Column
    String country_name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    List<City> cities = new ArrayList<>();

```
在oneToMany端設定mappedBy，會指定city 裡面的country 為對應mapping



City

```java=
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue
    int city_id;

    @Column
    String city_name;
    @Column
    String description;

    @ManyToOne
    Country country;
```

會在 city table 產生foreign key(country_id)




## ManyToMany
人(person)跟工作(job)為多對多關西
```java=
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column
    String name;
    @Column
    String info;

    // one  just have one country
    @OneToOne(cascade = CascadeType.ALL)
    // front is column will create in person table , next is the foreign key in country table
    @JoinColumn(name = "gg_account_id", referencedColumnName = "account_id")
    Account account;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "person_job", joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    List<Job> jobList;

```
會額會產生關聯table，所以需要在一方設定jointable屬性，
jointable ,joincolumn與mappedby屬性為互斥


```java=
@Entity
@Table(name = "job")
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
```

---

## CascadeType
| CascadeType | 操作 | 
| -------- | -------- | 
| CascadeType.PERSIST     | 在儲存時一併儲存 被參考的物件。     | 
|CascadeType.MERGE |	在合併修改時一併 合併修改被參考的物件。|
|CascadeType.REMOVE	|在移除時一併移除 被參考的物件。|
|CascadeType.REFRESH	|在更新時一併更新 被參考的物件。|
|CascadeType.ALL	|無論儲存、合併、 更新或移除，一併對被參考物件作出對應動作。|

---

## FetchType
fetchtype會決定資料關聯的撈取方式，
若是lazy 會等到該屬性呼叫get方法才去做select動作
|關聯方式 | FetchType|
|------|------|
|@Basic	|FetchType.EARGE|
|@OneToOne	|FetchType.EARGE|
|@ManyToOne|	FetchType.EARGE|
|@OneToMany	|FetchType.LAZY|
|@ManyToMany|	FetchType.LAZY|

---

## @Trasient /@Basic
@Trasient會忽略欄位不做table的mapping
@Basic欄位為預設
@Column(name="test_column")
不能使用駝峰它會做自動轉換成_分隔

## @Temperoal
可指定欄位存放時間類型date,time, timestamp

---


## entity to dto 
```java=
   BeanUtils.copyProperties(source,target);
   
```

使用注意事項

1. 由於是利用反射來達到複製的效果，因此效能上會比手動寫set方法來得差。
1. 屬性的命名上必須統一，所以系統中各屬性的命名規則必須嚴格規範。
1. 不好除錯，例如屬性中有Enum型態，但資料庫為字串等型態不一致，或是Entity類與DTO類命名不一致等也會造成屬性結果為null的情況。

Ref:https://matthung0807.blogspot.com/2019/10/spring-beanutilscopyproperties-to-copy.html

---
