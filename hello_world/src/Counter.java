
public class Counter {
	
	public int countTriples(String str)
	{
		int counter=0;
		for(int i=2; i<str.length(); i++)
		{
			if(str.charAt(i-2) == str.charAt(i-1) && str.charAt(i-2) == str.charAt(i))
			{
				counter++;
			}
		}
		
		System.out.println(counter);
		
		return counter;
	}
	
	public void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Hello, world!");
		
		countTriples("AAAaaaBBB");
	}

}