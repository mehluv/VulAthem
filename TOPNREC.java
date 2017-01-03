import Jama.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TOPNREC
{
	//static Matrix m, bm;
	static double precision, recall; 
  /*  public static void main(String[] args) throws Exception
    {
	int rank = 331;
	File f = new File("SVD_U_rec2.dat");
	BufferedReader br = new BufferedReader(new FileReader(f));
	double[][] uMat = new double[6041][rank];
	br.readLine();
	String line = br.readLine();
	int ctr = 0, i = 0, j = 0;
	while (line != null)
	{
	    if (ctr == rank)
	    {
		break;
	    }
	    String[] sa = line.split(" ");
	    try
	    {
		for (j = 0; j < rank; j++)
		{
		    uMat[i][j] = Double.parseDouble(sa[j + 1]);
		}
	    } catch (NumberFormatException nfe)
	    {
		System.out.println("At position i= " + i + ", j= " + j);
		System.exit(1);
	    } catch (ArrayIndexOutOfBoundsException aioobe)
	    {
		System.out.println("At position i= " + i + ", j= " + j);
		System.exit(1);
	    }
	    ctr++;
	    i++;
	    line = br.readLine();
	}
	System.out.println("Dimensions of U:- " + uMat.length + "," + uMat[0].length);
	for (i = 0; i < rank; i++)
	{
	    System.out.print(uMat[0][i] + " ");
	}
	System.out.println();
	File fs = new File("SVD_S_rec2.dat");
	br = new BufferedReader(new FileReader(fs));
	double[][] sMat = new double[rank][rank];
	br.readLine();
	line = br.readLine();
	System.out.println(line);
	i = 0;
	j = 0;
	ctr=0;
	while (line != null)
	{
	    if (ctr == rank)
	    {
		break;
	    }
	    String[] sa = line.split(" ");
	    try
	    {
		for (j = 0; j < rank; j++)
		{
		    sMat[i][j] = Double.parseDouble(sa[j + 1]);
		}
	    } catch (NumberFormatException nfe)
	    {
		System.out.println("At position i= " + i + ", j= " + j + nfe);
		System.exit(1);
	    } catch (ArrayIndexOutOfBoundsException aioobe)
	    {
		System.out.println("At position i= " + i + ", j= " + j + aioobe);
		System.exit(1);
	    }
	    ctr++;
	    i++;
	    line = br.readLine();
	}
	System.out.println(ctr);
//	System.out.println("Dimensions of S:- " + sMat.length + "," + sMat[0].length);
	for (i = 0; i < rank; i++)
	{
	    System.out.print(sMat[0][i] + " ");
	}
	System.out.println();
	f = new File("SVD_V_rec2.dat");
	br = new BufferedReader(new FileReader(f));
	double[][] vMat = new double[4000][rank];
	br.readLine();
	line = br.readLine();
	i = 0;
	j = 0;
	ctr=0;
	while (line != null)
	{
	    if (ctr == rank)
	    {
		break;
	    }
	    String[] sa = line.split(" ");
	    for (j = 0; j < rank; j++)
	    {
		vMat[i][j] = Double.parseDouble(sa[j + 1]);
	    }
	    ctr++;
	    i++;
	    line = br.readLine();
	}
	System.out.println("Dimensions of V:- " + vMat.length + "," + vMat[0].length);
	for (i = 0; i < rank; i++)
	{
	    System.out.print(vMat[0][i] + " ");
	}
	System.out.println();
	int k = 14;
	 Matrix sk = rec2.kReduction(new Matrix(sMat), k, k);
	 System.out.println("Reduced Sk");
	 sk.print(4, 3);
	 Matrix uk = rec2.kReduction(new Matrix(uMat), uMat.length, k);
	 System.out.println("Reduced Uk");
	 	uk.print(4, 3);
	 Matrix vk = rec2.kReduction(new Matrix(vMat), vMat.length, k);
	 System.out.println("Reduced Vk");
	vk.print(4, 3);
	
	/*for(int ii = 0; ii < sk.getRowDimension(); ii ++)
	{
		for(int jj = 0; jj < sk.getColumnDimension(); jj ++)
		{
			if(sk.get(ii, jj)!= 0)
				skc ++;
		}
	}
	System.out.println("skc = " + skc);
	Commment krna hMatrix sk = new Matrix(sMat);
	Matrix uk = new Matrix(uMat);
	Matrix vk = new Matrix(vMat);
	for (i = 0; i < k; i++)
	 {
	 sk.set(i, i, Math.sqrt(sk.get(i, i)));
	 }
	 System.out.println("Square Root of Sk");
	 //sk.print(4, 3);
	 Matrix ukskh = uk.times(sk);
	 System.out.println("Uk multiplied by Square Root of Sk");
	 //ukskh.print(4, 3);
	 Matrix skhvk = sk.times(vk.transpose());
	 System.out.println("Square Root of Sk multiplied by Vk'");
	 //skhvk.print(4, 3);
	System.out.println("Finding resultant prediction matrix:-");
	//Matrix res = ukskh.times(skhvk);
	Matrix res = uk.times(sk.times(vk.transpose()));
	
	File fout1 = new File("ukskh_rec3.dat");
	ukskh.print(new PrintWriter(fout1), 4, 3);
	
	File fout2 = new File("skhvk_rec3.dat");
	skhvk.print(new PrintWriter(fout2), 4, 3);
	
	//Using ukskh to find neighborhood
	//int user_matrix = 0; //taking the first user 

			
			f = new File("/home/swarnadeep/Desktop/Project/MovieLens Data/ratings.dat");
			br = new BufferedReader(new FileReader(f));
			double[][] mat = new double[6041][4000];
			line = br.readLine();
			int cntr = 0;
			while (line != null)
			{
			    String[] sa = line.split(" ");
			    mat[Integer.parseInt(sa[0])-1][Integer.parseInt(sa[1])-1] = Integer.parseInt(sa[2]);
			    cntr++;
			    if ((cntr % 5000) == 0)
			    {
				System.out.println("Test:- " + (cntr / 5000));
				cntr++;
				if (cntr > 50000)
				{
				    break;
				}
			    }
			    line = br.readLine();
			}
			m = new Matrix(mat);
			File fout = new File("originalmatrix.dat");
			m.print(new PrintWriter(fout), 4,3);
			System.out.println("Done");
			
			File f1 = new File("/home/swarnadeep/Desktop/Project/MovieLens Data/ratings.dat");
			BufferedReader bre = new BufferedReader(new FileReader(f1));
			double[][] msub = new double[6041][4000];
			String line1 = bre.readLine();
			int ctr1 = 0;
			while (line1 != null)
			{
			    String[] sa = line1.split(" ");
			    int z = Integer.parseInt(sa[2]);
			    if(z == 0)
			    	msub[Integer.parseInt(sa[0])-1][Integer.parseInt(sa[1])-1] = 0;
			    else
			    	msub[Integer.parseInt(sa[0])-1][Integer.parseInt(sa[1])-1] = 1;
			    	
			    ctr1++;
			    if ((ctr1 % 5000) == 0)
			    {
				System.out.println("Test:- " + (ctr1 / 5000));
				ctr1++;
				if (ctr1 > 50000)
				{
				    break;
				}
			    }
			    line1 = bre.readLine();
			}

			bm = new Matrix(msub);
			
			File foutt=new File("rec_binary_matrix_new.dat");
			bm.print(new PrintWriter(foutt), 4,3);
			int user = 1;
			
			for(user = 1; user < ukskh.getRowDimension(); user ++) 
				{
					calculate(user, ukskh);
				}
			System.out.println("Final:: ");
			System.out.println("Precision: " + precision/ukskh.getRowDimension() + " Recall: " 
					+ recall/ukskh.getRowDimension());
    }
    */
public static void set(double a[], int n)
{
	for(int i = 0; i < n; i ++)
		a[i] = 0.0;
}

public static void calculate(int user, Matrix ukskh,Matrix bm1,Matrix m1) throws Exception
{
	double cosine[] = new double[m1.getRowDimension()]; //this should be equal to m
	cosine[user] = -100; //for itself 
	//System.out.println(ukskh.getRowDimension() + " " + ukskh.getColumnDimension());
	double user_values[] = new double[ukskh.getColumnDimension()];
	
	int ct = 0;
	for(int u_col = 0; u_col < ukskh.getColumnDimension(); u_col ++)
		user_values[ct ++] = ukskh.get(user, u_col); 
	
	double fuss = 0;
	for(int show = 0; show < ukskh.getColumnDimension(); show ++)
		fuss += user_values[show] * user_values[show];
	fuss = Math.sqrt(fuss);
	//System.out.println("Fuss " + fuss);
	double sq_sum = 0;
	for(int u_row = 0; u_row < ukskh.getRowDimension(); u_row ++)
	{
		double sum = 0; sq_sum = 0;
		if(u_row != user) 
		{
		for(int u_col = 0; u_col < ukskh.getColumnDimension(); u_col ++)
		{
			//find cosine similarity of the users
			double pdt = user_values[u_col] * ukskh.get(u_row, u_col);
			sum = sum + pdt;
			sq_sum += ukskh.get(u_row, u_col) * ukskh.get(u_row, u_col);
		}
		cosine[u_row] = sum / (Math.sqrt(sq_sum) * fuss);
		}
	}
	int len = m1.getRowDimension();
	
	
	double pos[] = new double[len];
	for(int count = 0; count < len; count ++)
		pos[count] = count;
	pos[user] = -100;
	for(int ii = 0; ii < len - 1; ii ++)
	{
		for(int jj = ii + 1; jj < len; jj ++ )
		{
			if(cosine[ii] < cosine [jj])
			{
				double tem = cosine[ii]; 
				cosine[ii] = cosine[jj];
				cosine[jj] = tem;
				tem = pos[ii];
				pos[ii] = pos[jj];
				pos[jj] = tem;
			}
		}
	}
	PrintWriter writer;
	try{
	writer = new PrintWriter("cosines3.dat");
	
	//System.out.println("Cosine similarity for " + (user) + " user:");
	for(int show = 0; show < len; show ++) //greater than 331 shows NaN
		writer.println(cosine[show] + " " + pos[show]);
	writer.close();
	}
catch(Exception e)
{}
	/*System.out.println("100 neughborhood: ");
	for(int show = 1; show < 101; show ++)
		System.out.println(cosine[show] + " " + pos[show]);
	*/
	//Neighborhood is done 
	
	//System.out.println("Test ok! ");
	double frequency[] = new double[bm1.getColumnDimension()];
	//System.out.println(m.getColumnDimension());  //frequency of the movies
	int z = 0;
	for(int m_col = 0; m_col < bm1.getColumnDimension(); m_col ++)
	{
		int freq = 0;
		for(int m_row = 0; m_row < ukskh.getRowDimension(); m_row ++)
		{
			if(pos[m_row] != -100){
			if(bm1.get((int)(pos[m_row]), m_col) == 1)
				freq ++;
			}
		}
		frequency[z ++] = freq;
	}
	
	double position[] = new double[bm1.getColumnDimension()];
	for(int show = 0; show < bm1.getColumnDimension(); show ++)
	{
		position[show] = show;
	}
	for(int ii = 0; ii < bm1.getColumnDimension() - 1; ii ++)
	{
		for(int jj = ii + 1; jj < bm1.getColumnDimension(); jj ++)
		{
			if(frequency[ii] < frequency[jj])
			{
				double tem = frequency[ii];
				frequency[ii] = frequency[jj];
				frequency[jj] = tem;
				tem = position[ii];
				position[ii] = position[jj];
				position[jj] = tem;
			}
		}
	}
	
	writer = new PrintWriter("freq3.dat", "UTF-8");
	for(int show = 0; show < bm1.getColumnDimension(); show ++)
	{
		writer.println(frequency[show] + " " + position[show]);
	}
	writer.close();
	
	/*writer = new PrintWriter("frequency.dat");
    for (int var = 0; var < frequency.length; var++) {
              writer.println(frequency[var]);
        }
    writer.close();
    writer = new PrintWriter("position.dat");
    for (int var = 0; var < position.length; var++) {
        writer.println(position[var]);
    }
    writer.close();*/
	/*System.out.println("Top 50 movie recommendation: ");
	for(int show = 0; show < 50; show ++)
	{
		System.out.println(position[show] + " with frequency " + frequency[show]);
	}
	System.out.println("Done!!!");*/
	
	int r = m1.getRowDimension();
	int c = m1.getColumnDimension();
	int l = 0, dl = 0;
	for(int i = 0; i < c; i ++)
	{
		double v = m1.get(user, i);
		if(v > 3.0)
			l ++;
		else
			dl ++;
	}
	int c1 = 0, c2 = 0;
	int likes[] = new int[l];
	int dislikes[] = new int[dl];
	for(int i = 0; i < c; i ++)
	{
		if(m1.get(user, i) > 3.0)
			likes[c1 ++] = i;
		else
			dislikes[c2 ++] = i;
	}
	/*br = new BufferedReader(new FileReader("frequency.dat"));
	line = br.readLine();
	double frequency[] = new double[m.getColumnDimension()];
	double position[] = new double[m.getColumnDimension()];
	int k = 0;
	System.out.println(m.getColumnDimension());
	while(line != null)
	{
		//if(k > 4000)
		//	break;
		frequency[k ++] = Double.parseDouble(line);
		line = br.readLine();
	}
	br = new BufferedReader(new FileReader("position.dat"));
	line = br.readLine();
	k = 0;
	while(line != null)
	{
		//if(k > 4000)
		//	break;
		position[k ++] = Double.parseDouble(line);
		line = br.readLine();
	}
	k = 0;
	/*System.out.println("Frequency array: ");
	for(k = 0; k < m.getColumnDimension(); k ++)
		System.out.println(frequency[k]);*/
	int tp = 0, tn = 0, fp = 0, fn = 0, k = 0;
	//For top 50 recommendation
	int topn = 15;
	for(k = 0; k < topn; k ++)
	{
		double rec = position[k];
		for(int j = 0; j < c1; j ++)
		{
			if(rec == likes[j])
				tp ++;
		}
		for(int j = 0; j < c2; j ++)
		{
			if(rec == dislikes[j])
				fp ++;
		}
	}
	for(k = topn + 1; k < position.length; k ++)
	{
		int drec =(int) position[k];
		for(int j = 0; j < c1; j ++)
		{
			if(drec == likes[j])
				fn ++;
		}
		for(int j = 0; j < c2; j ++)
		{
			if(drec == dislikes[j])
				tn ++;
		}
	}
	//System.out.println(position.length + " " + tp + " " + fp + " " + fn + " " + tn);
	double p=0,re=0;
	if(tp!=0 && fp!=0)
	p = tp/(double)(tp + fp);
	if(tp!=0 && fn!=0)
	 re = tp/(double)(tp + fn);
	System.out.println("Precision: " + p + " and Recall: " + re);
	precision += p;
	recall += re;
	set(frequency, m1.getColumnDimension());
	set(position, m1.getColumnDimension());
	set(pos, ukskh.getRowDimension());
	set(user_values, ukskh.getColumnDimension());
	set(cosine, ukskh.getRowDimension());
}
}
