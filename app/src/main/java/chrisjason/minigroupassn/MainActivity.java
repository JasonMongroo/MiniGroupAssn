package chrisjason.minigroupassn;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity {
    Button sendButton;
    EditText mPhoneNo;
    EditText mSMSText;
    Button quizButton;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 96
        sendButton = (Button) findViewById(R.id.sendButton);
        mPhoneNo = (EditText) findViewById(R.id.mPhoneNo);
        mSMSText = (EditText) findViewById(R.id.mSMSText);

        //get quiz button
        quizButton= (Button) findViewById(R.id.quizbutton);

        //set quiz on click listener
        quizButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i= new Intent(getApplicationContext(), Quiz.class);
                startActivityForResult(i, 1);
            }
        });


        SmsReceiver SMSr = new SmsReceiver();
        sendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String phoneNo = mPhoneNo.getText().toString();
                String sms = mSMSText.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Message Successfully Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS Send Failed, error occurred!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                int score = data.getExtras().getInt("score");
                String message = "I got a score of " + String.valueOf(score) +
                        "/5 in a quiz based on Android Studio! See if you can ";
                if (score == 5) {
                    message += "match my score!";
                } else {
                    message += "do better!";
                }
                mSMSText.setText(message);
                Toast.makeText(this, "Thanks for completing the quiz! Send a text to a friend to challenge them.", Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Quiz cancelled.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
