package weibo4j.train;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yangxu on 2014/8/18.
 */
public class TrainThread {
    private static Map<String, String> nameMap = new HashMap();

    public TrainThread() {
        nameMap.put("sample_0.1_1.txt", "愤怒");
        nameMap.put("sample_0.1_2.txt", "厌恶");
        nameMap.put("sample_0.1_3.txt", "高兴");
        nameMap.put("sample_0.1_4.txt", "低落");
    }


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

    public void Covert(File rawWiboFolder, File outputFile, String catary) {

        try {
            FileReader fin = new FileReader(rawWiboFolder);
            BufferedReader reader = new BufferedReader(fin);
            FileWriter fout = new FileWriter(outputFile, true);
            BufferedWriter writer = new BufferedWriter(fout);
            // PrintWriter pw = new PrintWriter(fout);

           /*int c=0;
            while((c=reader.read())!=-1){
                char x=(char)c;
                writer.write(x+" ");

                //Character[]arrary=s.split();
            }*/
            String s = null;
            while ((s = reader.readLine()) != null) {
                char[] cArry = s.toCharArray();
                writer.write(catary);
                for (int i = 0; i < cArry.length; i++) {
                    char c = cArry[i];
                    String sappend = c + "";
                    writer.write(" " + new String(sappend.getBytes("UTF-8"), "GBK"));
                }
                writer.newLine();
            }
            writer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模型训练
     *
     * @param trainFilePath 训练语料的path
     * @param modelPath     训练后模型的存放位置
     */
    public void trainModel(Path trainFilePath, Path modelPath) {
        OutputStream modelOut = null;
        try {
            ObjectStream<String> lineStream = new PlainTextByLineStream(Files.newInputStream(trainFilePath), "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream);
            modelOut = Files.newOutputStream(modelPath);
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

    /**
     * 找出句子的分类
     *
     * @param text  需要分类的句子
     * @param modelPath  训练模型的存放位置
     * @return 分类
     */
    public String outputClassificationResult(String text,Path modelPath){
        String category="";
        try {
            InputStream is = Files.newInputStream(modelPath);
            DoccatModel m = new DoccatModel(is);
            String inputText =text;
            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
            double[] outcomes = myCategorizer.categorize(inputText);
            category = myCategorizer.getBestCategory(outcomes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return category;
    }
    /**
     * 产生最大熵模型需要的语料
     *
     * @param dataFolderPath      原始语料的文件夹
     * @param destinationFilePath 产生的语料的地址
     * @throws Exception
     */
    public void convertDataSet(Path dataFolderPath, Path destinationFilePath) throws Exception {
        BufferedWriter bw = Files.newBufferedWriter(destinationFilePath);
        int[] idx = {0};
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dataFolderPath, "{sample_0.1*}")) {
            for (Path path : ds) {
                System.out.println(path.toAbsolutePath().getFileName());
                Files.lines(path).forEach(line -> {
                    try {
                        bw.write(nameMap.get(path.toAbsolutePath().getFileName().toString()));
                        bw.write(" ");
                        StringBuffer tmp = new StringBuffer(line.length() * 2);
                        Arrays.asList(line.split("")).stream().forEach(x -> tmp.append(x).append(" "));
                        bw.write(tmp.toString());
                        bw.newLine();
                        if (idx[0]++ % 1000 == 0) bw.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bw.flush();
        bw.close();
    }


    /*public static void main(String[] args) {
        TrainThread trainThread = new TrainThread();
        File f1 = new File("sample_0.1_1.txt");
        File f2 = new File("sample_0.1_2.txt");
        File f3 = new File("sample_0.1_3.txt");
        File f4 = new File("sample_0.1_4.txt");
        File f5 = new File("sampleOut.txt");

        trainThread.Covert(f1, f5, "愤怒");
        trainThread.Covert(f1, f5, "厌恶");
        trainThread.Covert(f1, f5, "高兴");
        trainThread.Covert(f1, f5, "低落");
       *//* try {
            //trainThread.Sentiment();
            trainThread.Sentimented();
        } catch (Exception e) {
            e.printStackTrace();
        }*//*

    }*/
}