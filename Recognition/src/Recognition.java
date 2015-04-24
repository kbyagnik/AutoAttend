import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Recognition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[][] inputImage = null;
		int[] inImage = null;						// mn x 1
		double[] meanImage = null, diff = null;		// mn x 1	
		double[][] eigenFace = null; 				// mn x p-1
		double[][] a = null;						// mn x p
		double[] projTestImg = null;				// p-1 x 1
		double[][] projImages = null;				// p-1 x train_no
		double[] eucDist = null;
		
		int projRow = 0, projCol = 0;
		int eRow = 0, eCol = 0;
		int dRow = 0, dCol = 0;
		int train_no = 0, mn, p;
		int projTestRow = 0, projTestCol = 0;
		int eucN = 0;
		
		BufferedReader aReader = null;
		BufferedReader mReader = null;
		BufferedReader eigenReader = null;
		BufferedReader projImg = null;

		try {
			String line;
 
			aReader = new BufferedReader(new FileReader("/home/master/workspace/Recognition/src/A"));
			while ((line = aReader.readLine()) != null) {
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aReader != null)aReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
		/* [irow icol] = size(temp);
		InImage = reshape(temp',irow*icol,1); */
		
		
		reshape(inImage, inputImage, projRow, projCol);
		
		/*
		 * Difference = double(InImage)-m; % Centered test image
ProjectedTestImage = Eigenfaces'*Difference; % Test image feature vector
		 */
		
		difference(diff, meanImage, inImage, projRow*projCol);
		conj_multiply(eigenFace, eRow, eCol, diff, dRow, projTestImg);
		
		
		
		
		
		euc_dist(train_no, projImages, projRow, projCol, projTestImg, projTestRow, projTestCol, eucDist);
		
		
		
		
		/*
					[Euc_dist_min , Recognized_index] = min(Euc_dist);
					OutputName = strcat(int2str(Recognized_index),'.jpg');
		 */
		
		
		int index = get_min_index(eucDist, eucN);
		
//		OutputName = get file name of index
		

	}
	
	static void reshape(int[] inImage, int[][] inputImage, int row, int col)
	{
		int i,j;
		
		for (i=0; i<row; i++)
		{
			for (j=0; j<col; j++)
			{
				inImage[row*i + j] = inputImage[i][j] ;
			}
		}
	}
	
	static void difference(double[] diff, double[] meanImage, int[] inImage, int n)
	{
		int i;
		for(i=0; i<n; i++)
		{
			diff[i] = inImage[i] - meanImage[i];
		}
	}
	
	static void conj_multiply(double[][] eigFace, int eRow, int eCol, double[] diff, int dRow, double[] projTestImg)
	{
		int i,j;
		double sum;
		for (i=0; i<eCol; i++)
		{
			sum=0;
			for (j=0; j<(eRow<dRow?eRow:dRow); j++)
			{
				sum += eigFace[j][i]*diff[j];
			}
			projTestImg[i] = sum;
		}	
	}
	
	static void euc_dist(int train_no, double[][] projImages, int projRow, int projCol, double[] projTestImg, int projTestRow, int projTestCol, double[] eucDist)
	{
		/*
		 * Euc_dist = [];
			for i = 1 : Train_Number
    			q = ProjectedImages(:,i);
    			temp = ( norm( ProjectedTestImage - q ) )^2;
    			Euc_dist = [Euc_dist temp];
			end
*/
		int i,j;
		double sum;
		for(i=0; i<train_no ; i++)
		{
			sum=0;
			for(j=0; j<projRow; j++)
			{
				sum += Math.pow( projTestImg[j] - projImages[i][j], 2) ;
			}
			eucDist[i] = sum;
		}
		
		
	}

	static int get_min_index(double[] eucDist, int n)
	{
		int i, index=0;
		double min=eucDist[0];
		for (i=1; i<n; i++)
		{
			if(eucDist[i]<min)
			{
				index=i;
				min=eucDist[i];
			}
		}
		return index;
	}
	
	
	
}
