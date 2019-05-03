package kr.hs.dgsw.quizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionBean implements Parcelable {
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;

    public static final int CHOICES_SIZE = 4;

    private int id;
    private String question;
    private int score;
    private int answer;
    private String[] choices;
    private int type;

    public static final Parcelable.Creator<QuestionBean> CREATOR
            = new Parcelable.Creator<QuestionBean>() {
        public QuestionBean createFromParcel(Parcel in) {
            return new QuestionBean(in);
        }

        public QuestionBean[] newArray(int size) {
            return new QuestionBean[size];
        }
    };

    public QuestionBean() {
        choices = new String[CHOICES_SIZE];
    }

    protected QuestionBean(Parcel in) {
        id = in.readInt();
        question = in.readString();
        score = in.readInt();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeInt(score);
        dest.writeInt(answer);
        dest.writeArray(choices);
        dest.writeInt(type);
    }
}
