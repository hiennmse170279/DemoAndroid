package com.example.demoandroidsqlserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterForBook extends RecyclerView.Adapter<RecyclerViewAdapterForBook.BookViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private Context context;
    private List<Book> listBooks;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterForBook(Context context, List<Book> listBooks) {
        if(context instanceof RecyclerViewAdapterForBook.OnItemClickListener){
            onItemClickListener = (OnItemClickListener) context;
        }
        this.context = context;
        this.listBooks = listBooks;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.tvName.setText(listBooks.get(position).getName());
        holder.tvDescription.setText(listBooks.get(position).getDescription());
        holder.tvPrice.setText("" + listBooks.get(position).getPrice());
        holder.tvIsSold.setText(String.valueOf(listBooks.get(position).isSold()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvIsSold;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvIsSold = itemView.findViewById(R.id.tvIsSold);
        }
    }
}
