package com.developers.recycletryout.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developers.recycletryout.DisplayMovies;
import com.developers.recycletryout.R;
import com.developers.recycletryout.model.Beans_getcities;
import com.developers.recycletryout.model.Sathiya_Book;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by OMOSEFE NOEL OBASEKI on 08/04/2017.
 */
public class MyRecyclerAdapterone extends RecyclerView.Adapter<MyRecyclerAdapterone.MyViewHolder> {

    private List<Beans_getcities> feedsList;
    final private ListItemClickListener mOnClickListener;
    private Context context;
    private LayoutInflater inflater;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MyRecyclerAdapterone(Context context, List<Beans_getcities> feedsList, ListItemClickListener onClickListener) {

        this.context = context;
        this.feedsList = feedsList;
        mOnClickListener = onClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.multipleitem_recycle, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Beans_getcities feeds = feedsList.get(position);
        //Pass the values of feeds object to Views
        holder.name.setText(feeds.getName());
        holder.stat.setText(feeds.getState());
        holder.latitude.setText(feeds.getLatitude());
        holder.logitude.setText(feeds.getLongitude());
        holder.logitude.setText(feeds.getLongitude());
        holder.Lastupdate.setText(feeds.getLastUpdate());
        holder.Dates.setText(feeds.getCreatedDate());
        holder.Enabel.setText(feeds.getEnabled());


    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    //public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, stat, latitude, Lastupdate, logitude, Dates ,Enabel;
        HtmlTextView htmlTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            stat = (TextView) itemView.findViewById(R.id.stat);
            latitude = (TextView) itemView.findViewById(R.id.latitude);
            logitude = (TextView) itemView.findViewById(R.id.logitude);
            Enabel = (TextView) itemView.findViewById(R.id.Enabel);
            Lastupdate = (TextView) itemView.findViewById(R.id.Lastupdate);
            Dates = (TextView) itemView.findViewById(R.id.Dates);

        }

      /*  @Override
        public void onClick(View v) {
            Beans_getcities m = feedsList.get(getAdapterPosition());
            Intent intent = new Intent(context, DisplayMovies.class);
            intent.putExtra("title", m.getName());
            intent.putExtra("name", m.getState());
            intent.putExtra("tips", m.getLatitude());
            intent.putExtra("tips", m.getLongitude());
            intent.putExtra("tips", m.getEnabled());
            intent.putExtra("tips", m.getLastUpdate());
            intent.putExtra("tips", m.getCreatedDate());
            context.startActivity(intent);
        }*/
    }

}