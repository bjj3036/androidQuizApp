package kr.hs.dgsw.quizapp.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import kr.hs.dgsw.quizapp.R;
import kr.hs.dgsw.quizapp.adapters.QuestionListAdapter;
import kr.hs.dgsw.quizapp.model.QuestionDBHelper;

public class QuestionListActivity extends AppCompatActivity implements QuestionListAdapter.QuestionItemClickListener {
    private EditText passwordEdit;
    private View loginLayout, listLayout;
    private RecyclerView recyclerView;
    private QuestionDBHelper dbHelper;
    private QuestionListAdapter questionListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent i = new Intent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionlist);
        loginLayout = findViewById(R.id.loginLayout);
        listLayout = findViewById(R.id.listLayout);
        passwordEdit = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recycler_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        dbHelper = new QuestionDBHelper(this, "quizDB", null, 1);

        questionListAdapter = new QuestionListAdapter(this);
        recyclerView.setAdapter(questionListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QuizApp", "Resume");
        questionListAdapter.clearItem();
        questionListAdapter.addItem(dbHelper.select());
    }

    public void onEnter(View v) {
        if ("1234".equals(passwordEdit.getText().toString())) {
            loginLayout.setVisibility(View.GONE);
            listLayout.setVisibility(View.VISIBLE);
        } else
            finish();
    }

    public void onAddClick(View v) {
        Intent i = new Intent(this, QuestionActivity.class);
        startActivity(i);
    }

    @Override
    public void onQuestionItemClick(int id) {
        Intent i = new Intent(this, QuestionActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}
