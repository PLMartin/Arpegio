package Source;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pl-martin on 05/05/17.
 */
public abstract class Items {

    protected Point position;

    protected Point speed;

    protected Image image;

    protected int width;

    protected int height;

    protected Point direction;

    public void move(){
        position.x += speed.x;
        position.y += speed.y;
    }

    public void setImage(String url){
        ImageIcon icon;
        this.image = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource(url));
        icon = new ImageIcon(this.image);
        this.width = icon.getIconWidth();
        this.height = icon.getIconHeight();

    }

}
