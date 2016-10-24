//this is to
//save a normalized matrix, pre-SVD, to a file
//and put customer averages into a file for denormalization
import Jama.Matrix;
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
public class Tester_3
{

    public static void main(String[] args) throws Exception
    {
		File f = new File("/home/luv/NetBeansProjects/SVD/src/MovieLens Data/ratings.dat");
	BufferedReader br = new BufferedReader(new FileReader(f));
	double[][] msub = new double[6041][4000];
	String line = br.readLine();
	int ctr = 0;
	while (line != null)
	{
	    String[] sa = line.split(" ");
	    msub[Integer.parseInt(sa[0])-1][Integer.parseInt(sa[1])-1] = Integer.parseInt(sa[2]);
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

	Matrix m = new Matrix(msub);
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
	Tester.naiveNormalize(m);
	File fout=new File("origMat.dat");
	m.print(new PrintWriter(fout), 4,3);
	//this will generated the Customer Average array and place into a file
	PrintWriter avWrite = new PrintWriter(new BufferedWriter(new FileWriter("cAv.dat")));
	for(int i=0;i<cAv.length;i++)
	{
	    avWrite.print(cAv[i]+" ");
	}
	avWrite.flush();
    }
}
