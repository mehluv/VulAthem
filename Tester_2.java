
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Tester_2
{

    public static void main(String[] args) throws Exception
    {
	File f = new File("SVD_S.dat");
	FileReader fin = new FileReader(f);
	BufferedReader br = new BufferedReader(fin);
	br.readLine();
	String line = br.readLine();
	int ctr=1;
	Double[] sArr=new Double[4001];
	try{
	while (line != null)
	{
	    String[] sa = line.split(" ");
//	    System.out.println(sa.length);
	    sArr[ctr-1]=Double.parseDouble(sa[ctr]);
	    line=br.readLine();
	    ctr++;
	}
	for(int i=0;i<331;i++)
	    System.out.print(sArr[i]+" ");
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
	    System.out.println("Array index out of bounds:- ctr="+ctr);
	    e.printStackTrace();
	}
    }
}
