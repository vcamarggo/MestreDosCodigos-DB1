package br.com.poc.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

import br.com.poc.otp.otp.GenerateOTPActivity;

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("messageBody", intent.getAction());
        if (Objects.equals(intent.getAction(), Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            try {
                String messageBody = "";
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    messageBody = smsMessage.getMessageBody();
                }
                Intent messageReceived = new Intent(context, GenerateOTPActivity.class);
                messageReceived.putExtra("sms", messageBody);
                context.sendBroadcast(messageReceived); // when receiving it somewhere in your app, subString the additional text and leave only the code, then place it in the editText and do your verification
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}