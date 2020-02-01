package com.example.slidingpuzzlegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;


    public class FifteenSquares extends SurfaceView implements View.OnClickListener, View.OnTouchListener { //variables
        Paint boardPaint = new Paint(Color.BLACK);
        Paint numPaint = new Paint();
        int[][] Squares = new int[4][4];
        static int numOfSquares = 15;
        static int length = 4;
        int startX;
        int startY;
        int endX;
        int endY;
        

    public FifteenSquares(Context context, AttributeSet attrs) //constructor
        {
            super(context, attrs);
            setWillNotDraw(false);
            random();
            boardPaint.setStrokeWidth(10);
            boardPaint.setStyle(Paint.Style.STROKE);
            numPaint.setTextSize(50);
        }

        public void drawBoard(Canvas canvas) { //canvas
            int width = canvas.getWidth();
            int square = width/4;
            for (int row = 0; row < length; row++){
                for (int col = 0; col < length; col++) {
                    Rect rect = new Rect(row*square , col*square, (row*square)+square, (col*square)+ square);
                    canvas.drawRect(rect, boardPaint); //draw grid
                }
            }
        }

        public void drawNumbers(Canvas canvas){ //draws the numbers
            int width = canvas.getWidth();
            int size = width/4;
            int loc = width/8;
            for (int row = 0; row < length; row++){
                for (int col = 0; col < length; col++){
                    if (Squares[row][col] == 0)
                    {
                        //do nothing
                    } else if (Squares[row][col] < 10){ //draw one digit numbers
                        canvas.drawText(String.valueOf(Squares[row][col]), loc + row*size - 15, loc + col*size, numPaint);
                    } else{ //draw two digit numbers
                        canvas.drawText(String.valueOf(Squares[row][col]), loc + row*size - 30, loc + col*size, numPaint);
                    }
                }
            }
        }

        public int SquareOfX(int x){ //finds the square from user touch input (x)
            int size = getWidth()/4;
            //round x value
            if (x > 5 && x < size){
                return 0;
            } else if (x > size && x < (2*size)){
                return 1;
            } else if (x > (2*size) && x < (3*size)){
                return 2;
            } else if (x > (3*size) && x < getWidth()-5){
                return 3;
            }
            return -1;
        }

        public int SquareOfY(int y){ //finds the square from user touch input (y)
            int size = getWidth()/4;
            //round y value
            if(y > 5 && y < size){
                return 0;
            } else if(y > size && y < (2*size)){
                return 1;
            } else if(y > (2*size) && y < (3*size)){
                return 2;
            } else if(y > (3*size) && y < getWidth()-5){
                return 3;
            }
            return -1;
        }

        public void winCondition() //determines if user won
        {
            int i, j;
            int index = 1;
            int errors = 0;
            for (i = 0; i < length; i++) //if the index 1-15 is correct
            {
                for (j = 0; j < length; j++) {
                    if (Squares[j][i] != index)
                        errors += 1;
                    index++;
                }
            }

            if (errors == 1) { //last square should be empty
                setBackgroundColor(Color.GREEN);
                invalidate();
            } else {
                setBackgroundColor(Color.WHITE);
            }
        }

        public void random() //randomizing the squares
        {
            ArrayList<Integer> values = new ArrayList<Integer>();

            //sets an array list with values from 1 - 15 and then shuffles the numbers
            int i;
            for (i = 1; i <= numOfSquares; i++)
                values.add(i);
            Collections.shuffle(values);
            values.add(0); //add a zero for the blank space

            int row, col;
            for (row = 0; row < length; row++) {
                for (col = 0; col < length; col++) {
                    Squares[row][col] = values.get(row + col * length);
                }
            }
        }

        public boolean onTouch(View v, MotionEvent event) { //user touch input

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    startX = SquareOfX(startX);
                    startY = SquareOfY(startY);
                    return true;
                case MotionEvent.ACTION_UP:
                    endX = (int) event.getX();
                    endY = (int) event.getY();
                    endX = SquareOfX(endX);
                    endY = SquareOfY(endY);
                    break;
            }

            if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
                if (Squares[endX][endY] == 0) //if the drag ended on the blank space
                {
                    if ((Math.abs(endX - startX) == 1 && Math.abs(endY - startY) == 0) || (Math.abs(endX - startX) == 0 && Math.abs(endY - startY) == 1)) { //makes sure move is legal
                        Squares[endX][endY] = Squares[startX][startY]; //switch squares
                        Squares[startX][startY] = 0;
                        invalidate();
                        return false;
                    }
                }
            }

            return true;
        }

        public void onDraw(Canvas canvas) //drawing the board
        {
            winCondition();
            drawBoard(canvas);
            drawNumbers(canvas);
        }

        public void onClick(View v) //when random is clicked, it will randomize the board
        {
            random();
            invalidate();
        }


    }
