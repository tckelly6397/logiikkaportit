package components;

public abstract class Component {
	private volatile Boolean used;
	public abstract void update();
	
	public Boolean getUsed() {
		return used;
	}
	
	public void setUsed(Boolean used) {
		this.used = used;
	}
	
	public int clamp(int num, int max) {
		if(num > max) return max;
		return num;
	}
}
