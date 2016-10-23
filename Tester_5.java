//this is to
//pefrom SVD and immediately multiply
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author luv
 */
public class Tester_5
{

    public static void main(String[] args) throws Exception
    {
	File f = new File("/home/luv/NetBeansProjects/SVD/src/MovieLens Data/ratings.dat");
	BufferedReader br = new BufferedReader(new FileReader(f));
	double[][] msub = new double[6041][4000];
	String line = br.readLine();
	while (line != null)
	{
	    String[] sa = line.split(" ");
	    msub[Integer.parseInt(sa[0]) - 1][Integer.parseInt(sa[1]) - 1] = Integer.parseInt(sa[2]);
	    line = br.readLine();
	}
	Matrix m = new Matrix(msub);
	Tester.naiveNormalize(m);
//	m.print(new PrintWriter(System.out), 4, 3);
	//Normalization Done
//	m.print(4, 3);
	System.out.println("Performing SVD");
	SingularValueDecomposition svd = new SingularValueDecomposition(m);
	System.out.println("Rank:- " + m.rank());
	Matrix u = svd.getU(), v = svd.getV(), s = svd.getS();
	System.out.println("Dimensions of U:- " + u.getRowDimension() + ", " + u.getColumnDimension());
	System.out.println("Dimensions of S:- " + s.getRowDimension() + ", " + s.getColumnDimension());
	System.out.println("Dimensions of V:- " + v.getRowDimension() + ", " + v.getColumnDimension());
	Matrix sk = new Matrix(s.getArrayCopy());
	Matrix uk = new Matrix(u.getArrayCopy());
	Matrix vk = new Matrix(v.getArrayCopy());
	System.out.println("Finding resultant prediction matrix:-");
	//Matrix res = ukskh.times(skhvk);
	Matrix res = uk.times(sk.times(vk.transpose()));
	PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter("predMat.dat")));
	//res.print(fout, 4, 3);
	for(int i=0;i<uk.rank();i++)
	{
	    for(int j=0;j<uk.rank();j++)
	    {
		fout.write(String.format("%1.4f ",res.getArray()[i][j]));
	    }
	    fout.write("\n");
	    fout.flush();
	}
	for(int i=0;i<uk.rank();i++)
	{
	    System.out.print((res.getArray())[0][i]+" ");
	}
    }
}
