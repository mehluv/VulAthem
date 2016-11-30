
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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
public class MatrixModify100k
{

    static TreeSet<Integer> testRows = null;
    static ArrayList[] definedList;

    public static Matrix makeMatrices(File fi, Matrix mat) throws Exception
    {
	boolean flag = false;
	//File tha shit
	int index = 1;
	double[][] msub = mat.getArray();
	for (File f : fi.listFiles())
	{
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    String line = br.readLine();
	    int pre = -1;
	    int dimRow = 0;
	    ArrayList<Integer> userList = new ArrayList<>();
	    while (line != null)
	    {
		String s[] = line.split("	 ");
		int comp1 = Integer.parseInt(s[1]);
		if (comp1 != pre)
		{
		    pre = comp1;
		    dimRow++;
		}
		line = br.readLine();
	    }
	    br = new BufferedReader(new FileReader(f));
	    line = br.readLine();
	    pre = -1;
	    int rowPtr = -1;
	    while (line != null)
	    {
		String[] sa = line.split("	 ");

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
	int n = mat.getRowDimension() / 5;
	testRows = new TreeSet<>();
	Random r = new Random();
	while (testRows.size() < n)
	{
	    testRows.add(r.nextInt(mat.getRowDimension()));
	}
	definedList = new ArrayList[n];
	int ctr = 0;
	for (Integer i : testRows)
	{
	    definedList[ctr] = new ArrayList<Integer>();
	    for (int j = 0; j < 1682; j++)
	    {
		if (mat.get(i, j) != 0)
		{
		    definedList[ctr].add(j);
		}
	    }
	    ctr++;
	}
	//now perform prediction
	index = 1;
	for (File f : fi.listFiles())
	{
	    System.out.println(f);
	    SVDBatchLakh.work(f, index);
	    index++;
	}
	msub = new double[943][1682];
	Matrix res = new Matrix(msub);
	for (int i = 1; i < index; i++)
	{
	    File f = new File("temp/predictedMatrixFor100kDiv" + i + ".dat");
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
		for (int j = 0; j < 1681; j++)
		{
		    msub[rowPtr][j] = Double.parseDouble(rPred[j + 1]);
		}
		line = br.readLine();
	    }
	}
	/*	if (mat.getRowDimension() < mat.getColumnDimension())
	 {
	 mat = mat.transpose();
	 flag = true;
	 }
	
	 double[][] msub = mat.getArray();
	 for (int i : testRows)
	 {
	 msub[i] = new double[mat.getColumnDimension()];
	 }
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
	 Tester.naiveNormalize(mat);
	 System.out.println("Performing SVD");
	 SingularValueDecomposition svd = new SingularValueDecomposition(mat);
	 //System.out.println("Rank:- " + mat.rank());
	 int rank = mat.rank();
	 Matrix u = svd.getU(), v = svd.getV(), s = svd.getS();
	 System.out.println("Dimensions of U:- " + u.getRowDimension() + ", " + u.getColumnDimension());
	 System.out.println("Dimensions of S:- " + s.getRowDimension() + ", " + s.getColumnDimension());
	 System.out.println("Dimensions of V:- " + v.getRowDimension() + ", " + v.getColumnDimension());
	 int k = mat.rank() / 7;
	 Matrix sk = Tester.kReduction(s, k, k);
	 System.out.println("Reduced Sk");
	 //	sk.print(4, 3);
	 Matrix uk = Tester.kReduction(u, u.getRowDimension(), k);
	 System.out.println("Reduced Uk");
	 //	uk.print(4, 3);
	 Matrix vk = Tester.kReduction(v, v.getRowDimension(), k);
	 System.out.println("Reduced Vk");
	 System.out.println("Finding resultant prediction matrix:-");
	 Matrix res = uk.times(sk.times(vk.transpose()));
	 
	 if (flag)
	 {
	 return res.transpose();
	 }
	 */
	
	return res;
    }
}
