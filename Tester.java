
import Jama.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Tester
{

    static Matrix kReduction(Matrix m, int k, int k1)
    {
	Matrix reduced = new Matrix(m.getArrayCopy(), k, k1);
	return reduced;
    }

    public static void main(String[] args) throws Exception
    {
	/*	double[][] msub =
	 {
	 {
	 3.0, 4.9, 2.8
	 },
	 {
	 1.4, 4.1, 0.3
	 },
	 {
	 4.9, 1.5, 4
	 },
	 {
	 3.9, 1.5, 1.9
	 }
	 };*/
	File f = new File("/home/luv/NetBeansProjects/SVD/src/MovieLens Data/ratings.dat");
	BufferedReader br = new BufferedReader(new FileReader(f));
	double[][] msub = new double[6041][4000];
	String line = br.readLine();
	int ctr = 0;
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
	for (int i = 0; i < msub[0].length; i++)
	{
	    int cAv = 0;
	    for (int j = 0; j < msub.length; j++)
	    {
		if (msub[j][i] != 0)
		{
		    cAv += msub[j][i];
		}
	    }
	    cAv /= msub.length;
	    for (int j = 0; j < msub.length; j++)
	    {
		if (msub[j][i] == 0)
		{
		    msub[j][i] = cAv;
		}
	    }
	}
	
	for (int i = 0; i < msub.length; i++)
	{
	    int rAv = 0;
	    for (int j = 0; j < msub[0].length; j++)
	    {
		rAv += msub[i][j];
	    }
	    rAv /= msub[0].length;
	    for (int j = 0; j < msub[0].length; j++)
	    {
		msub[i][j] -= rAv;
	    }
	}
	//Normalization Done
	Matrix m = new Matrix(msub);
//	m.print(4, 3);
	System.out.println("Performing SVD");
	SingularValueDecomposition svd = m.svd();
	System.out.println("Rank:- " + m.rank());
	Matrix u = svd.getU(), v = svd.getV(), s = svd.getS();
	System.out.println("Dimensions of U:- "+u.getRowDimension()+", "+u.getColumnDimension());
	System.out.println("Dimensions of S:- "+s.getRowDimension()+", "+s.getColumnDimension());
	System.out.println("Dimensions of V:- "+v.getRowDimension()+", "+v.getColumnDimension());
/*	File fout = new File("SVD_U.dat");
	u.print(new PrintWriter(fout), 4, 3);
	System.out.println("U:-");
//	u.print(4, 3);
	fout = new File("SVD_V.dat");
	v.print(new PrintWriter(fout), 4, 3);
	System.out.println("V:-");
//	v.print(4, 3);
	fout = new File("SVD_S.dat");
	double[][] sArr=new double[1][m.rank()];
	for(int i=0;i<m.rank();i++)
	    sArr[0][i]=s.get(i, i);
	s=new Matrix(sArr);
	s.print(new PrintWriter(fout), 4, 3);
	System.out.println("S:-");
//	s.print(4, 3);
//	Matrix m1 = u.times(s).times(v);
//	m1.print(4, 3);
/*	int k = 2;
	 Matrix sk = kReduction(s, k, k);
	 System.out.println("Reduced Sk");
	 //	sk.print(4, 3);
	 Matrix uk = kReduction(u, u.getRowDimension(), k);
	 System.out.println("Reduced Uk");
	 //	uk.print(4, 3);
	 Matrix vk = kReduction(v, v.getRowDimension(), k);
	 System.out.println("Reduced Vk");
	 //	vk.print(4, 3);
	 for (int i = 0; i < 2; i++)
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
	 System.out.println("Printing generated value for 2nd customer and 1st movie");
	 float result = 0;
	 for (int i = 0; i < k; i++)
	 {
	 result += ukskh.get(0, i) * skhvk.get(i, 0);
	 }
	 System.out.println("Generated value:- " + result);*/
    }
}
