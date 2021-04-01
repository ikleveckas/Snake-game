int[][] grid;
int x,y,vX,vY,fX,fY;
int ilgis;
boolean gameRunning;
boolean hasTurned;
PImage img;
PFont f;
void setup()
{
  frameRate(18);
  vX = 0;
  vY = 1;
  ilgis = 1;
  x = 0; // galvos x
  y = 0; // galvos y
  gameRunning = true;
  hasTurned = false;
  size (500,570);
  grid = new int [25][25];
  img = loadImage("YouDied.png");
  f = loadFont("ComicSansMS-Bold-40.vlw");
  Grid ();
  foodCoordinates();
  background (20);
}

void draw()
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

void Grid() // Tinkelis skirtas informacijai saugoti.
{
  for (int i=0; i<25; i++) for (int j=0; j<25; j++)
  {
    grid [i][j] = 0;
  }
}

void MoveHead()
{
    if (vY != 0)
    {
      if(y==24 && vY>0) y=0;
      else if(y==0 && vY<0) y=24;
      else y+=vY;
      checkLose();
      checkFood();
    }
    if (vX != 0)
    {
      if(x==24 && vX>0) x=0;
      else if(x==0 && vX<0) x=24;
      else x+=vX;
      checkLose();
      checkFood();
    }
}

void MoveSnake()
{
  for(int i=0; i<25; i++) for (int j=0; j<25; j++)
  {
   if (grid[i][j]==ilgis) grid[i][j]=0;
   else if (grid[i][j] > 0) grid[i][j]++;
  }
}

void keyPressed()
{
  if (!hasTurned)
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

void foodCoordinates()
{ 
  fX = (int) random(25); // foodX
  fY = (int) random(25); // foodY
  println(fX, fY, x, y);
  if(grid[fY][fX]>0 || (fX==x && fX==y)) foodCoordinates();
  else grid[fY][fX] = -1;
}

void checkFood()
{
  if (x==fX && y==fY)
  {
    grid[y][x]=1;
    foodCoordinates();
    ilgis++;
  }
  else grid[y][x]+=1;
}

void checkLose()
{
  if (grid[y][x]>0) gameRunning=false;
}

void Display()
{
 fill(237, 41, 57);
 rect (fX*20,fY*20,20,20);
 for(int i = 0; i < 25; i++) for (int j = 0; j < 25; j++)
 {
   fill(255);
   if (grid[i][j] > 0) rect (j*20,i*20,20,20);
 }
}

void Scoreboard()
{
  fill(30);
  rect(0, 500, 500, 70);
  textFont(f);
  fill(237, 41, 57);
  String txt = "Score: " + ilgis;
  text(txt,10,550);
}    

void EndScore()
{
  textFont(f);
  fill(237, 41, 57);
  String txt = "Final Score: " + ilgis;
  text(txt,112,400);
}
