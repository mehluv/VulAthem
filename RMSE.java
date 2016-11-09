
import Jama.Matrix;

class RMSE
{

    public static double rmse(Matrix trainingMat, Matrix testMat)	//testMat is predicted,trainingMat is original
    {
	int m = trainingMat.getRowDimension();
	int n = trainingMat.getColumnDimension();
	double s = 0.0;
	for (int i = 0; i < m; i++)
	{
	    for (int j = 0; j < n; j++)
	    {
		s += Math.pow((trainingMat.get(i, j) - testMat.get(i, j)), 2);
	    }
	}
	double error = Math.sqrt(s / (m * n));

	return error;
    }

}
