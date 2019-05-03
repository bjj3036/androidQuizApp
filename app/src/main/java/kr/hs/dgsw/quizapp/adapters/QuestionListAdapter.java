package kr.hs.dgsw.quizapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.quizapp.R;
import kr.hs.dgsw.quizapp.model.QuestionBean;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionVH> {

    private List<QuestionBean> questions;
    private QuestionItemClickListener questionItemClickListener;

    public QuestionListAdapter(QuestionItemClickListener qicl) {
        questions = new ArrayList<>();
        this.questionItemClickListener = qicl;
    }

    @NonNull
    @Override
    public QuestionVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        QuestionVH vh = new QuestionVH(inflater.inflate(R.layout.item_question, viewGroup, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionVH vh, int i) {
        QuestionBean bean = questions.get(i);
        if (bean.getType() == QuestionBean.TYPE_IMAGE) {
            vh.image_icon.setImageResource(R.drawable.ic_baseline_photo_24px);
        } else {
            vh.image_icon.setImageResource(R.drawable.ic_baseline_text_format_24px);
        }
        vh.text_title.setText(bean.getQuestion());
        vh.itemView.setOnClickListener(v -> {
            questionItemClickListener.onQuestionItemClick(bean.getId());
        });
    }

    @Override
    public int getItemCount() {
        if (questions == null)
            return 0;
        return questions.size();
    }

    public void addItem(QuestionBean questionBean) {
        this.questions.add(questionBean);
        notifyDataSetChanged();
    }

    public void addItem(List<QuestionBean> questionBeans) {
        this.questions.addAll(questionBeans);
        notifyDataSetChanged();
    }

    public void clearItem(){
        this.questions.clear();
        notifyDataSetChanged();
    }

    class QuestionVH extends RecyclerView.ViewHolder {

        ImageView image_icon;
        TextView text_title;

        public QuestionVH(@NonNull View itemView) {
            super(itemView);
            image_icon = itemView.findViewById(R.id.imageView);
            text_title = itemView.findViewById(R.id.text_title);
        }
    }

    public interface QuestionItemClickListener {
        public void onQuestionItemClick(int id);
    }
}
