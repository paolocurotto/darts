package main;

import java.awt.*;

public class MagDart extends Dart {

    public int i = 0;
    public int k = 0;

	public MagDart() {
	}

	public MagDart(int x_pos, int y_pos, int angle_c) {
		super(x_pos, y_pos, angle_c, Color.magenta);
		color = Color.magenta;
	}

	@Override
	public void draw(int h, int w, Graphics g) {
		super.draw(h, w, g);
	}

    @Override
    public void newTurn () {
        turn = dir_moves[k][1];
        if (i == dir_moves[k][0]) {
            turn = dir_moves[k][1];
            i = 0;
            k++;
            if (k == 200) {
                k = 0;
            }
        }
        i++;

    }

    @Override
    public void reset() {
        super.reset();
        i = 0;
        k = 0;
        turn = 0;
    }

}
