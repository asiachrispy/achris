package com.chris.algo.svd;

import java.io.*;
import java.util.Random;

/**
 * User: zhong.huang
 * Date: 13-7-3
 */
public class SVDTool {
    public static final String configureFile = "D:\\dj\\trunk\\algorithm\\dj-crawler\\algo\\src\\main\\python\\svd\\svd.conf";
    public static final String trainDataFile = "D:\\dj\\trunk\\algorithm\\dj-crawler\\algo\\src\\main\\python\\svd\\ml_data\\training.txt";
    public static final String testDataFile = "D:\\dj\\trunk\\algorithm\\dj-crawler\\algo\\src\\main\\python\\svd\\ml_data\\test.txt";
    public static final String modelSaveFile = "D:\\dj\\trunk\\algorithm\\dj-crawler\\algo\\src\\main\\python\\svd\\svd_model.pkl";
    public static final String resultSaveFile = "D:\\dj\\trunk\\algorithm\\dj-crawler\\algo\\src\\main\\python\\svd\\prediction.txt";

    private static int userNum = 0;
    private static int itemNum = 0;
    private static int factorNum = 0;
    private static double learnRate = 0.0d;
    private static double averageScore = 0.0d;
    private static double regularization = 0.0d;

    private static double[] bi = null;
    private static double[] bu = null;
    private static double[][] qi = null;
    private static double[][] pu = null;

    public SVDTool() {
        init();
    }

    private void init() {
        try {
            FileReader fr = new FileReader(configureFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] arr = line.split(" ");
            averageScore = Double.valueOf(arr[0].trim());
            userNum = Integer.valueOf(arr[1].trim());
            itemNum = Integer.valueOf(arr[2].trim());
            factorNum = Integer.valueOf(arr[3].trim());
            learnRate = Double.valueOf(arr[4].trim());
            regularization = Double.valueOf(arr[5].trim());
            br.close();
            fr.close();

            bi = new double[itemNum];
            bu = new double[userNum];
            double temp = Math.sqrt(factorNum);

            qi = new double[itemNum][factorNum];
            for (int i = 0; i < itemNum; i++) {
                for (int j = 0; j < factorNum; j++) {
                    qi[i][j] = 0.1 * random() / temp;
                }
            }

            pu = new double[userNum][factorNum];
            for (int i = 0; i < userNum; i++) {
                for (int j = 0; j < factorNum; j++) {
                    pu[i][j] = 0.1 * random() / temp;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //calculate the overall average
    private double average(String fileName) {
        double result = 0.0f;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            int cnt = 0;
            while (br.ready()) {
                cnt++;
                line = br.readLine();
                result = result + Integer.valueOf(line.split(" ")[2].trim());
            }
            br.close();
            fr.close();
            return result / cnt;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private double inerProduct(double[] v1, double[] v2) {
        double result = 0;
        if (v1.length == 0 || v2.length == 0) {
            return result;
        }
        int size = Math.min(v1.length, v2.length);
        for (int i = 0; i < size; i++) {
            result = result + v1[i] * v2[i];
        }
        return result;
    }

    private double predictScore(double av, double bu, double bi, double[] pu, double[] qi) {
        double pScore = av + bu + bi + inerProduct(pu, qi);
        if (pScore < 1) {
            pScore = 1;
        } else if (pScore < 5) {
            pScore = 5;
        }
        return pScore;
    }

    private static double random() {
        Random r = new Random();
        return r.nextDouble();
    }

    private void svd(String testDataFile, String trainDataFile, String modelSaveFile) {
        try {
            //train model
            double preRmse = 1000000.0;

            int uid = 0;
            int iid = 0;
            double eui = 0.0d;
            double temp = 0.0d;
            double score = 0.0d;
            double prediction = 0.0d;
            String line = "";
            String[] arr = null;
            FileReader fr = null;
            BufferedReader br = null;
            for (int i = 0; i < 100; i++) {
                fr = new FileReader(trainDataFile);
                br = new BufferedReader(fr);

                while (br.ready()) {
                    line = br.readLine();
                    arr = line.split("    ");
                    uid = Integer.valueOf(arr[0].trim()) - 1;
                    iid = Integer.valueOf(arr[1].trim()) - 1;
                    score = Double.valueOf(arr[2].trim());
                    //System.out.println(uid + "--" + iid);
                    prediction = predictScore(averageScore, bu[uid], bi[iid], pu[uid], qi[iid]);
                    eui = score - prediction;
                    //update parameters
                    bu[uid] += learnRate * (eui - regularization * bu[uid]);
                    bi[iid] += learnRate * (eui - regularization * bi[iid]);

                    for (int k = 0; k < factorNum; k++) {
                        temp = pu[uid][k];
                        //attention here, must save the value of pu before updating
                        pu[uid][k] += learnRate * (eui * qi[iid][k] - regularization * pu[uid][k]);
                        qi[iid][k] += learnRate * (eui * temp - regularization * qi[iid][k]);
                    }
                }
                br.close();
                fr.close();

                //learnRate *= 0.9
                double curRmse = validate(testDataFile, averageScore, bu, bi, pu, qi);
                if (curRmse >= preRmse) {
                    break;
                } else {
                    preRmse = curRmse;
                }
            }
            //write the model to files
            //FileWriter fw = new FileWriter(modelSaveFile);
            //BufferedWriter bw = new BufferedWriter(fw);
            //todo write
            //bw.close();
            //fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
        }
    }

    //validate the model
    private double validate(String testDataFile, double av, double[] bu, double[] bi, double[][] pu, double[][] qi) {
        int cnt = 0;
        double rmse = 0.0d;
        try {
            FileReader fr = new FileReader(testDataFile);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String[] arr = null;
            double pScore = 0d;
            double tScore = 0d;
            int uid = 0;
            int iid = 0;
            while (br.ready()) {
                cnt++;
                line = br.readLine();
                arr = line.split("    ");
                uid = Integer.valueOf(arr[0].trim()) - 1;
                iid = Integer.valueOf(arr[1].trim()) - 1;
                tScore = Integer.valueOf(arr[2].trim());

                pScore = predictScore(av, bu[uid], bi[iid], pu[uid], qi[iid]);
                rmse += (tScore - pScore) * (tScore - pScore);
            }
            br.close();
            fr.close();
            return Math.sqrt(rmse / cnt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
        }
        return 0.0d;
    }

    private void predict(String testDataFile, String resultSaveFile) {
        try {
            System.out.println("predict start");
            // get parameter
            // get model
            // predict
            FileReader fr = new FileReader(testDataFile);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(resultSaveFile);
            BufferedWriter bw = new BufferedWriter(fw);

            String line = "";
            String[] arr = null;
            int uid = 0;
            int iid = 0;
            double pScore = 0.0d;
            while (br.ready()) {
                line = br.readLine();
                arr = line.split("    ");
                uid = Integer.valueOf(arr[0].trim()) - 1;
                iid = Integer.valueOf(arr[1].trim()) - 1;
                pScore = predictScore(averageScore, bu[uid], bi[iid], pu[uid], qi[iid]);
                bw.write(pScore + "");
                bw.newLine();
            }

            br.close();
            fr.close();
            bw.flush();
            bw.close();
            fw.close();
            System.out.println("predict over");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
        }
    }

    public static void main(String[] args) {
        SVDTool svd = new SVDTool();
//        System.out.print(">>av" + Average("ua.base"));
        svd.svd(testDataFile, trainDataFile, modelSaveFile);
        svd.predict(testDataFile, resultSaveFile);
    }

}