package hello.foundation;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.*;

/*
    BasicService提供通用的增删改查服务，所有的Service应继承自BasicService
    主要是通过jooq来编写sql语句，并使用JdbcTemplate完成与数据库的交互
    如果有必要，请在Service子类中重写BasicService的方法以完成不同的要求
*/
public abstract class BasicService<T> {

    private JdbcTemplate jdbcTemplate;
    private String tableName;
    private static final SQLDialect dialect=SQLDialect.POSTGRES_9_5;

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource); //初始化jdbc
    }

    public void setTableName(String name){
        this.tableName = name;
    }

    public List<T> findList(Map<String, Object> params, Class<T> clazz){
        String order=(String)params.get("order");
        int limit=(int)params.get("limit");
        int offset=(int)params.get("offset");
        boolean desc=(boolean)params.get("desc");
        params.remove("order");params.remove("limit");params.remove("offset");params.remove("desc");
        DSLContext create= DSL.using(dialect);
        SelectSelectStep select=create.select();
        SelectConditionStep sql=getDSL(select,params);
        Field orderf=DSL.field(order);
        SortField sort=desc?orderf.desc():orderf.asc();
        sql.orderBy(sort).offset(offset);
        if(limit!=-1)sql.limit(limit);
        String strSql=sql.toString();

        return  jdbcTemplate.query(strSql, new BeanPropertyRowMapper<>(clazz));
    }

    public long getCount(Map<String,Object> params){
        long count;
        DSLContext create= DSL.using(dialect);
        SelectSelectStep select=create.selectCount();
        SelectConditionStep sql=getDSL(select,params);
        String strSql=sql.toString();
        count=jdbcTemplate.queryForObject(strSql,long.class);
        return  count;
    }


    public T getRowById(String id, Class<T> clazz){
        T ret = null;
        String sql = "SELECT * FROM " + this.tableName + " where id=?";
        try {
            ret=jdbcTemplate.queryForObject(sql, new Object[]{UUID.fromString(id)}, new BeanPropertyRowMapper<>(clazz));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public void removeRow(String id) {
        String sql = "UPDATE "+ this.tableName +" SET rowstate=1 WHERE id=?";
        jdbcTemplate.update(sql,UUID.fromString(id));
    }

    public void updateRow(String id, Map<String, Object> params){
        params.remove("id");
        params.remove("crtdate");  //移除掉不允许修改的部分
        DSLContext create= DSL.using(dialect);
        HashMap<Field<?>,Field<?>> sets=new HashMap<>();
        for (Object o : params.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            Object val =  entry.getValue();
            if (val == null) {
                continue;
            }
            sets.put(DSL.field(key),DSL.inline(val));
        }
        String sql=create.update(DSL.table(this.tableName)).set(sets).where(DSL.field("id").equal(DSL.inline(id))).getSQL();
        jdbcTemplate.update(sql);
    }

    public T addRow(Class<T> clazz,Map<String, Object> params){
        DSLContext create = DSL.using(SQLDialect.POSTGRES_9_5);
        List<Field<?>> columns = new ArrayList<>();
        List<Field<?>> values = new ArrayList<>();
        String id = (String)params.get("id");

        for (Object o : params.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val == null) {
                continue;
            }
            columns.add(DSL.field(key));
            values.add(DSL.inline(val));
        }
        String sql=create.insertInto(DSL.table(this.tableName)).columns(columns).values(values).getSQL();
        jdbcTemplate.update(sql);
        sql = "SELECT * FROM " + this.tableName + " where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{UUID.fromString(id)}, new BeanPropertyRowMapper<>(clazz));
    }

    private SelectConditionStep getDSL(SelectSelectStep select,Map<String,Object> params) {
        Condition c1 = DSL.field("rowstate").notEqual(1);
        SelectConditionStep sql = select.from(this.tableName).where().and(c1);
        for (Object o : params.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val == null) {
                continue;
            }
            Condition condition;
            switch (key) {  //这部分用于添加日期搜索功能，搜索时间在crtbeg和crtend之间的记录
                case "crtbeg":
                    condition = DSL.field("crtdate").ge(val);
                    break;
                case "crtend":
                    condition = DSL.field("crtdate").le(val);
                    break;
                default:
                    condition = DSL.field(key).eq(val);
                    break;
            }
            sql.and(condition);
        }
        return sql;
    }

    public abstract HashMap<String,Object> getProperties(T t);

}
