package chrisjason.minigroupassn;

import android.app.Activity;
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
                startActivity(i);
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

}
