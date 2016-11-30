
import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//To calculate all errors
public class CalcErrorsCF100k
{

    public static void main(String[] args) throws Exception
    {
	double[][] msub = new double[943][1682];
	Matrix m = new Matrix(msub);
	/*
	File fi = new File("testdata/100k - 4/Polygon_ratings/P_55414.TXT");
	BufferedReader br = new BufferedReader(new FileReader(fi));
	String line=br.readLine();
	int i=0;
	while (line != null)
	{
	    String[] sa = line.split("	 ");
	    msub[Integer.parseInt(sa[1])][Integer.parseInt(sa[2]) - 1] = Integer.parseInt(sa[3]);
	    line = br.readLine();
	    
	}*/
	Matrix testMat=MatrixModify100k.makeMatrices(new File("testdata/100k - 1/Polygon_ratings"), m);
	System.out.println("RMSE:- "+RMSE.rmse(m, testMat,MatrixModify100k.testRows, MatrixModify100k.definedList));
	System.out.println("MAE:- "+MAE.mae(m, testMat,MatrixModify100k.testRows, MatrixModify100k.definedList));
    }
}