package chrisjason.minigroupassn;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends Activity {

    private static final int NUM_QUESTIONS= 5;
    private static final int NUM_OPTIONS= 4;
    private Button submit;
    private RadioGroup[] rg;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        home= (Button)findViewById(R.id.backToHome);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        rg= new RadioGroup[NUM_QUESTIONS];

        //set radio groups into array
        rg[0]= (RadioGroup) findViewById(R.id.q1_grp);
        rg[1]= (RadioGroup) findViewById(R.id.q2_grp);
        rg[2]= (RadioGroup) findViewById(R.id.q3_grp);
        rg[3]= (RadioGroup) findViewById(R.id.q4_grp);
        rg[4]= (RadioGroup) findViewById(R.id.q5_grp);

        setQuestionTexts();

        submit= (Button) findViewById(R.id.submit_quiz);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MYAPP", "submitted quiz");
                //save scores to file
                int score= getScore();
                Toast.makeText(getApplicationContext(), "Score:" + score, Toast.LENGTH_SHORT).show();
                Intent i= new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("score", score);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    public void setQuestionTexts(){
        //question
        TextView q_text;
        //to hold array of all the texts
        String[] ques;
        //to hold ids of all arrays
        int[] ids= new int[NUM_QUESTIONS];
        //to hold ids of all TextView components
        int[] q_textids= new int[NUM_QUESTIONS];


        ids[0]= R.array.q1;
        ids[1]= R.array.q2;
        ids[2]= R.array.q3;
        ids[3]= R.array.q4;
        ids[4]= R.array.q5;

        q_textids[0]= R.id.q1_text;
        q_textids[1]= R.id.q2_text;
        q_textids[2]= R.id.q3_text;
        q_textids[3]= R.id.q4_text;
        q_textids[4]= R.id.q5_text;

        for(int x=0; x<NUM_QUESTIONS; x++){
            ques= getResources().getStringArray(ids[x]);
            q_text= findViewById(q_textids[x]);
            //set question text
            q_text.setText(ques[0]);

            //set answer text
            for(int y=0; y<NUM_OPTIONS; y++){
                RadioButton rb= (RadioButton)rg[x].getChildAt(y);
                //set text of each of the 4 answers
                rb.setText(ques[y+1]);
            }
        }
    }

    public int getScore(){
        RadioButton[] answers= new RadioButton[NUM_QUESTIONS];
        answers[0]= (RadioButton)rg[0].getChildAt(1);
        answers[1]= (RadioButton)rg[1].getChildAt(3);
        answers[2]= (RadioButton)rg[2].getChildAt(2);
        answers[3]= (RadioButton)rg[3].getChildAt(0);
        answers[4]= (RadioButton)rg[4].getChildAt(1);


        //get score
        int score=0;

        for(int x=0; x<NUM_QUESTIONS; x++){
            if(findViewById(rg[x].getCheckedRadioButtonId())== answers[x])
                score++;
        }
        return score;
    }

}
