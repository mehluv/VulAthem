//this is to
//take files generated from Tester and multiply them
import Jama.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Tester_1
{

    public static void main(String[] args) throws Exception
    {
	int rank = 332;
	File f = new File("SVD_U.dat");
	BufferedReader br = new BufferedReader(new FileReader(f));
	double[][] uMat = new double[6041][rank];
	br.readLine();
	String line = br.readLine();
	int ctr = 0, i = 0, j = 0;
	while (line != null)
	{
	    if (ctr == rank)
	    {
		break;
	    }
	    String[] sa = line.split(" ");
	    try
	    {
		for (j = 0; j < rank; j++)
		{
		    uMat[i][j] = Double.parseDouble(sa[j + 1]);
		}
	    } catch (NumberFormatException nfe)
	    {
		System.out.println("At position i= " + i + ", j= " + j);
		System.exit(1);
	    } catch (ArrayIndexOutOfBoundsException aioobe)
	    {
		System.out.println("At position i= " + i + ", j= " + j);
		System.exit(1);
	    }
	    ctr++;
	    i++;
	    line = br.readLine();
	}
	System.out.println("Dimensions of U:- " + uMat.length + "," + uMat[0].length);
	for (i = 0; i < rank; i++)
	{
	    System.out.print(uMat[0][i] + " ");
	}
	System.out.println();
	File fs = new File("SVD_S.dat");
	br = new BufferedReader(new FileReader(fs));
	double[][] sMat = new double[rank][rank];
	br.readLine();
	line = br.readLine();
	System.out.println(line);
	i = 0;
	j = 0;
	ctr=0;
	while (line != null)
	{
	    if (ctr == rank)
	    {
		break;
	    }
	    String[] sa = line.split(" ");
	    try
	    {
		for (j = 0; j < rank; j++)
		{
		    sMat[i][j] = Double.parseDouble(sa[j + 1]);
		}
	    } catch (NumberFormatException nfe)
	    {
		System.out.println("At position i= " + i + ", j= " + j + nfe);
		System.exit(1);
	    } catch (ArrayIndexOutOfBoundsException aioobe)
	    {
		System.out.println("At position i= " + i + ", j= " + j + aioobe);
		System.exit(1);
	    }
	    ctr++;
	    i++;
	    line = br.readLine();
	}
	System.out.println(ctr);
//	System.out.println("Dimensions of S:- " + sMat.length + "," + sMat[0].length);
	for (i = 0; i < rank; i++)
	{
	    System.out.print(sMat[0][i] + " ");
	}
	System.out.println();
	f = new File("SVD_V.dat");
	br = new BufferedReader(new FileReader(f));
	double[][] vMat = new double[4000][rank];
	br.readLine();
	line = br.readLine();
	i = 0;
	j = 0;
	ctr=0;
	while (line != null)
	{
	    if (ctr == rank)
	    {
		break;
	    }
	    String[] sa = line.split(" ");
	    for (j = 0; j < rank; j++)
	    {
		vMat[i][j] = Double.parseDouble(sa[j + 1]);
	    }
	    ctr++;
	    i++;
	    line = br.readLine();
	}
	System.out.println("Dimensions of V:- " + vMat.length + "," + vMat[0].length);
	for (i = 0; i < rank; i++)
	{
	    System.out.print(vMat[0][i] + " ");
	}
	System.out.println();
	int k = 14;
	 Matrix sk = Tester.kReduction(new Matrix(sMat), k, k);
	 System.out.println("Reduced Sk");
	 //	sk.print(4, 3);
	 Matrix uk = Tester.kReduction(new Matrix(uMat), uMat.length, k);
	 System.out.println("Reduced Uk");
	 //	uk.print(4, 3);
	 Matrix vk = Tester.kReduction(new Matrix(vMat), vMat.length, k);
	 System.out.println("Reduced Vk");
	//	vk.print(4, 3);
	/*Matrix sk = new Matrix(sMat);
	Matrix uk = new Matrix(uMat);
	Matrix vk = new Matrix(vMat);
	/*for (i = 0; i < 2; i++)
	 {
	 sk.set(i, i, Math.sqrt(sk.get(i, i)));
	 }
	 /*System.out.println("Square Root of Sk");
	 //	sk.print(4, 3);
	 Matrix ukskh = uk.times(sk);
	 System.out.println("Uk multiplied by Square Root of Sk");
	 //	ukskh.print(4, 3);
	 Matrix skhvk = sk.times(vk.transpose());
	 System.out.println("Square Root of Sk multiplied by Vk'");
	 //	skhvk.print(4, 3);*/
	System.out.println("Finding resultant prediction matrix:-");
	//Matrix res = ukskh.times(skhvk);
	Matrix res = uk.times(sk.times(vk.transpose()));
	br = new BufferedReader(new FileReader("cAv.dat"));
	String s[]=br.readLine().split(" ");
	double[] cAv = new double[res.getColumnDimension()];
	for(i=0;i<cAv.length;i++)
	{
	    cAv[i]=Double.parseDouble(s[i]);
	}
	for(j=0;j<res.getColumnDimension();j++)
	{
	    for(i=0;i<res.getRowDimension();i++)
	    {
		res.set(i, j, res.get(i, j)+cAv[j]);
	    }
	}
	f = new File("predictedMatrix.dat");
	//to get single digit values
	res.print(new PrintWriter(f), 1, 0);
	for (i = 0; i < rank; i++)
	{
	    System.out.print((res.getArray())[0][i] + " ");
	}
	System.out.println();
    }
}
