package com.example.momtobe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Question;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;

import java.util.List;

public class HomeQuestionApdater extends RecyclerView.Adapter<HomeQuestionApdater.CustomHoleder>{

    List<Question> questionList;
    HomeQuestionApdater.CustomClickListener listener;
    Context context ;

    public HomeQuestionApdater(List<Question> questionList) {
        this.questionList = questionList;
    }


    public HomeQuestionApdater(Context context,List<Question> questionList, HomeQuestionApdater.CustomClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeQuestionApdater.CustomHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.recycler_view_main_question ,parent , false);
        return new HomeQuestionApdater.CustomHoleder(listItemView, listener);
    }



    @Override
    public void onBindViewHolder(@NonNull HomeQuestionApdater.CustomHoleder holder, int position) {
        holder.questionTitle.setText(questionList.get(position).getTitle());
        holder.questionContent.setText(questionList.get(position).getDescription());
//        String url = questionList.get(position).getImage().toString();
//        Glide.with(context).load(url).into(holder.questionImage);

    }
    @Override
    public int getItemCount() {
        return questionList.size();
    }
    static class CustomHoleder extends RecyclerView.ViewHolder {

        TextView questionTitle ;
        TextView questionContent;
        ImageView questionImage ;

        HomeQuestionApdater.CustomClickListener listener ;
        public CustomHoleder(@NonNull View itemView , HomeQuestionApdater.CustomClickListener listener) {
            super(itemView);
            this.listener = listener;
            questionImage = itemView.findViewById(R.id.question_image);
            questionContent= itemView.findViewById(R.id.question_content);
            questionTitle= itemView.findViewById(R.id.question_title);
            itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
        }
    }
    public interface CustomClickListener{
        void onTaskItemClicked(int position);
    }

}
