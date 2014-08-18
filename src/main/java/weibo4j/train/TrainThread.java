package weibo4j.train;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.*;


/**
 * Created by yangxu on 2014/8/18.
 */
public class TrainThread extends Thread {

    public void Sentimented() {

        OutputStream modelOut = null;
        try {
            InputStream dataIn = new FileInputStream("sample.txt");
            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream);
            modelOut = new FileOutputStream(new File("model.out"));
            model.serialize(modelOut);
        } catch (IOException e) {
            // Failed to save modela
            e.printStackTrace();
        } finally {
            if (modelOut != null) {
                try {
                    modelOut.close();
                } catch (IOException e) {
                    // Failed to correctly save model.
                    // Written model might be invalid.
                    e.printStackTrace();
                }
            }
        }


    }

    public static void main(String[] args) {
        TrainThread trainThread = new TrainThread();
        try {
            //trainThread.Sentiment();
            trainThread.Sentimented();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}