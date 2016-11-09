
import Jama.Matrix;
import Jama.SingularValueDecomposition;
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
public class MatrixModify
{

    public static Matrix makeTestMatrix(Matrix mat)
    {
	if(mat.getRowDimension()<mat.getColumnDimension())
	{
	    return makeTestMatrix(mat.transpose());
	}
	int n=mat.getRowDimension()/5;
	TreeSet<Integer> st=new TreeSet<>();
	Random r=new Random();
	while(st.size()<n)
	{
	    st.add(r.nextInt(mat.getRowDimension()));
	}
	double[][] msub=mat.getArray();
	for(int i:st)
	{
	    msub[i]=new double[mat.getColumnDimension()];
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
	for (int j = 0; j < res.getColumnDimension(); j++)
	{
	    for (int i = 0; i < res.getRowDimension(); i++)
	    {
		res.set(i, j, res.get(i, j) + cAv[j]);
	    }
	}
	return res;
    }
}
