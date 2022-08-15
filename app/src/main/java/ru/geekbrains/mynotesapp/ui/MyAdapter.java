package ru.geekbrains.mynotesapp.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.mynotesapp.R;

import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final DataSource dataSource;
    private OnItemClickListener itemClickListener;

    public MyAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String titleOfNote = dataSource.getData(position).getTitleOfNote();
        if (titleOfNote.equals(""))
            titleOfNote = String.format(new Locale(Locale.getDefault().getLanguage()), "Note #%d", holder.getAdapterPosition());

        holder.getTitleView().setText(titleOfNote);

        holder.getDateView().setText((dataSource.getData(position).getCreateData().toString()));
    }

    @Override
    public int getItemCount() {
        return dataSource.getDataSize();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteItemByPosition(int position) {
        dataSource.deleteData(position);
        notifyDataSetChanged();
    }

    public void updateItemByPosition(int position) {
        notifyItemChanged(position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView dateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView;
            titleView = cardView.findViewById(R.id.title_view);
            dateView = cardView.findViewById(R.id.date_view);

            cardView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(view, getAdapterPosition());
                }

            });
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDateView() {
            return dateView;
        }

    }
}
