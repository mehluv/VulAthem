import Jama.Matrix;

public class MAE {
	public static double mae(Matrix trainingMat, Matrix testMat,Treeset<Integer> ts)	//testMat is predicted,trainingMat is original
    {
	int m = trainingMat.getRowDimension();
	int n = trainingMat.getColumnDimension();
	double s = 0.0;
	for (int i in ts)
	{
	    for (int j = 0; j < n; j++)
	    {
		s += (trainingMat.get(i, j) - testMat.get(i, j));
	    }
	}
	double error = Math.sqrt(s / (m * n));

	return error;
    }


}
