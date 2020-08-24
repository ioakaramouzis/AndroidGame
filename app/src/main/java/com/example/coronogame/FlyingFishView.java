package com.example.coronogame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View
{

    private Bitmap fish [] = new Bitmap[2];
    private  int fishX = 10;
    private  int fishY = 550;
    private  int fishSpeed;

    private  boolean touch = false;

    private int  canvasWidth,canvasHeight;
    private  int yellowX,yellowY,yellowSpeed = 16;
    private  Paint yellowPaint = new Paint();
    private  int score = 0,lifeCounterOfMask = 3;

    private  int greenX,greenY,greenSpeed = 20;
    private  Paint greenPaint = new Paint();


    private  int redX,redY,redSpeed =60;
    private  Paint redPaint = new Paint();


    private  Bitmap BackGroundImage;
    private Paint ScorePaint = new Paint();
    private  Bitmap life [] = new Bitmap[2];
    private  Bitmap mask;
    private Bitmap scissor, corono2green;



    public FlyingFishView(Context context)
    {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        BackGroundImage= BitmapFactory.decodeResource(getResources(),R.drawable.background);
        mask= BitmapFactory.decodeResource(getResources(),R.drawable.covid);
        scissor = BitmapFactory.decodeResource(getResources(),R.drawable.scissor);
        corono2green = BitmapFactory.decodeResource(getResources(),R.drawable.corono2);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);


       greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);


       redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        ScorePaint.setColor(Color.WHITE);
        ScorePaint.setTextSize(70);
        ScorePaint.setTypeface(Typeface.DEFAULT);
        ScorePaint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);



    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(BackGroundImage, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;
        if (fishY < minFishY) {

            fishY = minFishY;
        }
        if (fishY > maxFishY) {

            fishY = maxFishY;
        }


        fishSpeed = fishSpeed + 2;

        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {

            canvas.drawBitmap(fish[0], fishX, fishY, null);

        }

       //yellow = corona icon
        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX,yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }
        if(score>100)
        {
            redSpeed= 150;
        }


        if (yellowX < 0 )
        {
            yellowX= canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY-minFishY) ) + minFishY;

        }

        canvas.drawBitmap(mask,yellowX,yellowY,null);

        // yellow = corona icon



      greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX,greenY))
        {
            score = score + 20;
            greenX = -100;
        }
        if(score>100)
        {
            redSpeed= 40;
        }




        if (greenX < 0)
        {
            greenX= canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY-minFishY) ) + minFishY;

        }

        canvas.drawBitmap(corono2green,greenX,greenY,null);


        redX = redX -redSpeed;

        if(hitBallChecker(redX,redY))
        {

            redX = -100;
            lifeCounterOfMask--;
            if(lifeCounterOfMask == 0)
            {
                Toast toast = Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                redSpeed=50;
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                //gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("SCORE", score);
                getContext().startActivity(gameOverIntent);


            }
        }


        if (redX < 0)
        {
            redX= canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY-minFishY) ) + minFishY;
        }

        canvas.drawBitmap(scissor,redX,redY,null);

        canvas.drawText("Score  :  " + score, 20,60,ScorePaint );
        ScorePaint.setColor(Color.BLACK);

        for(int i =0 ; i<3;  i++)
        {

            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i<lifeCounterOfMask)
            {
                canvas.drawBitmap(life[0], x, y,null);

            }
            else
                {
                    canvas.drawBitmap(life[1],x,y,null);
                }
        }












    }


    public  boolean hitBallChecker(int x,int y)
    {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {

            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;

            fishSpeed= -22;


        }
        return  true;
    }
}
