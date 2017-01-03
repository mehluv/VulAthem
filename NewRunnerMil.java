
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author luv
 */
public class NewRunnerMil
{

    static double rmse;
    static double mae;
    static int noOfTestRatings;
    static protected final double trainTestRatio;
    /**
     * Multiplicative decay factor for learning_rate
     */
    static protected final double learningRateDecay;
    /**
     * Learning rate (step size)
     */
    static protected final double learningRate;
    /**
     * Parameter used to prevent overfitting.
     */
    static protected final double preventOverfitting;
    /**
     * Number of iterations
     */
    static private final int noOfIterations;
    /**
     * Standard deviation for random initialization of features
     */
    static protected final double randomNoise;

    static int noOfFeatures;

    static
    {
	trainTestRatio = 0.7;
	noOfFeatures = 12;
	learningRateDecay = 1.0;
	noOfIterations = 1000;
	learningRate = 0.01;
	preventOverfitting = 0.1;
	randomNoise = 0.01;
    }

    static void work(File f, int index) throws Exception
    {
	BufferedReader br = new BufferedReader(new FileReader(f));
	String line = br.readLine();
	int pre = -1;
	int dimRow = 0;
	//this will contain all the users in a single file
	ArrayList<Integer> userList = new ArrayList<>();
	while (line != null)
	{
	    String s[] = line.split(" ");
	    int comp1 = Integer.parseInt(s[1]) - 1;
	    if (comp1 != pre)
	    {
		pre = comp1;
		dimRow++;
	    }
	    line = br.readLine();
	}
	System.out.println("Number of users in cluster:- " + dimRow);
	double[][] msub = new double[dimRow][3952]; //change the shit
	//resetting the file pointer
	br = new BufferedReader(new FileReader(f));
	line = br.readLine();
	pre = -1;
	int rowPtr = -1;
	//read in all the values
	while (line != null)
	{
	    String[] sa = line.split(" ");
	    int temp = Integer.parseInt(sa[1]) - 1;
	    if (pre != temp)
	    {
		rowPtr++;
		pre = temp;
		userList.add(pre);
	    }
	    msub[rowPtr][Integer.parseInt(sa[2]) - 1] = Integer.parseInt(sa[3]);
	    line = br.readLine();

	}
	System.out.println(msub.length + " " + msub[0].length);
	ArrayList[] definedList = new ArrayList[dimRow];
	int ctr = 0;
	for (int i = 0; i < msub.length; i++)
	{
	    definedList[i] = new ArrayList<Integer>();
	    for (int j = 0; j < msub[0].length; j++)
	    {
		if (msub[i][j] != 0)
		{
		    definedList[i].add(j);
		}
	    }
	}
	Date d = new Date();
	long b = d.getTime();
	System.out.println("Finding resultant prediction matrix:-");
	//Matrix res = ukskh.times(skhvk);
	ArrayList<Integer>[] trainingSet = new ArrayList[msub.length];
	ArrayList<Integer>[] testSet = new ArrayList[msub.length];
	testDataSelection(definedList, trainingSet, testSet);
	double[][] uMat = new double[dimRow][noOfFeatures];
	double[][] vMat = new double[noOfFeatures][3952];
	train(uMat, vMat, msub, trainingSet, testSet);
	//Matrix res1 = uk.times(sk.times(vk.transpose()));
	Matrix res = new Matrix(uMat).times(new Matrix(vMat));
	for (int j = 0; j < res.getColumnDimension(); j++)
	{
	    for (int i = 0; i < res.getRowDimension(); i++)
	    {
		double a = res.get(i, j);
		if (a < 1.0)
		{
		    a = 1.0;
		}
		else if (a > 5.0)
		{
		    a = 5.0;
		}
		res.set(i, j, a);
	    }
	}
	long a = new Date().getTime() - b;

	f = new File("temp/predictedMatrixFor1MDiv" + index + ".dat");
	PrintWriter pw = new PrintWriter(f);
	for (Integer i : userList)
	{
	    pw.print(i + " ");
	}
	pw.println();
	System.out.println("===");
	System.out.println(res.get(0, 0));
	System.out.println("===");
	res.print(pw, 4, 3);
	pw.flush();
	System.out.println();

	System.out.println("Time taken:- " + a / 1000);
    }

