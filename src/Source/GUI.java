package Source;

import javax.swing.*;

public class GUI extends JFrame{

    private final Space space;

    private static final int timestep = 10;

    public GUI(Space space) {
		this.space = space;
		this.addKeyListener(space);
	}

	public void displayOnscreen() {
		add(space);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Arpegio");
		setVisible(true);

		while(true) {
			space.updateScreen();
			try {
				Thread.sleep(timestep);
			} catch (InterruptedException e) {};
		}
	}
}
