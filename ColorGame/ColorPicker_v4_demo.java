/* This is Color Picker v4.1
 * By @cam-c7
 * Method to extract color names and hex values from .txt file by @g2-games
 * Text file containing 29,780 colors and their hex values from 
 * https://github.com/meodai/color-names 
 */
import acm.program.*;
import java.awt.*;
import acm.graphics.*;
import java.awt.event.*;

public class ColorPicker_v4_demo extends GraphicsProgram {
//runs from strings inputted by user
	private int redValue;
	private int greenValue;
	private int blueValue;
	private boolean INCREMENT_VALUE = false;
	//defining slider parameters
	private static final int SLIDER_X = 200;
	private static final int RED_SLIDER_Y = 325;
	private static final int GREEN_SLIDER_Y = 425;
	private static final int BLUE_SLIDER_Y = 525;
	private static final int SLIDER_HEIGHT = 50;
	private static final int SLIDER_LENGTH = 400;
	private static final int END_OF_SLIDER = SLIDER_X+SLIDER_LENGTH-SLIDER_HEIGHT;
	//submit button parameters
	private static final int SUBMIT_X = 150;
	private static final int SUBMIT_Y = 150;
	private static final int SUBMIT_WIDTH = 100;
	private GOval submitButton;
	private GLabel submitText;
	//skip button parameters
	private static final int SKIP_X = 250;
	private static final int SKIP_Y = 202;
	private static final int SKIP_WIDTH = 50;
	private GOval skipButton;
	private GLabel skipText;
	//various other fields and global variables
	public boolean SUBMIT = false;
	private static final int SCORE_X = 350;
	private GOval nextButton;
	private GLabel nextText;
	private boolean GO_TO_NEXT= false;
	
