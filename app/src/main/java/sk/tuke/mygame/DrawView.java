package sk.tuke.mygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import java.util.Random;

public class DrawView extends View {

    Context context;
    float ballX, ballY;
    Velocity velocity = new Velocity(30, 37);
    Handler handler;
    final long UPDATE_MILLS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint = new Paint();
    float TEXT_SIZE = 120;
    float paddleX, paddleY;
    float oldX, oldPaddleX;
    int points = 0;
    int life = 3;
    Bitmap ball, paddle;
    int dWidth, dHeight;
    int ballWidth, ballHeight;
    MediaPlayer mpHit, mpMiss;
    Random random;
    Brick[] bricks = new Brick[40];
    int numBricks = 0;
    int brokenBricks = 0;
    boolean gameOver = false;

    public DrawView(Context context) {

        super(context);
        this.context = context;

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.board);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        mpHit = MediaPlayer.create(context, R.raw.hit);
        mpMiss = MediaPlayer.create(context, R.raw.miss);

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);
        brickPaint.setColor(Color.argb(255, 33, 55, 255));

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        dWidth = size.x;
        dHeight = size.y;

        random = new Random();
        ballX = random.nextInt(dWidth - 50);
        ballY = dHeight/3;

        paddleX = dWidth/2 - paddle.getWidth()/2;
        paddleY = (dHeight * 4)/5;

        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();

        createBricks();
    }

    private void createBricks() {

        int brickWidth = dWidth / 8;
        int brickHeight = dHeight / 16;

        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 4; row++) {
                bricks[numBricks] = new Brick(row, column,brickWidth, brickHeight);
                numBricks++;
            }
        }
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        ballX += velocity.getX();
        ballY += velocity.getY();

        if ((ballX >= dWidth - ball.getWidth()) || ballX <= 0) {
            velocity.setX(velocity.getX() * -1);
        }
        if (ballY <= 0) {
            velocity.setY(velocity.getY() * -1);
        }
        if (ballY > paddleY + paddle.getHeight()) {
            ballX = 1 + random.nextInt(dWidth - ball.getWidth() - 1);
            ballY = dHeight / 3;
            if (mpMiss != null) {
                mpMiss.start();
            }
            velocity.setX(xVelocity());
            velocity.setY(32);
            life--;
            if (life == 0) {
                gameOver = true;
                launchGameOver();
            }
        }
        if (((ballX + ball.getWidth()) >= paddleX) && (ballX <= paddleX + paddle.getWidth())
                && (ballY + ball.getHeight() >= paddleY) && (ballY + ball.getHeight() <= paddleY +
                paddle.getHeight())) {
            if (mpHit != null) {
                mpHit.start();
            }
            velocity.setX(velocity.getX() + 1);
            velocity.setY((velocity.getY() + 1) * -1);
        }
        canvas.drawBitmap(ball, ballX, ballY, null);
        canvas.drawBitmap(paddle, paddleX, paddleY, null);

        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                canvas.drawRect(bricks[i].column * bricks[i].width + 1, bricks[i].row *
                        bricks[i].height + 1, bricks[i].column * bricks[i].width +
                        bricks[i].width - 1, bricks[i].row * bricks[i].height +
                        bricks[i].height - 1, brickPaint);
            }
        }
        canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
        if (life == 2) {
            healthPaint.setColor(Color.YELLOW);
        } else if (life == 1) {
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 + life, 80, healthPaint);
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (ballX + ballWidth >= bricks[i].column * bricks[i].width
                        && ballX <= bricks[i].column * bricks[i].width + bricks[i].width
                        && ballY <= bricks[i].row * bricks[i].height + bricks[i].height
                        && ballY >= bricks[i].row * bricks[i].height) {

                    velocity.setY((velocity.getY() + 1) * -1);
                    bricks[i].setInvisible();
                    points += 10;
                    brokenBricks++;

                    if (brokenBricks == 32) {
                        launchGameOver();
                    }
                }
            }
        }


        if (brokenBricks == numBricks) {
            gameOver = true;
        }
        if (!gameOver) {
            handler.postDelayed(runnable, UPDATE_MILLS);
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        if (touchY >= paddleY) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newPaddleX = oldPaddleX - shift;
                if (newPaddleX <= 0)
                    paddleX = 0;
                else if (newPaddleX >= dWidth - paddle.getWidth())
                    paddleX = dWidth - paddle.getWidth();
                else
                    paddleX = newPaddleX;
            }
        }
        return true;
    }

    public boolean isIntersecting(float x, float y) {
        ImageButton playBtn = ((Activity) context).findViewById(R.id.playBtn);

        int[] location = new int[2];
        playBtn.getLocationOnScreen(location);

        int btnX = location[0];
        int btnY = location[1];
        int btnWidth = playBtn.getWidth();
        int btnHeight = playBtn.getHeight();

        return x >= btnX && x <= btnX + btnWidth && y >= btnY && y <= btnY + btnHeight;
    }

    private void launchGameOver() {

        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("Points", points);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private int xVelocity() {

        int[] values = {-50, -30, -20, -15, 5, 5, 15, 20, 30, 50};
        int index = random.nextInt(10);
        return values[index];
    }
}
