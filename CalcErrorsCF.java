
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//To calculate all errors
public class CalcErrorsCF
{

    public static void main(String[] args) throws Exception
    {
	double[][] msub = new double[942][1681];
	Matrix m = new Matrix(msub);
	File fi = new File("predictedMatFor100k.dat");
	BufferedReader br = new BufferedReader(new FileReader(fi));
	String line = br.readLine();
	line = br.readLine();
	System.out.println(fi);
	for (int k = 0; k < 942; k++)
	{
	    String[] rPred = line.split(" ");
	    for (int j = 0; j < 1681; j++)
	    {
		msub[k][j] = Double.parseDouble(rPred[j + 1]);
	    }
	    line = br.readLine();
	}
	Matrix testMat=MatrixModify.makeTestMatrix(m);
	System.out.println("Error:- "+RMSE.rmse(m, testMat,MatrixModify.st));
    }
}
