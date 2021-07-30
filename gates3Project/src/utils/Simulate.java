package utils;

import java.util.ArrayList;

import gates3Project.Initialize;

public class Simulate {
	
	public static TruthTable getTheTruth() {
		TruthTable table = new TruthTable();
		
		ArrayList<String> stringInput = genInp(Initialize.e.getInputNodes().size());
		ArrayList<Boolean[]> tableIn = new ArrayList<>();
		ArrayList<Boolean[]> tableOut = new ArrayList<>();
		
		for(String s : stringInput) {
			Boolean[] bools = new Boolean[s.length()];
			
			for(int i = 0; i < s.length(); i++) {
				Boolean theBool = false;
				
				if(s.charAt(i) == '1') 
					theBool = true;
				else if(s.charAt(i) == '0') 
					theBool = false;
				
				Initialize.e.getInputNodes().get(i).setPowered(theBool);
				Initialize.e.getInputNodes().get(i).update();
				try {
					Thread.sleep(10);
				} catch(InterruptedException e) {
					
				}
				bools[i] = theBool;
			}
			
			tableIn.add(bools);
			tableOut.add(getTableOut());
			
			table.addTruth(bools, getTableOut());
		}
		
		return table;
	}
	
	public static Boolean[] getTableOut() {
		Boolean[] bools = new Boolean[Initialize.e.getOutputNodes().size()];
		
		for(int i = 0; i < Initialize.e.getOutputNodes().size(); i++) {
			bools[i] = Initialize.e.getOutputNodes().get(i).isPowered();
		}
		
		return bools;
	}
	
	//Get all inputs
    public static ArrayList<String> genInp(int x) {
        ArrayList<String> inp = new ArrayList<>();
        for(int i = 0; i < Math.pow(2, x); i++)
            inp.add(fillEnds(toBinary(i), '0', x));
        return inp;
    }
    
    //Convert the number to binary
    private static String toBinary(int n) {
        String sb = "";
        while (n > 0) {
        sb += (n % 2);
            n /= 2;
        }
        sb = toReverse(sb);   
        return sb.toString();
    }
      
    //Reverse the String
    private static String toReverse(String s) {
        String n = "";
        for(int i = s.length() - 1; i >= 0; i--)
            n += s.charAt(i);  
        return n;
    }
    
    //Fill the string with the given char
    private static String fillEnds(String s, char c, int t) {
        if(s.length() == t) return s;
        while(s.length() < t)
            s = c + s;
        return s;
    }
}
