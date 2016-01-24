package com.example.fondson.findmev2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.clarifai.api.Tag;
import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.exception.ClarifaiException;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static String APP_ID = "Ri9CPCidIOPze-ZX2IHF7vd1zofLSFgTEm3xqvOH";
    public static String APP_SECRET="Vu6rq2sNPv0kQKbGFq82-oeg_U8TF3sHLFKE5318";
    final Random rand = new Random();
    private final ClarifaiClient client = new ClarifaiClient(APP_ID, APP_SECRET);
    private String tags="Tags: ";
    private int randNum;
    private ImageButton[] imageButtons;
    private TextView tv;
    private Button btn;
    private String[] LINKS =new String[]{"http://www.planwallpaper.com/static/images/colorful-triangles-background_yB0qTG6.jpg",
            "http://landscapingemag.com/wp-content/uploads/2012/11/Highline.jpg",
            "http://www.publishedart.com.au/image/big_urbangreen3133_1.jpg?w=336&h=320",
            "http://travelercorner.com/wp-content/uploads/2016/01/tropical-island-beauty.jpg",
            "http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/08/Urban-Landscape-Sunset-Wallpapers-1920x1080.jpg",
            "http://www.mrwallpaper.com/wallpapers/nevada-desert.jpg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButtons=new ImageButton[]{(ImageButton)findViewById(R.id.imageButton),
                (ImageButton)findViewById(R.id.imageButton2),
                (ImageButton)findViewById(R.id.imageButton3),
                (ImageButton)findViewById(R.id.imageButton4),
                (ImageButton)findViewById(R.id.imageButton5),
                (ImageButton)findViewById(R.id.imageButton6)};
        btn=(Button)findViewById(R.id.button);
        tv=(TextView)findViewById(R.id.textView);
        for (int i=0;i<imageButtons.length;i++) {
            imageButtons[i].setOnClickListener(onClickListener);
        }
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomize();
            }
        });
        try {
            randomize();
        } catch(ClarifaiException e) {
            Toast.makeText(this,"hi",Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i=0;i<imageButtons.length;i++){
                if(v==imageButtons[i]){
                    if(i==randNum){
                        Toast.makeText(MainActivity.this,"Correct!",Toast.LENGTH_LONG).show();
                        btn.setEnabled(true);
                    }
                    else{Toast.makeText(MainActivity.this,"Incorrect!",Toast.LENGTH_LONG).show();}
                }
            }
        }
    };
    private void randomize() {
        tags="Tags: "; btn.setEnabled(false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        randNum=rand.nextInt(6);
        List < RecognitionResult > results =
                client.recognize(
                        new RecognitionRequest(LINKS[randNum]));

        for (Tag tag : results.get(0).getTags()) {
            tags+=tag.getName() +" " ;
        }
        tv.setText(tags);
    }
}