    static void testDataSelection(ArrayList<Integer>[] definedList, ArrayList<Integer>[] trainingSet, ArrayList<Integer>[] testSet)
    {
	Random r = new Random();
	for (int i = 0; i < definedList.length; i++)
	{
	    trainingSet[i] = new ArrayList<>();
	    testSet[i] = new ArrayList<>();
	    TreeSet<Integer> tempHolder = new TreeSet<>();
	    int size = definedList[i].size();
	    int trainingSize = (int) (trainTestRatio * size);
	    while (tempHolder.size() <= trainingSize)
	    {
		tempHolder.add(definedList[i].get(r.nextInt(size)));
	    }
	    for (int j : definedList[i])
	    {
		if (tempHolder.contains(j))
		{
		    trainingSet[i].add(j);
		}
		else
		{
		    testSet[i].add(j);
		}
	    }
	}
    }

    static void train(double[][] uMat, double[][] vMat, double[][] msub, ArrayList<Integer>[] trainingSet, ArrayList<Integer>[] testSet)
    {
	//we initialize the matrices here
	Random r = new Random();
	for (int i = 0; i < uMat.length; i++)
	{
	    for (int j = 0; j < uMat[0].length; j++)
	    {
		uMat[i][j] = r.nextGaussian() * randomNoise;
	    }
	}
	for (int i = 0; i < vMat.length; i++)
	{
	    for (int j = 0; j < vMat[0].length; j++)
	    {
		vMat[i][j] = r.nextGaussian() * randomNoise;
	    }
	}
	int ctr;
	double currentLearningRate = learningRate;
	for (int iter = 0; iter < noOfIterations; iter++)
	{
	    //System.out.println(iter);
	    ctr = 0;
	    for (ArrayList<Integer> al : trainingSet)
	    {
		for (Integer k : al)
		{
		    //System.out.println("==========");
		    for (int feature = 0; feature < noOfFeatures; feature++)
		    {

			double err;
			double predRating = 0;
			for (int i = 0; i < noOfFeatures; i++)
			{
			    predRating += uMat[ctr][i] * vMat[i][k];//add the cAv here
			}
			//System.out.println(predRating);
			err = msub[ctr][k] - predRating;
			//double uNew = uMat[ctr][feature] + vMat[feature][k] * err * currentLearningRate;
			//double vNew = vMat[feature][k] + uMat[ctr][feature] * err * currentLearningRate;
			//System.out.println(err);
			double delU = 2 * currentLearningRate * (err * vMat[feature][k] - preventOverfitting * uMat[ctr][feature]);
			double delV = 2 * currentLearningRate * (err * uMat[ctr][feature] - preventOverfitting * vMat[feature][k]);
			uMat[ctr][feature] += delU;
			vMat[feature][k] += delV;
			/*u.set(ctr, feature, u.get(ctr,feature)*err*lRate);
			 v.set(feature, k, v.get(feature,k)*err*lRate);*/
		    }

		}
		ctr++;
	    }
	    currentLearningRate *= learningRateDecay;
	}
	ctr = 0;
	for (ArrayList<Integer> al : testSet)
	{
	    for (Integer k : al)
	    {
		double predRating = 0.0;
		for (int i = 0; i < noOfFeatures; i++)
		{
		    predRating += uMat[ctr][i] * vMat[i][k];//add the cAv here
		}
		//System.out.println(predRating);
		double err = msub[ctr][k] - predRating;
		mae += Math.abs(err);
		rmse += Math.pow(err, 2);
	    }
	    
	    noOfTestRatings+=al.size();
	    ctr++;
	}
    }

