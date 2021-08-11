package peripherals;

public interface mouseInterface {
	public void leftClick(int x, int y);
	public void rightClick(int x, int y);
	public void middleClick(int x, int y);
	public void drag(int x, int y);
	public void mouseReleased(int x, int y);
	public void mousePressed(int x, int y);
}
