package com.hannanyildiz.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;

    TextView scoreText;
    TextView timeText;

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;

    ImageView[] imageArray;//image view den bir image dizisi olusturdum.


    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //yukarida tanimladik burada baslatiyoruz(initialize) textView lari
        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        //yukaridaki resimleri imageView dizime attim
        imageArray = new ImageView[] {imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};
        hideImages();

        score = 0;

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {

                timeText.setText("Time: "+l/1000);

            }

            @Override
            public void onFinish() {

                timeText.setText("Time is over!");
                handler.removeCallbacks(runnable);//zaman bitince runnable dursun dedik

                //zaman bittiginde kenny kaybolsun dedik
                for(ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);

                    //simdi alertDialog ile uyari mesaji gosterecez oyunu tekrar oynamak istermisiniz diye
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                    alert.setTitle("Restart");//alertin basligi
                    alert.setMessage("Do you want to play again?");//alertte yazacak mesaj

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //yese basarsa oyun tekrar baslayacak
                            //asagidaki methosla kendi activity mizi tekrar cagiracaz. oncesinde guncel activityi destroy edecez yorulmamasi icin
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this,"Game Over!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.show();

                }

            }
        }.start();//sonda yazdigimiz star() geri sayimi baslat manasinda

    }

          public void increaseScore(View view){
           score++;//kenny resmlerine tiklandikca score 1er 1er artacak
           scoreText.setText("Score: "+ score);

          }

          public void hideImages(){

        //her saniye bir resmi gorunmez yapacaz o yuzden runnable kullaniyoruz
        handler =new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i =random.nextInt(9);//o ile 8 arasi rast gele sayi getirecek. 9 tane resmim oldugu icin 9 yazdik
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,500);//this yerine runabble da diyebiliriz, yarim saniyede bir random bir resim gorunur olacak

            }
        };

        handler.post(runnable);



    }

}