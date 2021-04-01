import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SnakeGame_v2 extends PApplet {

// My own project before starting university.


int[][] grid; // the main game board
int x,y; // snake's head coordinates on the board
int vX,vY; // velocity in x and y direction
int foodX,foodY;  // food x and y coordinates
int snakeLength;
final int FRAMERATE = 18; // default game speed
final int DEFAULT_NR_ROWS = 25;
final int DEFAULT_NR_COLS = 25;
boolean gameRunning;
boolean hasTurned; // used for not allowing player to turn several times in one frame
PImage img;
PFont f;

public void setup()
{
  frameRate(FRAMERATE);
  // starting direction is downwards
  vX = 0;
  vY = 1;
  snakeLength = 1;
  x = 0; // head x
  y = 0; // head y
  gameRunning = true;
  hasTurned = false;
  // game screen is 500px horizontally and 570px vertically
  
  grid = new int [DEFAULT_NR_COLS][DEFAULT_NR_ROWS];
  img = loadImage("YouDied.png");
  f = loadFont("ComicSansMS-Bold-40.vlw");
  Grid ();
  foodCoordinates();
  background (20);
}

public void draw() // Main game loop
{
  background(20);
  if(gameRunning)
  {
    MoveSnake();
    MoveHead();
    Display();
    Scoreboard();
    hasTurned = false;
  }
  else 
  {
    image(img, 0, 0);
    EndScore();
  }
}

public void Grid() // Starting grid data is initialised.
{
  for (int i=0; i<DEFAULT_NR_ROWS; i++) for (int j=0; j<DEFAULT_NR_COLS; j++)
  {
    grid [i][j] = 0;
  }
}

public void MoveHead()
{
    if (vY != 0)
    {
      // The snakes head is moved to the other side of the board
      // if it touches the edge and the direction does not change.
      if(y==DEFAULT_NR_ROWS-1 && vY>0) y=0;
      else if(y==0 && vY<0) y=24;
      else y+=vY;
      checkLose();
      checkFood();
    }
    if (vX != 0)
    {
      if(x==DEFAULT_NR_COLS-1 && vX>0) x=0;
      else if(x==0 && vX<0) x=24;
      else x+=vX;
      checkLose();
      checkFood();
    }
}

public void MoveSnake()
{
  for(int i=0; i<DEFAULT_NR_ROWS; i++) 
    for (int j=0; j<DEFAULT_NR_COLS; j++)
    {
     if (grid[i][j]==snakeLength) grid[i][j]=0;
     else if (grid[i][j] > 0) grid[i][j]++;
    }
}

public void keyPressed() // Used for changing direction of the snake.
{
  if (!hasTurned) // this ensures that no more than 1 turn is done in one frame
  {
    if((keyCode == UP || key=='w') && vY==0) 
    {
      vY=-1;
      vX=0;
      hasTurned=true;
    }
    if((keyCode == DOWN || key=='s') && vY==0)
    {
      vY=1;
      vX=0;
      hasTurned=true;
    }
    if((keyCode == LEFT || key=='a') && vX==0)
    {
      vY=0;
      vX=-1;
      hasTurned=true;
    }
    if((keyCode == RIGHT || key=='d') && vX==0)
    {
      vY=0;
      vX=1;
      hasTurned=true;
    }
  }
}

public void foodCoordinates() // Generates coordinates for food.
{ 
  foodX = (int) random(DEFAULT_NR_COLS); // foodX
  foodY = (int) random(DEFAULT_NR_ROWS); // foodY
  if(grid[foodY][foodX]>0 || (foodX==x && foodX==y)) foodCoordinates();
  else grid[foodY][foodX] = -1;
  println(foodX, foodY, x, y);
}

public void checkFood() // Checks if the snake eats food.
{
  if (x==foodX && y==foodY)
  {
    grid[y][x]=1;
    foodCoordinates();
    snakeLength++;
  }
  else grid[y][x]+=1;
}

public void checkLose() // Checks if the snake has bitten itself.
{
  if (grid[y][x]>0) gameRunning=false;
}

public void Display() // Visually displays the game board.
{
 fill(237, 41, 57);
 rect (foodX*20,foodY*20,20,20);
 for(int i=0; i<DEFAULT_NR_ROWS; i++) 
   for (int j=0; j<DEFAULT_NR_COLS; j++)
   {
     fill(255);
     if (grid[i][j] > 0) rect (j*20,i*20,20,20);
   }
}

public void Scoreboard() // Displays the scoreboard.
{
  fill(30);
  rect(0, 500, 500, 70);
  textFont(f);
  fill(237, 41, 57);
  String txt = "Score: " + snakeLength;
  text(txt,10,550);
}    

public void EndScore() // Displays the endscore.
{
  textFont(f);
  fill(237, 41, 57);
  String txt = "Final Score: " + snakeLength;
  text(txt,112,400);
}
  public void settings() {  size (500, 570); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SnakeGame_v2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
