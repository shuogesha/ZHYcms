package cms;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class messaheTest {
    public static void main(String[] args) {  
        MessageSource resources = new ClassPathXmlApplicationContext("classpath:servlet-front.xml");  
        Locale locale = new Locale("zh", "CN");
        
        String message = resources.getMessage("tpl.index", null, locale);  
        System.out.println(message);  
    }  
}
