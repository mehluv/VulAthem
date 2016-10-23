///this is to
//do small scale testing
import Jama.Matrix;
import Jama.SingularValueDecomposition;
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
public class Tester_4
{

    public static void main(String[] args)
    {
	double[][] msub =
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
	};
	Matrix m=new Matrix(msub);
	m.print(4, 3);
	SingularValueDecomposition svd=new SingularValueDecomposition(m);
	svd.getU().print(4, 3);
	System.out.println();
	svd.getS().print(4, 3);
	System.out.println();
	svd.getV().print(4, 3);
	System.out.println();
	Matrix res=svd.getU().times(svd.getS().times(svd.getV().transpose()));
	res.print(4, 3);
	Matrix uk=Tester.kReduction(svd.getU(), 4, 2);
	Matrix sk=Tester.kReduction(svd.getS(), 2, 2);	
	Matrix vk=Tester.kReduction(svd.getV(), 3, 2);
	uk.print(4, 3);
	System.out.println();
	sk.print(4, 3);
	System.out.println();
	vk.print(4, 3);
	res=uk.times(sk.times(vk.transpose()));
	res.print(4, 3);
    }
}
