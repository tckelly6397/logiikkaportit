package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class TruthTable {
	private ArrayList<Boolean[]> left = new ArrayList<>();
	private ArrayList<Boolean[]> right = new ArrayList<>();
	
	public TruthTable() {
		
	}

	public ArrayList<Boolean[]> getLeft() {
		return left;
	}

	public void setLeft(ArrayList<Boolean[]> left) {
		this.left = left;
	}

	public ArrayList<Boolean[]> getRight() {
		return right;
	}

	public void setRight(ArrayList<Boolean[]> right) {
		this.right = right;
	}
	
	public void addTruth(Boolean[] inputs, Boolean[] outputs) {
		left.add(inputs);
		right.add(outputs);
	}
	
	public Boolean[] getOutputs(Boolean[] inputs) {
		for(int i = 0; i < left.size(); i++) {
			if(Arrays.equals(inputs, left.get(i)))
				return right.get(i);
		}
		
		return null;
	}
	
	public Boolean[] getOutputs(ArrayList<Boolean> inputs) {
		Boolean[] inp = new Boolean[inputs.size()];
		for(int i = 0; i < inputs.size(); i++)
			inp[i] = inputs.get(i);
		
		Boolean[] out = getOutputs(inp);
		
		return out;
	}
	
	public String toString() {
		String s = "Left: [";
		for(int j = 0; j < left.size(); j++) {
			for(int i = 0; i < left.get(j).length; i++) {
				s += left.get(j)[i];
			}
			
			if(j != right.size() - 1)
				s += ", ";
		}
		
		s += "] , Right: [";
		
		for(int j = 0; j < right.size(); j++) {
			for(int i = 0; i < right.get(j).length; i++) {
				s += right.get(j)[i];
			}
			
			if(j != right.size() - 1)
				s += ", ";
		}
		
		s += "]";
		
		return s;
	}
}
