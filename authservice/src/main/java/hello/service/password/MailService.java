package hello.service.password;

import hello.foundation.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;

@Service
public class MailService extends BasicService<Mail> {

    @Autowired
    public MailService(DataSource dataSource){
        this.setDataSource(dataSource);
        this.setTableName("mail");
    }

    public HashMap<String,Object> getProperties(Mail mail) {
        // 将user对象的值填充到key-value形式的HashMap内
        HashMap<String,Object> params=new HashMap<>();
//        params.put("id", user.getId().toString());
        params.put("username",mail.getUsername());
        params.put("sid",mail.getSid());
        params.put("out_time",mail.getOut_time().toString());
        return params;
    }

    public Mail findMailByUsername(String username){
        Mail mail = null;
        try {
            mail = this.getJdbcTemplate().queryForObject("SELECT * FROM mail WHERE username=?",
                    new Object[]{username}, new BeanPropertyRowMapper<>(Mail.class));
        }
        catch (Exception e){
            System.out.println("无相关记录！");
        }
        return mail;
    }

}
