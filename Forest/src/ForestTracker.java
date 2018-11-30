import java.util.Arrays;

public class ForestTracker {
	
	public static char[][] trackForestTerrain(char[][] forest, int years)
	{
		int rows=forest.length;
		int cols=forest[0].length;
		
		char [][] changed_forest= new char [rows][cols];
		
		int count=10;
		int neighbours=0;
		
		while(count<=years)
		{
		
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				neighbours=0;
				
				switch(forest[i][j])
				{
				case '4':
					if(i>0)
					{
						if(forest[i-1][j] == '4') {neighbours++;}
						if(j>0 && forest[i-1][j-1] == '4'){neighbours++;}
						if(j<cols-1 && forest[i-1][j+1] == '4'){neighbours++;}
					}
					
					if(j>0 && forest[i][j-1] == '4') {neighbours++;}
					if(j<cols-1 && forest[i][j+1] == '4') {neighbours++;}
					
					if(i<rows-1)
					{
					if(j>0 && forest[i+1][j-1] == '4') {neighbours++;}
					if(forest[i+1][j] == '4') {neighbours++;}
					if(j<cols-1 && forest[i+1][j+1] == '4') {neighbours++;}
					}
				
					if(neighbours>=3) {changed_forest[i][j]='3';}
					else {changed_forest[i][j]='4';}
					break;
				case '1': 
					changed_forest[i][j]='2';
					break;
				case '2':
					changed_forest[i][j]='3';
					break;
				case '3':
					changed_forest[i][j]='4';
					break;
				default:
					changed_forest[i][j]=forest[i][j];
					break;						
				}
				
			}
			
		}	
		
		forest=changed_forest;
		changed_forest= new char[rows][cols];
		
		count+=10;
		}
		
		return forest;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		char[][]forest= {{'4', '2', '3','3', '2', '4'}
						};
		
		forest=trackForestTerrain(forest, 30);
		
		
		for(int i=0; i<1; i++)
		{
			for(int j=0; j<6; j++)
			{
				System.out.print(forest[i][j] + " ");
			}
			System.out.println();
		}
		
			

	}

}
