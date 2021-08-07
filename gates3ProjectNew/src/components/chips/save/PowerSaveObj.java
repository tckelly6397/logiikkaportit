package components.chips.save;

import java.util.ArrayList;

public class PowerSaveObj {
	private String label; //wire or node
	private int id;
	private ArrayList<Integer> inputIds;
	private ArrayList<Integer> outputIds;
	private boolean powered;
	private int ngId; //Used for nodes
	
	public PowerSaveObj(int id) {
		this.id = id;
		label = "";
		inputIds = new ArrayList<>();
		outputIds = new ArrayList<>();
		powered = false;
		ngId = -1;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Integer> getInputIds() {
		return inputIds;
	}

	public void setInputIds(ArrayList<Integer> value) {
		this.inputIds = value;
	}

	public void addInputId(int inputId) {
		this.inputIds.add(inputId);
	}

	public ArrayList<Integer> getOutputIds() {
		return outputIds;
	}
	
	public void setOutputIds(ArrayList<Integer> value) {
		this.outputIds = value;
	}
	
	public void addOutputId(int outputId) {
		this.outputIds.add(outputId);
	}

	public boolean isPowered() {
		return powered;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	public int getNgId() {
		return ngId;
	}

	public void setNgId(int ngId) {
		this.ngId = ngId;
	}
	
	public String getInputIdsString() {
		String ids = "";
		ids += "[";
		
		if(!inputIds.isEmpty()) {
			for(int i = 0; i < inputIds.size() - 1; i++) {
				String id = Integer.toString(inputIds.get(i));
				ids += id + ",";
			}
			ids += Integer.toString(inputIds.get(inputIds.size() - 1));
		}
		
		ids += "]";
		
		return ids;
	}
	
	public String getOutputIdsString() {
		String ids = "";
		ids += "[";
		
		if(!outputIds.isEmpty()) {
			for(int i = 0; i < outputIds.size() - 1; i++) {
				String id = Integer.toString(outputIds.get(i));
				ids += id + ",";
			}
			ids += Integer.toString(outputIds.get(outputIds.size() - 1));
		}
		
		ids += "]";
		
		return ids;
	}

	@Override
	public String toString() {
		return "{" +
				"Label:" + label +
				", id:" + id +
				", inputIds:" + getInputIdsString() +
				", outputIds:" + getOutputIdsString() + 
				", powered:" + powered +
				", ngId:" + ngId +
				"}";
	}
}
