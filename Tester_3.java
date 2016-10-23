//this is to
//save a normalized matrix, pre-SVD, to a file
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	Tester.naiveNormalize(m);
	File fout=new File("origMat.dat");
	m.print(new PrintWriter(fout), 4,3);
    }
}
