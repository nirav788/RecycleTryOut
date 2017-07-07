package com.developers.recycletryout.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.recycletryout.DisplayMovies;
import com.developers.recycletryout.R;
import com.developers.recycletryout.model.Sathiya_Book;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 08/04/2017.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<Sathiya_Book> feedsList;
    final private ListItemClickListener mOnClickListener;
    private Context context;
    private LayoutInflater inflater;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MyRecyclerAdapter(Context context, List<Sathiya_Book> feedsList, ListItemClickListener onClickListener) {

        this.context = context;
        this.feedsList = feedsList;
        mOnClickListener = onClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.singleitem_recyclerview, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Sathiya_Book feeds = feedsList.get(position);
        //Pass the values of feeds object to Views
        holder.title.setText(feeds.getTitle());
        holder.LANGUAGE.setText(feeds.getName());
        holder.htmlTextView.setText(Html.fromHtml(feeds.getTips()));
        holder.htmlTextView.setMaxLines(4);

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView content, title, LANGUAGE;
        HtmlTextView htmlTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_view);
            LANGUAGE = (TextView) itemView.findViewById(R.id.LANGUAGE);
            // content = (TextView) itemView.findViewById(R.id.content_view);
            htmlTextView = (HtmlTextView) itemView.findViewById(R.id.html_text);

        }

        @Override
        public void onClick(View v) {
            Sathiya_Book m = feedsList.get(getAdapterPosition());
            Intent intent = new Intent(context, DisplayMovies.class);
            intent.putExtra("title", m.getTitle());
            intent.putExtra( "name",m.getName());
            intent.putExtra("tips", m.getTips());
            context.startActivity(intent);
        }
    }

}