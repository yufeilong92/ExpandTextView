package com.example.expandtextview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @Author : YFL  is Creating a porject in ExpandTextView
 * @Package com.example.expandtextview
 * @Email : yufeilong92@163.com
 * @Time :2019/12/5 14:19
 * @Purpose :
 */
public class Main2Adapter extends RecyclerView.Adapter<Main2Adapter.ViewHodler> {
    private Context mContext;
    private List<?> mListDatas;
    private LayoutInflater mInflater;

    public Main2Adapter(Context mContext, List<?> mListDatas) {
        this.mContext = mContext;
        this.mListDatas = mListDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置监听
     */
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onClickItem(View view,Object o);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodler(mInflater.inflate(R.layout.item_main2, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.mTvItemMainContent.setText(mListDatas.get(position).toString());
        holder.mTvItemMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (onItemClickListener!=null){
                   onItemClickListener.onClickItem(holder.mTvItemMainContent,mListDatas.get(position));
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListDatas.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public TextView mTvItemMainContent;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            this.mTvItemMainContent = (TextView) itemView.findViewById(R.id.tv_item_main_content);

        }
    }


}
