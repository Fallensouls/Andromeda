package hello.init;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by JiangYueSong on 2017/4/5 0005.
 *
 */
@ConfigurationProperties(prefix = "uvlab.foundation.node")
public class JxCustomConfigure {
    /**
     * data root folder
     */
    private String dataRoot=JxAppFoundation.DATA_ROOT;
    /**
     * data tmp save folder
     */
    private String dataTemp=JxAppFoundation.DATA_TEMP;
    /**
     * wiki picture save path
     */
    private String nodeUrl =JxAppFoundation.NODE_URL;

    private String wikiPicture=JxAppFoundation.WIKI_PICTURE;
    /**
     * news picture save path
     */
    private String newsPicture=JxAppFoundation.NEWS_PICTURE;
    /**
     * dataset root node id
     */
    private String datasetNode=JxAppFoundation.DATASET_NODE;

    /**
     * kafka server cluster host:port pairs
     */
    private String kafkaBootstrapServers=JxAppFoundation.KAFKA_BOOTSTRAP_SERVERS;

    private String fileNodeUrl=JxAppFoundation.FILE_NODE_URL;

    private String fileNodeMdUrl=JxAppFoundation.FILE_NODE_MD_URL;

    public String getDataRoot() {
        return dataRoot;
    }

    public void setDataRoot(String dataRoot) {
        this.dataRoot = dataRoot;
    }

    public String getDataTemp() {
        return dataTemp;
    }

    public void setDataTemp(String dataTemp) {
        this.dataTemp = dataTemp;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getFileNodeUrl() {
        return fileNodeUrl;
    }

    public void setFileNodeUrl(String fileNodeUrl) {
        this.fileNodeUrl = fileNodeUrl;
    }

    public String getFileNodeMdUrl() {
        return fileNodeMdUrl;
    }

    public void setFileNodeMdUrl(String fileNodeMdUrl) {
        this.fileNodeMdUrl = fileNodeMdUrl;
    }

    public String getWikiPicture() {
        return wikiPicture;
    }

    public void setWikiPicture(String wikiPicture) {
        this.wikiPicture = wikiPicture;
    }

    public String getNewsPicture() {
        return newsPicture;
    }

    public void setNewsPicture(String newsPicture) {
        this.newsPicture = newsPicture;
    }

    public String getDatasetNode() {
        return datasetNode;
    }

    public void setDatasetNode(String datasetNode) {
        this.datasetNode = datasetNode;
    }

    public String getKafkaBootstrapServers() {
        return kafkaBootstrapServers;
    }

    public void setKafkaBootstrapServers(String kafkaBootstrapServers) {
        this.kafkaBootstrapServers = kafkaBootstrapServers;
    }
}
