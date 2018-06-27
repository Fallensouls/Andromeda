package hello.init;

import java.nio.file.Paths;

public class JxAppFoundation{

    public static final String DATA_ROOT = Paths.get(System.getProperty("User.dir"),"Data").toString();
    public static final String DATA_TEMP =Paths.get(System.getProperty("User.dir"),"Data","temp").toString();
    public static final String WIKI_PICTURE = Paths.get(DATA_ROOT,"wiki","picture").toString();
    public static final String NEWS_PICTURE = Paths.get(DATA_ROOT,"news","picture").toString();
    public static final String DATASET_NODE ="73826c12-ee94-4c7a-8044-fcb2bfd6f9fe";
    public static final String KAFKA_BOOTSTRAP_SERVERS="localhost:9092";
    public static final String NODE_URL ="http://localhost:8085";
    public static final String FILE_NODE_URL ="http://localhost:9002";
    public static final String FILE_NODE_MD_URL ="http://localhost:8092";

    public static final String NDB_ROOT = "0fa2674b-7893-4804-9e6e-69ac59c42603";
    private JxAppFoundation(){
        
    }
}