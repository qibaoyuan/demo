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
            modelOut = new FileOutputStream(new File("modelOut.txt"));
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

    public void Covert(File rawWiboFolder,File outputFile,String catary){

        try {
            FileReader fin = new FileReader(rawWiboFolder);
            BufferedReader reader = new BufferedReader(fin);
            FileWriter fout = new FileWriter(outputFile,true);
            BufferedWriter writer = new BufferedWriter(fout);
           // PrintWriter pw = new PrintWriter(fout);

           /*int c=0;
            while((c=reader.read())!=-1){
                char x=(char)c;
                writer.write(x+" ");

                //Character[]arrary=s.split();
            }*/
            String s=null;
            while((s=reader.readLine())!=null){
                char[]cArry=s.toCharArray();
                writer.write(catary);
                for(int i=0;i<cArry.length;i++){
                    char c=cArry[i];
                    String sappend=c+"";
                   writer.write(" "+new String(sappend.getBytes("UTF-8"),"GBK"));
                }
                writer.newLine();
            }
            writer.close();
            reader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TrainThread trainThread = new TrainThread();
        File f1 = new File("sample_0.1_1.txt");
        File f2 = new File("sample_0.1_2.txt");
        File f3 = new File("sample_0.1_3.txt");
        File f4 = new File("sample_0.1_4.txt");
        File f5 = new File("sampleOut.txt");

        trainThread.Covert(f1,f5,"愤怒");
        trainThread.Covert(f1,f5,"厌恶");
        trainThread.Covert(f1,f5,"高兴");
        trainThread.Covert(f1,f5,"低落");
       /* try {
            //trainThread.Sentiment();
            trainThread.Sentimented();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}