	public void run() {
		
		String[] colors = new String[] {"87CEEB", "967BB6", "DDBCBC", "E2B330", "AA4A44", "009E60", "F28500", "8591A2", "211338"};
		String[] colorNames = new String[] {"Sky Blue", "Lavender", "Fizzing Whizbees", "Buttercup Yellow", "Brick", "Shamrock", "Tangerine", "Fantasy Romance", "Purple Obsidian"};
	
		int score = 0;
		//set up canvas
		setSize(800, 700);
		setBackground(Color.DARK_GRAY);
		pause(500);	
		int random = 0;
		//while loop that repeats for each new color(aka new round), continues forever
		while (true) {
			//set colors
			String hexColor = colors[random];
			String colorName = colorNames[random];
			if(random < colors.length-1) {
				random ++;
			} else {
				random = 0;
			}
			//hex for debugging
			println(hexColor);
			
			//set user's values to white for user selected color
			redValue = 255;
			greenValue = 255;
			blueValue = 255;
			
			//drawing sliders
			sliderDrawer (RED_SLIDER_Y, Color.RED);
			sliderDrawer (GREEN_SLIDER_Y, Color.GREEN);
			sliderDrawer (BLUE_SLIDER_Y, Color.BLUE);
			//boxes on sliders
			boxDrawer(END_OF_SLIDER, RED_SLIDER_Y);
			boxDrawer(END_OF_SLIDER, GREEN_SLIDER_Y);
			boxDrawer(END_OF_SLIDER, BLUE_SLIDER_Y);
			//submit button
			submitButton = new GOval(SUBMIT_X, SUBMIT_Y, SUBMIT_WIDTH, SUBMIT_WIDTH);
			submitButton.setFilled(true);
			submitButton.setColor(new Color(0, 200, 0));
			submitText = new GLabel("SUBMIT", SUBMIT_X+SUBMIT_WIDTH/2, SUBMIT_Y+SUBMIT_WIDTH/2);
			submitText.setFont("sans serif-20");
			submitText.setColor(new Color(0,0,0,0));
			add(submitText);
			submitText = new GLabel("SUBMIT", SUBMIT_X+SUBMIT_WIDTH/2-submitText.getWidth()/2, SUBMIT_Y+SUBMIT_WIDTH/2+submitText.getHeight()/4);
			submitText.setFont("sans serif-20");
			add(submitButton);
			add(submitText);
			//skip button
			skipButton = new GOval(SKIP_X, SKIP_Y, SKIP_WIDTH, SKIP_WIDTH);
			skipButton.setFilled(true);
			skipButton.setColor(new Color(217, 56, 30));
			add(skipButton);
			skipText = new GLabel("SKIP", SKIP_X+SKIP_WIDTH/2, SKIP_Y+SKIP_WIDTH/2);
			skipText.setFont("sans serif-16");
			skipText.setColor(new Color(0,0,0,0));
			add(skipText);
			skipText = new GLabel("SKIP", SKIP_X+SKIP_WIDTH/2-skipText.getWidth()/2, SKIP_Y+SKIP_WIDTH/2+skipText.getHeight()/4);
			skipText.setFont("sans serif-16");
			add(skipText);
			//color name
			GLabel colorClue = new GLabel("Match this color: " + colorName, 600, 100);
			colorClue.setColor(new Color(0,0,0,0));
			colorClue.setFont("sans serif-24");
			add(colorClue);
			colorClue = new GLabel("Match this color: " + colorName, (getWidth()-colorClue.getWidth())/2, 50);
			colorClue.setColor(Color.WHITE);
			colorClue.setFont("sans serif-24");
			add(colorClue);
			//score
			GLabel scoreText = new GLabel("Score: " + score, SCORE_X, 300);
			scoreText.setFont("sans serif-24");
			scoreText.setColor(Color.WHITE);
			add(scoreText);
			//displays values for red, green, and blue
			GLabel red = new GLabel("Red: "+redValue, SLIDER_X-100, RED_SLIDER_Y+(SLIDER_HEIGHT/2));
			GLabel green = new GLabel("Green: "+greenValue, SLIDER_X-100, GREEN_SLIDER_Y+(SLIDER_HEIGHT/2));
			GLabel blue = new GLabel("Blue: "+blueValue, SLIDER_X-100, BLUE_SLIDER_Y+(SLIDER_HEIGHT/2));
			add(red);
			add(green);
			add(blue);
			//initializes user's selected color and draws
			GRect totalColor = new GRect(350, 150, 100, 100);
			totalColor.setFilled(true);
			totalColor.setFillColor(new Color(redValue, greenValue, blueValue));
			add(totalColor);
			//prints label of user's color
			GLabel userColor = new GLabel("Your Color: ", 350, 140);
			userColor.setFont("sans serif-20");
			userColor.setColor(Color.WHITE);
			add(userColor);
			addMouseListeners();
			//updates along with mouse click
			while(INCREMENT_VALUE != true) {
				remove(red);
				remove(green);
				remove(blue);
				remove(totalColor);
				//updates to show user's selected color values
				red = new GLabel("Red: "+redValue, SLIDER_X-25-red.getWidth(), RED_SLIDER_Y+(SLIDER_HEIGHT/2)+(red.getHeight()/4));
				red.setFont("sans serif-24");
				red.setColor(Color.WHITE);
				green = new GLabel("Green: "+greenValue, SLIDER_X-25-green.getWidth(), GREEN_SLIDER_Y+(SLIDER_HEIGHT/2)+(green.getHeight()/4));
				green.setFont("sans serif-24");
				green.setColor(Color.WHITE);
				blue = new GLabel("Blue: "+blueValue, SLIDER_X-25-blue.getWidth(), BLUE_SLIDER_Y+(SLIDER_HEIGHT/2)+(blue.getHeight()/4));
				blue.setFont("sans serif-24");
				blue.setColor(Color.WHITE);
				add(red);
				add(green);
				add(blue);
				//rectangle in middle showing current user's color
				totalColor = new GRect(350, 150, 100, 100);
				totalColor.setFilled(true);
				totalColor.setFillColor(new Color(redValue, greenValue, blueValue));
				add(totalColor);
				pause(33);
			}
			//HEX TO RGB CONVERTOR
			int actualRedValue = Integer.valueOf( hexColor.substring( 0, 2 ), 16 );
            int actualGreenValue = Integer.valueOf( hexColor.substring(2, 4 ), 16 );
            int actualBlueValue = Integer.valueOf( hexColor.substring(4, 6), 16);
			//prints label of user's color
			GLabel actualColorText = new GLabel("Actual Color:", 545, 140);
			actualColorText.setFont("sans serif-20");
			actualColorText.setColor(Color.WHITE);
			add(actualColorText);
            GRect actualColor = new GRect(550, 150, 100, 100);
			actualColor.setFilled(true);
			actualColor.setFillColor(Color.decode("0x" + hexColor));
			add(actualColor);
			//show actual color labels
			GLabel actualRed = new GLabel("Actual: "+actualRedValue, SLIDER_X+SLIDER_LENGTH+25, RED_SLIDER_Y+(SLIDER_HEIGHT/2)+(red.getHeight()/4));
			actualRed.setFont("sans serif-24");
			actualRed.setColor(Color.WHITE);
			GLabel actualGreen = new GLabel("Actual: "+actualGreenValue, SLIDER_X+SLIDER_LENGTH+25, GREEN_SLIDER_Y+(SLIDER_HEIGHT/2)+(green.getHeight()/4));
			actualGreen.setFont("sans serif-24");
			actualGreen.setColor(Color.WHITE);
			GLabel actualBlue = new GLabel("Actual: "+actualBlueValue, SLIDER_X+SLIDER_LENGTH+25, BLUE_SLIDER_Y+(SLIDER_HEIGHT/2)+(blue.getHeight()/4));
			actualBlue.setFont("sans serif-24");
			actualBlue.setColor(Color.WHITE);
			add(actualRed);
			add(actualGreen);
			add(actualBlue);
			int tempScore = score;
			if(SUBMIT != false) {
				//calculate score
				score = calculateScore(score, actualRedValue, redValue);
				score = calculateScore(score, actualGreenValue, greenValue);
				score = calculateScore(score, actualBlueValue, blueValue);
				int roundScore = score-tempScore;
				//display that round's score
				GLabel roundScoreText = new GLabel("Score: " + roundScore, SCORE_X+195, 300);
				roundScoreText.setFont("sans serif-24");
				roundScoreText.setColor(Color.WHITE);
				add(roundScoreText);
			}
			//reprint updated score
			remove(scoreText);
			scoreText = new GLabel("Score: " + score, SCORE_X, 300);
			scoreText.setFont("sans serif-24");
			scoreText.setColor(Color.WHITE);
			add(scoreText);
			//next button
			nextButton = new GOval(SKIP_X, SKIP_Y-SKIP_WIDTH-5, SKIP_WIDTH, SKIP_WIDTH);
			nextButton.setFilled(true);
			nextButton.setColor(new Color(140, 140, 255));
			add(nextButton);
			nextText = new GLabel("NEXT", SKIP_X+SKIP_WIDTH/2, SKIP_Y+SKIP_WIDTH/2-SKIP_WIDTH-5);
			nextText.setFont("sans serif-16");
			nextText.setColor(new Color(0,0,0,0));
			add(nextText);
			nextText = new GLabel("NEXT", SKIP_X+SKIP_WIDTH/2-skipText.getWidth()/2, SKIP_Y+SKIP_WIDTH/2-SKIP_WIDTH-5+skipText.getHeight()/3);
			nextText.setFont("sans serif-16");
			add(nextText);
			//pause until clicked
			while(GO_TO_NEXT != true) {
				pause(100);
			}
			//resetting for next round
			INCREMENT_VALUE = false;
			GO_TO_NEXT = false;
			setSubmitFalse();
			removeAll();
		} //end of while(true) loop
	} //end of run
	//mouse dragged and released (clicked) function
	@Override public void mouseDragged(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		GObject clickedOn = getElementAt(x,y);
		if(clickedOn != null) {
			if(y > RED_SLIDER_Y && y < RED_SLIDER_Y+50) {
				redValue = sliders(y, x, RED_SLIDER_Y, true, false, false);
			} else if(y > GREEN_SLIDER_Y && y < GREEN_SLIDER_Y+50) {
				greenValue = sliders(y, x, GREEN_SLIDER_Y, false, true, false);
			} else if(y > BLUE_SLIDER_Y && y < BLUE_SLIDER_Y+50) {
				blueValue = sliders(y, x, BLUE_SLIDER_Y, false, false, true);
			}
		}
	}
	@Override public void mouseReleased(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		GObject clickedOn = getElementAt(x,y);	
		if(clickedOn != null) {
			if(y > RED_SLIDER_Y && y < RED_SLIDER_Y+50) {
				redValue = sliders(y, x, RED_SLIDER_Y, true, false, false);
			} else if(y > GREEN_SLIDER_Y && y < GREEN_SLIDER_Y+50) {
				greenValue = sliders(y, x, GREEN_SLIDER_Y, false, true, false);
			} else if(y > BLUE_SLIDER_Y && y < BLUE_SLIDER_Y+50) {
				blueValue = sliders(y, x, BLUE_SLIDER_Y, false, false, true);
			} else if (clickedOn == submitButton || clickedOn == submitText) {
				//when submit button pressed
				INCREMENT_VALUE = true;
				setSubmitTrue();
			} else if (clickedOn == skipButton || clickedOn == skipText) {
				//when submit button pressed
				INCREMENT_VALUE = true;
				setSubmitFalse();
			} else if (clickedOn == nextButton || clickedOn == nextText) {
				//when submit button pressed
				GoToNext();
			} 
		}
	}
//methods
	private void setSubmitTrue() {
		SUBMIT = true;
	}
	private void setSubmitFalse() {
		SUBMIT = false;
	}
	private void GoToNext() {
		GO_TO_NEXT = true;
	}
	private int sliders (double mouseY, double mouseX, int sliderYValue, boolean red, boolean green, boolean blue) {
		//setting color value based on mouse X position
		int colorValue = 0;
		Color rectColor = new Color(0, 0, 0);
		//makes it so rounded edges don't change value b/c circle slider doesn't move
		colorValue = 255 * ((int)mouseX-SLIDER_X-SLIDER_HEIGHT/2) / (SLIDER_LENGTH-SLIDER_HEIGHT);
		//prevents errors of color values
		if(colorValue <= 0) {
			colorValue = 0;
		} else if (colorValue >= 255) {
			colorValue = 255;
		}
		//changes color of rectangle, based on boolean passed
		if(red == true) {
			rectColor = new Color(colorValue, 0, 0);
		} else if (green == true) {
			rectColor = new Color(0, colorValue, 0);
		} else if (blue == true) {
			rectColor = new Color(0, 0, colorValue);
		}	
		sliderDrawer(sliderYValue, rectColor);
		if(mouseX > END_OF_SLIDER+25) {
		//draws slider circle on top
			boxDrawer(END_OF_SLIDER, sliderYValue);
		} else if (mouseX < SLIDER_X+25) {
		//draws slider circle on bottom
			boxDrawer(SLIDER_X, sliderYValue);
		} else {
		//draws slider circle in line with mouse
			boxDrawer((int)mouseX-25, sliderYValue);
		}
		return colorValue;
	}
	//color slider drawer
	private void sliderDrawer (int y, Color color) {
		GRoundRect slider = new GRoundRect(SLIDER_X, y, SLIDER_LENGTH, SLIDER_HEIGHT, SLIDER_HEIGHT);
		slider.setFilled(true);
		slider.setFillColor(color);
		add(slider);
	}
	//box on slider drawer
	private void boxDrawer (int x, int y) {
		GOval startingBox = new GOval(x, y, SLIDER_HEIGHT, SLIDER_HEIGHT);
		startingBox.setFilled(true);
		startingBox.setColor(Color.GRAY);
		add(startingBox);
	}
	//calculates score for each color value
	private int calculateScore(int score, int actualColorValue, int colorValue) {
		if(calculateScoreTotal(actualColorValue, colorValue) > 0) {
			score += calculateScoreTotal(actualColorValue, colorValue);
			return(score);
		}
		else {
			return(score);
		}
	}
	//points to add
	private double calculateScoreTotal(int actualColorValue, int colorValue) {
		double score = -(Math.pow(Math.abs(actualColorValue-colorValue), 2)/30)+1000/3;
		println(score);
		//double score = Math.pow(1.05, 213.451-Math.abs(actualColorValue-colorValue));
		return(score);
	}
} // end of class