    public static Matrix prediction(File fi, Matrix mat) throws Exception
    {
	boolean flag = false;
	//File tha shit
	int index = 1;
	double[][] msub = mat.getArray();
	//read the folder
	for (File f : fi.listFiles())
	{
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    String line = br.readLine();
	    int pre = -1;
	    int dimRow = 0;
	    ArrayList<Integer> userList = new ArrayList<>();
	    //count the number of users represented in each file
	    while (line != null)
	    {
		String s[] = line.split(" ");
		int comp1 = Integer.parseInt(s[1]);
		if (comp1 != pre)
		{
		    pre = comp1;
		    dimRow++;
		}
		line = br.readLine();
	    }
	    //reset the position of reading
	    br = new BufferedReader(new FileReader(f));
	    line = br.readLine();
	    pre = -1;
	    int rowPtr = -1;
	    //read in all the shit
	    while (line != null)
	    {
		String[] sa = line.split(" ");

		if (pre != Integer.parseInt(sa[1]))
		{
		    rowPtr++;
		    pre = Integer.parseInt(sa[1]);
		    userList.add(pre);
		}
		msub[rowPtr][Integer.parseInt(sa[2]) - 1] = Integer.parseInt(sa[3]);
		line = br.readLine();

	    }
	    index++;
	}
	//changing everything here. ALL the rows will be used.
	ArrayList<Integer>[] fullDefinedList = new ArrayList[mat.getRowDimension()];
	for (int i = 0; i < mat.getRowDimension(); i++)
	{
	    //System.out.println(ctr);
	    fullDefinedList[i] = new ArrayList<>();

	    for (int j = 0; j < mat.getColumnDimension(); j++)
	    {
		if (mat.get(i, j) != 0.0)
		{
		    fullDefinedList[i].add(j);
		}
	    }
	    //System.out.println(ctr+":- "+definedList[ctr]);
	}
	//now perform prediction
	index = 1;
	for (File f : fi.listFiles())
	{
	    System.out.println(f);
	    work(f, index);
	    index++;
	}
	int noOfRatings = 0;
	System.out.println(fullDefinedList.length);
	for (ArrayList<Integer> ar : fullDefinedList)
	{
	    //System.out.println(ar.size());
	    noOfRatings += ar.size();
	}
	noOfRatings *= (1 - trainTestRatio);
	System.out.println("====");
	System.out.println(noOfRatings);
	System.out.println(noOfTestRatings);
	System.out.println(mae);
	System.out.println(rmse);
	System.out.println("====");
	mae /= noOfTestRatings;
	rmse = Math.sqrt(rmse / noOfTestRatings);
	double[][] mres = new double[mat.getRowDimension()][mat.getColumnDimension()];
	Matrix res = new Matrix(mres);
	for (int i = 1; i < index; i++)
	{
	    File f = new File("temp/predictedMatrixFor1MDiv" + i + ".dat");
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    String[] sa = br.readLine().split(" ");
	    int userList[] = new int[sa.length];
	    for (int j = 0; j < sa.length; j++)
	    {
		userList[j] = Integer.parseInt(sa[j]);
	    }
	    String line = br.readLine();
	    line = br.readLine();
	    int rowPtr;
	    System.out.println(fi);
	    for (int k = 0; k < sa.length; k++)
	    {
		String[] rPred = line.split(" ");
		rowPtr = userList[k];
		for (int j = 0; j < rPred.length - 1; j++)
		{
		    mres[rowPtr][j] = Double.parseDouble(rPred[j + 1]);
		}
		line = br.readLine();
	    }
	}
	mat.print(new PrintWriter("origMat.dat"), 4, 3);
	res.print(new PrintWriter("prediMat.dat"), 4, 3);
	return res;
    }

    public static void main(String[] args) throws Exception
    {
	double[][] msub = new double[6041][3952];
	Matrix m = new Matrix(msub);
	File f = new File("temp/");
	for (File fi : f.listFiles())
	{
	    fi.delete();
	}
	Matrix testMat = prediction(new File("testdata/Poly_user_rat_ts4"), m);
	System.out.println("RMSE:- " + rmse);
	System.out.println("MAE:- " + mae);
	//System.out.println("RMSE:- " + rmse(m, testMat));
	//System.out.println("MAE:- " + mae(m, testMat));
    }
}
