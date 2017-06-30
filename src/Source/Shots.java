package Source;


import java.awt.*;

public class Shots extends Items {

    Shots(Point origine, Point direction){
        setImage("Images/ball.jpeg");
        position = new Point(origine.x, origine.y+15);
        speed = direction;
    }



}
