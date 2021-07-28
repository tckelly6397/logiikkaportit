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
}
