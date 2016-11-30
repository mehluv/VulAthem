
import Jama.Matrix;
import java.util.ArrayList;
import java.util.TreeSet;

public class MAE
{

    public static double mae(Matrix trainingMat, Matrix testMat, TreeSet<Integer> rowList, ArrayList<Integer>[] definedSet)	//testMat is predicted,trainingMat is original
    {
	int m = trainingMat.getRowDimension();
	int n = trainingMat.getColumnDimension();
	double s = 0.0;
	int ctr=0;
	for (int i : rowList)
	{
	    for (int j: definedSet[ctr])
	    {
		s += Math.abs((trainingMat.get(i, j) - testMat.get(i, j)));
	    }
	    ctr++;
	}
	int noOfRatings=0;
	for (ArrayList<Integer> ar: definedSet)
	{
	    noOfRatings+=ar.size();
	}
	double error = s / noOfRatings;

	return error;
    }
}
