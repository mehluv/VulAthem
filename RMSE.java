
import Jama.Matrix;
import java.util.ArrayList;
import java.util.TreeSet;

class RMSE
{

    public static double rmse(Matrix trainingMat, Matrix testMat, TreeSet<Integer> rowList, ArrayList<Integer>[] definedSet)	//testMat is predicted,trainingMat is original
    {
	int m = trainingMat.getRowDimension();
	int n = trainingMat.getColumnDimension();
	double s = 0.0;
	int ctr=0;
	for (int i : rowList)
	{
	    for (int j: definedSet[ctr])
	    {
		double a=(trainingMat.get(i, j));
		double b=testMat.get(i, j);
		s += Math.pow((a - b), 2);
	    }
	    ctr++;
	}
	
	int noOfRatings=0;
	for (ArrayList<Integer> ar: definedSet)
	{
	    noOfRatings+=ar.size();
	}
	double error = Math.sqrt(s/noOfRatings);///m;// / (m * n));

	return error;
    }

}
