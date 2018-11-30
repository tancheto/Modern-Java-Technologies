
public class Anagram {
	
	public static boolean isAnagram(String input)
	{
		String[] parts = input.split(" ");
		
		String word1 = parts[0];
		String word2 = parts[1];
		
		int[] arr = new int [26];
		
		word1=word1.toLowerCase();
		word2=word2.toLowerCase();		
		
		for(int i=0; i<word1.length(); i++)
		{
			if(word1.charAt(i)>='a' && word1.charAt(i)<='z')
			{
				arr[word1.charAt(i)-'a']++;
			}
		}
		
		for(int j=0; j<word2.length(); j++)
		{
			if(word2.charAt(j)>='a' && word2.charAt(j)<='z')
			{
				arr[word2.charAt(j)-'a']--;
			}
		}
		
		for(int n=0; n<26; n++)
		{
			if (arr[n]!=0)
			{
				return false;
			}
		}
		
		return true;
		

		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(isAnagram("night thing"));
		System.out.println(isAnagram("tired dire"));
		System.out.println(isAnagram("eaT ate$"));
		System.out.println(isAnagram("beer beere"));
		
	}

}
