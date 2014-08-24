package weibo4j.train;

import junit.framework.TestCase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TrainThreadTest extends TestCase {

    /*public void testSentimented() throws Exception {

    }

    public void testCovert() throws Exception {

    }

    public void testConvertDataSet() throws Exception {
        TrainThread trainThread = new TrainThread();
        Path dataFolderPath = Paths.get("D:/sina_tweets_sentiment_category_sample");
        Path destinationFilePath = Paths.get("D:/sina_tweets_sentiment_category_sample/sample.txt");

        trainThread.convertDataSet(dataFolderPath, destinationFilePath);

    }*/

    public void testSplit() {
        String str = "测试123";
        Arrays.asList(str.split("")).stream().forEach(x -> System.out.print(x + " "));
    }

    /*public void testMain() throws Exception {

    }*/
    public void testTrainModel() {
        Path destinationFilePath = Paths.get("D:/sina_tweets_sentiment_category_sample/sample.txt");
        Path outModelPath = Paths.get("D:/sina_tweets_sentiment_category_sample/model.out");
        TrainThread trainThread = new TrainThread();
        trainThread.trainModel(destinationFilePath, outModelPath);
    }

    public void testOutputClassificationResult(){
        String text = "亲身经验告诉大家，搜饭店评价和美食推荐简直是满满惊喜！";
        Path modelPath = Paths.get("D:/sina_tweets_sentiment_category_sample/model.out");
        TrainThread trainThread = new TrainThread();
        System.out.println(trainThread.outputClassificationResult(text,modelPath));
    }
}