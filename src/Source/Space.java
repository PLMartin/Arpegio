package Source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Space extends JPanel implements KeyListener{

    public final int SIZE_X = 1000;

    public final int SIZE_Y = 800;

    private static final Color BACKGROUND_COLOR = new Color(64, 64, 64);

    private Image gameIMG = null;

    private Graphics graphicContext = null;

    private int command = 5;

    Player player;
    //Player [] player = new Player[10];

    private int nbPlayer;

    Space(){
        this.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		this.addKeyListener(this);
        Point position = new Point((int) (Math.random() * SIZE_X), (int) (Math.random() * SIZE_Y));
        System.out.print(position.x + " " + position.y);
        this.player = new Player();
    }



    public void update(){
		player.move();
		if(player.shots.getSize() > 0) {
			for (int i = 0; i < player.shots.getSize(); ++i) {
				Shots shot = (Shots) player.shots.pop();
				if(!collision(shot)){
					shot.move();
					player.shots.push(shot);
				}

			}
		}
    }

	public boolean collision(Items item){
		if(item.position.x + item.width > SIZE_X || item.position.y + item.height > SIZE_Y ||
				item.position.x < 0 || item.position.y < 0) {
			//player.munitions += 1;
			return true;
		}
		return false;
	}

    public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			/* MOVE */
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				player.speed.y = -5;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				player.speed.y = 5;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
				player.speed.x = 5;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
				player.speed.x = -5;
				break;
			/* DIRECTION */
			case KeyEvent.VK_Z:
				player.setImage("Images/player1up.jpeg");
				player.direction.x = 0;
				player.direction.y = -7;
				break;
			case KeyEvent.VK_Q:
				player.setImage("Images/player1.jpeg");
				player.direction.x = -7;
				player.direction.y = 0;
				break;
			case KeyEvent.VK_S:
				player.setImage("Images/player1down.jpeg");
				player.direction.x = 0;
				player.direction.y = 7;
				break;
			case KeyEvent.VK_D:
				player.setImage("Images/player1right.jpeg");
				player.direction.x = 7;
				player.direction.y = 0;
				break;

			/* ATTACK */
			case KeyEvent.VK_SPACE:
				player.shot(player.direction);
			default:
				System.out.println("got press "+e);
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				player.speed.y = 0;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				player.speed.y = 0;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
				player.speed.x = 0;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
				player.speed.x = 0;
				break;
			case KeyEvent.VK_BACK_SPACE:
				break;

			default:
				System.out.println("got release "+e);
		}
	}
	public void keyTyped(KeyEvent e) { }

	/*
	 * (non-Javadoc) This method is called by the AWT Engine to paint what
	 * appears in the screen. The AWT engine calls the paint method every time
	 * the operative system reports that the canvas has to be painted. When the
	 * window is created for the first time paint is called. The paint method is
	 * also called if we minimize and after we maximize the window and if we
	 * change the size of the window with the mouse.
	 *
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(gameIMG, 0, 0, this);
	}

    public void updateScreen() {
        if (gameIMG == null) {

			/* First time we get called with all windows initialized */

			gameIMG = createImage(SIZE_X, SIZE_Y);
            if (gameIMG == null)
                throw new RuntimeException("Could not instanciate graphics");
            else
                graphicContext = gameIMG.getGraphics();
        }

		/* Fill the area with gray */

        graphicContext.setColor(BACKGROUND_COLOR);
        graphicContext.fillRect(0, 0, SIZE_X, SIZE_Y);

		/* Draw items */

        graphicContext.drawImage(player.image, player.position.x, player.position.y,
				player.width, player.height, null);

		for(int i = 0 ; i < player.shots.getSize(); ++i) {
			Shots shot = player.shots.pop();
			graphicContext.drawImage(shot.image, shot.position.x, shot.position.y,
						shot.width, shot.height, null);

			player.shots.push(shot);
		}
		if(player.timeBeforeNewShot != 0){
			player.timeBeforeNewShot -= 10;
		}
		update();
        this.repaint();
    }




}
