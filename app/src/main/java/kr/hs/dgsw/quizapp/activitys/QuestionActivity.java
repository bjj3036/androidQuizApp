package kr.hs.dgsw.quizapp.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static kr.hs.dgsw.quizapp.model.QuestionBean.CHOICES_SIZE;

import kr.hs.dgsw.quizapp.R;
import kr.hs.dgsw.quizapp.model.QuestionBean;
import kr.hs.dgsw.quizapp.model.QuestionDBHelper;

public class QuestionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    public static final int PHOTO_PICKER_REQ1 = 0;
    public static final int PHOTO_PICKER_REQ2 = 1;
    public static final int PHOTO_PICKER_REQ3 = 2;
    public static final int PHOTO_PICKER_REQ4 = 3;

    private QuestionDBHelper dbHelper;

    private EditText editTextQuestion, editTextScore;
    private RadioGroup radioGroupText;
    private LinearLayout textQuestionLayout, imageQuestionLayout;
    private CompoundButton typeButton;
    private ImageView[] imageViews;
    private EditText[] textViews;
    private RadioButton[] radios;

    private int answer;
    private int questionType;
    private String[] choices;

    private QuestionBean updateTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        dbHelper = new QuestionDBHelper(this, "quizDB", null, 1);


        editTextQuestion = findViewById(R.id.edit_question);
        editTextScore = findViewById(R.id.edit_score);
        textQuestionLayout = findViewById(R.id.textQuestionLayout);
        imageQuestionLayout = findViewById(R.id.imageQuestionLayout);
        radioGroupText = findViewById(R.id.radiogroup_text);
        typeButton = findViewById(R.id.toggle_type);
        typeButton.setOnCheckedChangeListener(this);
        findViewById(R.id.button_save).setOnClickListener(this);
        choices = new String[4];

        textViews = new EditText[]{
                findViewById(R.id.text_1),
                findViewById(R.id.text_2),
                findViewById(R.id.text_3),
                findViewById(R.id.text_4),
        };

        imageViews = new ImageView[]{
                findViewById(R.id.image_1),
                findViewById(R.id.image_2),
                findViewById(R.id.image_3),
                findViewById(R.id.image_4),
        };

        radios = new RadioButton[]{
                findViewById(R.id.radio_1),
                findViewById(R.id.radio_2),
                findViewById(R.id.radio_3),
                findViewById(R.id.radio_4),
        };
        if (getIntent().hasExtra("id")) {
            updateTarget = dbHelper.select(getIntent().getIntExtra("id", 1));
            setViewWithBean(updateTarget);
        } else
            //기본 문자 문제
            this.questionType = QuestionBean.TYPE_TEXT;
    }

    private void setViewWithBean(QuestionBean bean) {
        this.questionType = bean.getType();
        this.editTextQuestion.setText(bean.getQuestion());
        this.editTextScore.setText("" + bean.getScore());
        this.answer = bean.getAnswer();
        String[] choices = bean.getChoices();
        for (int i = 0; i < choices.length; i++) {
            if (bean.getType() == QuestionBean.TYPE_IMAGE) {
                imageViews[i].setImageURI(Uri.parse(choices[i]));
            } else {
                textViews[i].setText(choices[i]);
            }
        }
        radios[bean.getAnswer() - 1].setChecked(true);
        typeButton.setChecked(bean.getType() == QuestionBean.TYPE_IMAGE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int text, image;
        if (isChecked) {
            this.questionType = QuestionBean.TYPE_IMAGE;
            text = View.GONE;
            image = View.VISIBLE;
        } else {
            this.questionType = QuestionBean.TYPE_TEXT;
            text = View.VISIBLE;
            image = View.GONE;
        }
        textQuestionLayout.setVisibility(text);
        imageQuestionLayout.setVisibility(image);
    }

    public void onRadioClick1(View v) {
        this.answer = 1;
    }

    public void onRadioClick2(View v) {
        this.answer = 2;
    }

    public void onRadioClick3(View v) {
        this.answer = 3;
    }

    public void onRadioClick4(View v) {
        this.answer = 4;
    }

    //Save 버튼
    @Override
    public void onClick(View v) {
        if (!checkForm())
            return;
        QuestionBean questionBean = new QuestionBean();
        questionBean.setAnswer(this.answer);
        questionBean.setQuestion(editTextQuestion.getText().toString());
        questionBean.setScore(Integer.parseInt(editTextScore.getText().toString()));
        questionBean.setChoices(this.choices);
        dbHelper.insert(questionBean);
        finish();
    }

    public boolean checkForm() {
        if (editTextQuestion.getText().toString().isEmpty() || editTextScore.getText().toString().isEmpty() || answer == 0)
            return false;

        if (this.questionType == QuestionBean.TYPE_TEXT) {
            for (int i = 0; i < textViews.length; i++) {
                if (i >= CHOICES_SIZE)
                    break;
                choices[i] = textViews[i].getText().toString();
            }
        }

        for (String choice : choices) {
            if (choice.isEmpty())
                return false;
        }
        return true;
    }

    public void onImageClick(View v) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        int reqId;
        switch (v.getId()) {
            case R.id.image_1:
                reqId = PHOTO_PICKER_REQ1;
                break;
            case R.id.image_2:
                reqId = PHOTO_PICKER_REQ2;
                break;
            case R.id.image_3:
                reqId = PHOTO_PICKER_REQ3;
                break;
            case R.id.image_4:
                reqId = PHOTO_PICKER_REQ4;
                break;
            default:
                return;
        }
        startActivityForResult(i, reqId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String uriString = uri.toString();
            Log.d("QuizApp", uriString);
            choices[requestCode] = uriString;
            imageViews[requestCode].setImageURI(uri);
        }
    }
}
