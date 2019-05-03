package kr.hs.dgsw.quizapp.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.hs.dgsw.quizapp.R;

public class MainActivity extends AppCompatActivity {

    public static final int EASY_MODE = 1;
    public static final int HARD_MODE = 2;

    private int selected_mode = 0;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }

    public void OnSettingClick(View v) {
        Intent i = new Intent(this, QuestionListActivity.class);
        startActivity(i);
    }

    public void onModeClick(View v) {
        if (v.getId() == R.id.radio_easy) {
            textView.setText(getString(R.string.mode_easyselect));
            selected_mode = EASY_MODE;
        } else if (v.getId() == R.id.radio_hard) {
            textView.setText(getString(R.string.mode_hardselect));
            selected_mode = HARD_MODE;
        }
    }
}
