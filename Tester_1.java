
import Jama.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Tester_1
{

    public static void main(String[] args) throws Exception
    {
	int rank = 331;
	File f = new File("SVD_U.dat");
	BufferedReader br = new BufferedReader(new FileReader(f));
	double[][] uMat = new double[6041][rank];
	br.readLine();
	String line = br.readLine();
	int ctr = 0, i = 0, j = 0;
	while (line != null)
	{
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

	f = new File("SVD_S.dat");
	br = new BufferedReader(new FileReader(f));
	double[][] sMat = new double[rank][rank];
	br.readLine();
	line = br.readLine();
	i = 0;
	j = 0;
	while (line != null)
	{
	    String[] sa = line.split(" ");
	    
/*	    try
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
	    line = br.readLine();*/
	}
	System.out.println(ctr);
//	System.out.println("Dimensions of S:- " + sMat.length + "," + sMat[0].length);

	f = new File("SVD_V.dat");
	br = new BufferedReader(new FileReader(f));
	double[][] vMat = new double[4000][rank];
	br.readLine();
	line = br.readLine();
	i = 0;
	j = 0;
	while (line != null)
	{
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
	for (i = 0; i < 2; i++)
	{
	    sk.set(i, i, Math.sqrt(sk.get(i, i)));
	}
	System.out.println("Square Root of Sk");
	//	sk.print(4, 3);
	Matrix ukskh = uk.times(sk);
	System.out.println("Uk multiplied by Square Root of Sk");
	//	ukskh.print(4, 3);
	Matrix skhvk = sk.times(vk.transpose());
	System.out.println("Square Root of Sk multiplied by Vk'");
	//	skhvk.print(4, 3);
	System.out.println("Finding resultant prediction matrix:-");
	Matrix res = ukskh.times(skhvk);
	File fOrig = new File("/home/luv/NetBeansProjects/SVD/src/MovieLens Data/ratings.dat");
	br = new BufferedReader(new FileReader(fOrig));
	double[][] msub = new double[6041][4000];
	line = br.readLine();
	ctr = 0;
	while (line != null)
	{
	    String[] sa = line.split(" ");
	    msub[Integer.parseInt(sa[0])][Integer.parseInt(sa[1])] = Integer.parseInt(sa[2]);
	    ctr++;
	    if ((ctr % 5000) == 0)
	    {
		System.out.println("Test:- " + (ctr / 5000));
		ctr++;
		if (ctr > 50000)
		{
		    break;
		}
	    }
	    line = br.readLine();
	}

	for (i = 0; i < res.getColumnDimension(); i++)
	{
	    int cAv = 0;
	    for (j = 0; j < msub.length; j++)
	    {
		if (msub[j][i] != 0)
		{
		    cAv += msub[j][i];
		}
	    }
	    cAv /= msub.length;
	    for (j = 0; j < res.getRowDimension(); j++)
	    {
		res.set(i, j, res.get(i, j) + cAv);
	    }
	}
	f = new File("predictedMatrix.dat");
	res.print(new PrintWriter(f), 4, 3);
	System.out.println("Printing generated value for 2440th customer and 60th movie");
	float result = 0;
	for (i = 0; i < k; i++)
	{
	    result += ukskh.get(2439, i) * skhvk.get(i, 59);
	}
	System.out.println("Generated value:- " + result);
    }
}
