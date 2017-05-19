import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BowlingGame {
	
	public OneShot oneShots[];
	int extranum;
	char extraShots[];
	enum CalculateMethod{FRAME, SPARE, NORMAL};
	
	public class OneShot{
		
		public OneShot()
		{
			
		}
			
	     public	OneShot(String str)
		 {
	    	 if(str.length() == 1)
	    	 {
	    		 first_ = str.charAt(0);
	    		 second_ = first_;
	    		 _type = CalculateMethod.FRAME;
	    		 firstScore = 10;
	    		 secondScore = 0;
	    	 }
	    	 else
	    	 {
	    		 first_ = str.charAt(0);
	    		 second_ = str.charAt(1);
	    		 if(second_ == '/')
	    		 {
	    			 _type = CalculateMethod.SPARE;
	    			 if(first_ == '-')
	    			 {
	    				 firstScore = 0;
	    				 secondScore = 10;
	    			 }
	    			 else
	    			 {
	    				 firstScore = Character.getNumericValue((int)str.charAt(0));
	    				 secondScore = 10 - firstScore;
	    			 }
	    		 }
	    		 else
	    		 {
	    			 _type = CalculateMethod.NORMAL;
	    			 if(first_ == '-')
	    			 {
	    				 firstScore = 0;
	    			 }
	    			 else
	    			 {
	    				 firstScore = Character.getNumericValue((int)str.charAt(0));
	    			 }	 
	    			 
	    			 if(second_ == '-')
	    			 {
	    				 secondScore = 0;
	    			 }
	    			 else
	    			 {
	    				 secondScore = Character.getNumericValue((int)str.charAt(1));
	    			 }	
	    		 }
	    	 }
		 }
	     
	     public String ToString()
	     {
	    	 String string = "firstScore : " + firstScore;
	    	 string += "  secondScore : " + secondScore;
	    	 string += "   type_ : " + _type;
	    	 return string;
	     }
			
		char first_;
		char second_;
		int firstScore;
		int secondScore;
		public CalculateMethod _type;
	
	}
	
	public void Initialization(String bowlingCode)
	{
		oneShots = new OneShot[11];
		
		String _str[] = bowlingCode.split("\\|\\|");
		
		String _str2[] = _str[0].split("\\|");

		for(int i = 0; i < _str2.length; i++)
		{
			OneShot oShot = new OneShot(_str2[i]);
			oneShots[i] = oShot;
		}
		
		oneShots[10] = new OneShot(new String("00"));
		if(_str.length == 1)
		{
			extranum = 0;
			extraShots = null;
		}
		else {
			extranum = _str[1].length();
			extraShots = new char[extranum];
			int[] extrascore = new int[extranum];
			for(int i = 0; i < extranum; i++)
			{
				extraShots[i] = _str[1].charAt(i);
				if(extraShots[i] != 'X' && extraShots[i] != '-')
				{
					extrascore[i] =   Character.getNumericValue((int)extraShots[i]);
				}
				else if(extraShots[i] == 'X')
				{
					extrascore[i] = 10;
				}
				else if(extraShots[i] == '-')
				{
					extrascore[i] = 0;
				}
			}
			
			if(extranum == 1)
			{
				oneShots[10].firstScore = extrascore[0];
			}
			else if(extranum == 2)
			{
				oneShots[10].firstScore = extrascore[0];
				oneShots[10].secondScore = extrascore[1];
			}
		}
	}
	
	public String ToString()
	{
		String str = new String();
		for(int i = 0; i < 11; i++)
		{
			str += oneShots[i].ToString();
			str += "\n";
		}
		str += "extra num : " + extranum;
		str += "\n";
		return str;
	}
	
	public int CalculateSocre()
	{
		int result = 0;
		for(int i = 0 ; i < 10; i++)
		{
			if(oneShots[i]._type == CalculateMethod.FRAME)
			{
				result += 10;
				if(oneShots[i+1]._type != CalculateMethod.FRAME)
				{
					result += oneShots[i+1].firstScore + oneShots[i+1].secondScore;
				}
				else {
					result += 10;
					result += oneShots[i+2].firstScore;
				}
			}
			else if(oneShots[i]._type == CalculateMethod.SPARE)
			{
				result += 10;
				result += oneShots[i+1].firstScore;
			}
			else 
			{
				result += oneShots[i].firstScore;
				result += oneShots[i].secondScore;
			}
		}
		return result;
	}
	
	public int getBowlingScore(String bowlingCode) {
		Initialization(bowlingCode);
		return CalculateSocre();
	       
	}
	 
	 public static void main(String[] args) throws IOException{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String str = null;
			str = br.readLine();
			BowlingGame bowlingGame = new BowlingGame();
			System.out.println("score : " + bowlingGame.getBowlingScore(str));
		}
}
