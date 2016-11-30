
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author luv
 */
public class SVDBatchMil
{

    static void work(File f, int index) throws Exception
    {
	BufferedReader br = new BufferedReader(new FileReader(f));
	String line = br.readLine();
	int pre = -1;
	int dimRow = 0;
	ArrayList<Integer> userList = new ArrayList<>();
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
	System.out.println("Number of users in cluster:- " + dimRow);
	double[][] msub = new double[dimRow][3952]; //change the shit
	br = new BufferedReader(new FileReader(f));
	line = br.readLine();
	pre = -1;
	int rowPtr = -1;
	while (line != null)
	{
	    String[] sa = line.split(" ");
	    int temp=Integer.parseInt(sa[1])-1;
	    if (pre != temp)
	    {
		rowPtr++;
		pre = temp;
		userList.add(pre);
	    }
	    msub[rowPtr][Integer.parseInt(sa[2]) - 1] = Integer.parseInt(sa[3]);
	    line = br.readLine();
	    
	}

	Matrix m1 = new Matrix(msub);
	double[] cAv = new double[msub[0].length];
	for (int i = 0; i < msub[0].length; i++)
	{
	    int k = 0;
	    for (int j = 0; j < msub.length; j++)
	    {
		if (msub[j][i] != 0)
		{
		    cAv[i] += msub[j][i];
		    k++;
		}
	    }
	    if (k == 0)
	    {
		cAv[i] = 3.0;
		continue;
	    }
	    cAv[i] /= k;
	}
	Tester.naiveNormalize(m1);
	Matrix m = m1.transpose();
	//File fout = new File("origMat.dat");
	//m.print(new PrintWriter(fout), 4, 3);
	//File fout = new File("origMat.dat");
	//m.print(new PrintWriter(fout), 4, 3);
	//this will generated the Customer Average array and place into a file
	/*PrintWriter avWrite = new PrintWriter(new BufferedWriter(new FileWriter("cAv.dat")));
	 for (int i = 0; i < cAv.length; i++)
	 {
	 avWrite.print(cAv[i] + " ");
	 }
	 avWrite.flush();*/
	Date d = new Date();
	long b = d.getTime();
	System.out.println("Performing SVD");
	SingularValueDecomposition svd = new SingularValueDecomposition(m);
	System.out.println("Rank:- " + m.rank());
	int rank = m.rank();
	Matrix u = svd.getU(), v = svd.getV(), s = svd.getS();
	System.out.println("Dimensions of U:- " + u.getRowDimension() + ", " + u.getColumnDimension());
	System.out.println("Dimensions of S:- " + s.getRowDimension() + ", " + s.getColumnDimension());
	System.out.println("Dimensions of V:- " + v.getRowDimension() + ", " + v.getColumnDimension());
	int k = m.rank() / 7;
	Matrix sk = Tester.kReduction(s, k, k);
	System.out.println("Reduced Sk");
	//	sk.print(4, 3);
	Matrix uk = Tester.kReduction(u, u.getRowDimension(), k);
	System.out.println("Reduced Uk");
	//	uk.print(4, 3);
	Matrix vk = Tester.kReduction(v, v.getRowDimension(), k);
	System.out.println("Reduced Vk");
	System.out.println("Finding resultant prediction matrix:-");
	//Matrix res = ukskh.times(skhvk);
	Matrix res1 = uk.times(sk.times(vk.transpose()));
	Matrix res = res1.transpose();
	for (int j = 0; j < res.getColumnDimension(); j++)
	{
	    for (int i = 0; i < res.getRowDimension(); i++)
	    {
		res.set(i, j, res.get(i, j) + cAv[j]);
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
	res.print(pw, 4, 3);
	pw.flush();
	for (int i = 0; i < rank; i++)
	{
	    System.out.print((res.getArray())[0][i] + " ");
	}
	System.out.println();

	System.out.println("Time taken:- " + a / 1000);
    }

    public static void main(String[] args) throws Exception
    {
	File[] f = new File("/home/luv/NetBeansProjects/SVD/testdata/Poly_user_rat_ts3").listFiles();
	int index = 1;
	for (File fi : f)
	{
	    System.out.println(fi);
	    work(fi, index);
	    index++;
	}
	double[][] msub = new double[6041][3952];
	Matrix m = new Matrix(msub);
	for (int i = 1; i < index; i++)
	{
	    File fi = new File("predictedMatrixFor1MDiv" + i + ".dat");
	    BufferedReader br = new BufferedReader(new FileReader(fi));
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
		rowPtr = userList[k]-1;
		for (int j = 0; j < 3952; j++)
		{
		    msub[rowPtr][j] = Double.parseDouble(rPred[j + 1]);
		}
		line = br.readLine();
	    }
	}
	File fi = new File("prediMat.dat");
	PrintWriter pw=new PrintWriter(fi);
	m.print(pw, 4, 3);
	pw.flush();
    }

}
