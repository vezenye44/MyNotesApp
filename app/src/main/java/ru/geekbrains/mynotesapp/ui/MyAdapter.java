package ru.geekbrains.mynotesapp.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.geekbrains.mynotesapp.R;
import ru.geekbrains.mynotesapp.model.DataSource;

import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private DataSource dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;

    private int itemPosition;

    public MyAdapter(Fragment fragment) {
        this.fragment = fragment;
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

        holder.getDateView().setText((dataSource.getData(position).getDate().toString()));
    }

    @Override
    public int getItemCount() {
        return dataSource.getDataSize();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteItemByPosition(int position) {
        //dataSource.deleteData(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }

    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public void updateItemByPosition(int position) {
        //notifyItemChanged(position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getItemPosition() {
        return itemPosition;
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

            if (fragment != null) {
                fragment.registerForContextMenu(cardView);
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        itemPosition = getAdapterPosition();
                        cardView.showContextMenu();
                        //Toast.makeText(fragment.requireContext(), "context menu", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDateView() {
            return dateView;
        }

    }
}
