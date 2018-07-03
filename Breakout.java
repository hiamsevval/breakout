// TODO: comment this program

import acm.graphics.*;     // GOval, GRect, etc.
import acm.program.*;      // GraphicsProgram
import acm.util.*;         // RandomGenerator
import java.applet.*;      // AudioClip
import java.awt.*;         // Color
import java.awt.event.*;   // MouseEvent

public class Breakout extends GraphicsProgram implements BreakoutConstants {

	private RandomGenerator rgen = RandomGenerator.getInstance();
	GRect rect;
	GRect paddle;
	GLabel label;

	public void run() {

		double vx=rgen.nextDouble(VELOCITY_MIN, VELOCITY_MAX);  
		double vy=rgen.nextDouble(VELOCITY_MIN, VELOCITY_MAX);

		for(int i=0; i<NBRICK_ROWS; i++) {

			for(int j=0; j<NBRICK_ROWS; j++) {
				createRect(BRICK_WIDTH,BRICK_HEIGHT,Color.RED);
				add(rect, i*(BRICK_SEP+BRICK_WIDTH),j*(BRICK_HEIGHT+BRICK_SEP)+100);
				if(1<j && j<4) {
					rect.setColor(Color.ORANGE);
				}
				else if(3<j && j<6) {
					rect.setColor(Color.YELLOW);
				}else if (5<j && j<8) {
					rect.setColor(Color.GREEN);
				}else if (7<j && j<10) { 
					rect.setColor(Color.CYAN);
				}
			}
		}
		displayMessage("CLICK TO PLAY");
		addMouseListeners();
		int plays = 0;
		while(plays<NTURNS) {
			waitForClick();
			GOval ball= new GOval(BALL_RADIUS*2,BALL_RADIUS*2);
			ball.setFilled(true);
			add(ball, getWidth()/2-BALL_RADIUS, getHeight()/2-BALL_RADIUS*2);		


			paddle= new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
			paddle.setFilled(true);
			paddle.setColor(Color.BLACK);
			add(paddle, getWidth()/2-PADDLE_WIDTH/2, getHeight()-PADDLE_HEIGHT-PADDLE_Y_OFFSET);
			

			if (rgen.nextBoolean(0.5)) { 
				vx = -1*vx;
			}

			while(true) {
				ball.move(vx,vy);

				pause(DELAY);	
				if(ball.getX()<=0 || ball.getX()>= getWidth()-BALL_RADIUS) {
					vx=-vx;
				}
				if(ball.getY()<=0){
					vy=-vy;
				}
				if(ball.getY()>=getHeight()-BALL_RADIUS) {
					plays=plays+1;
					remove(ball);
					remove(paddle);
					break;
				}
				double x= ball.getX();
				double y= ball.getY();
				GObject object= getElementAt(x,y);
				GObject object2=getElementAt(x+BALL_RADIUS*2,y);
				GObject object3=getElementAt(x,y+BALL_RADIUS*2);
				GObject object4=getElementAt(x+BALL_RADIUS*2, y+ BALL_RADIUS*2);
				if(object!=null) {
					if(object==paddle) {
						vx=-vx;
					}else {
						remove(object);
						vy=-vy;
					}
				}	
				else if(object2!=null) {
					if(object2==paddle) {
						vx=-vx;
					}else {
						remove(object2);
						vy=-vy;
					}			
				}		
				else if(object3!=null) {
					if(object3==paddle) {
						vy=-vy;
					}else {
						remove(object3);
						vy=-vy;
					}
				}	
				else if(object4!=null) {
					if(object4==paddle) {
						vy=-vy;
					}else {
						remove(object4);
						vy=-vy;
					}
				}

			}
			
		}
       displayMessage("GAME OVER");
	}
    private void displayMessage(String message) {
    	 label= new GLabel(message);
    	 label.setFont("Arial Black-40");
    	 label.setLocation(getWidth()/2-label.getWidth()/2, getHeight()/2-label.getHeight()/2);
    	 add(label);
    }



	public void mouseMoved(MouseEvent e) {
		int x= e.getX();
		if(x>APPLICATION_WIDTH-PADDLE_WIDTH) {
			x=APPLICATION_WIDTH-PADDLE_WIDTH;
		}
		if(x<=0) {
			x=0;
		}
		paddle.setLocation(x,getHeight()-PADDLE_Y_OFFSET-paddle.getHeight());


	}
	private GRect createRect(double x, int y, Color color) {
		rect= new GRect(x,y);
		rect.setFilled(true);
		rect.setColor(color);
		return rect;
	}
	public void mouseClicked() {
		remove(label);
	}



}
