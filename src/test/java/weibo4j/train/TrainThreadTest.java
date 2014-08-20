package weibo4j.train;

import junit.framework.TestCase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TrainThreadTest extends TestCase {

    public void testSentimented() throws Exception {

    }

    public void testCovert() throws Exception {

    }

    public void testConvertDataSet() throws Exception {
        TrainThread trainThread = new TrainThread();
        Path dataFolderPath = Paths.get("/Users/qibaoyuan/Documents/sina_tweets_sentiment_category_sample");
        Path destinationFilePath = Paths.get("/Users/qibaoyuan/Documents/sina_tweets_sentiment_category_sample/sample.txt");

        trainThread.convertDataSet(dataFolderPath, destinationFilePath);

    }

    public void testSplit() {
        String str = "测试123";
        Arrays.asList(str.split("")).stream().forEach(x -> System.out.print(x + " "));
    }

    public void testMain() throws Exception {

    }
}