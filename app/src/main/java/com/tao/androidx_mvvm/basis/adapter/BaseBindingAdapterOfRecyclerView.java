package com.tao.androidx_mvvm.basis.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.tao.androidx_mvvm.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev02 on 2018/3/9.
 */

public abstract class BaseBindingAdapterOfRecyclerView<M, B extends ViewDataBinding> extends RecyclerView.Adapter {
    protected Context context;
    protected ObservableArrayList<M> items;
    protected ArrayList<M> data;
    protected ListChangedCallback itemsChangeCallback;
    protected Handler handler;

    public BaseBindingAdapterOfRecyclerView(Context context, ObservableArrayList<M> items) {
        this.context = context;
        this.items = items;
        data = new ArrayList<>();
        data.addAll(items);
        this.itemsChangeCallback = new ListChangedCallback();
        handler = new Handler(Looper.getMainLooper());
    }

    public ArrayList<M> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), this.getLayoutResId(viewType), parent,
                false);
        afterCreatingViewDataBinding(parent,binding,viewType);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        if (position<data.size()){
            this.onBindItem(binding, this.data.get(position),position);
        }else {
            this.onBindItem(binding,null,position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.items.addOnListChangedCallback(itemsChangeCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.items.removeOnListChangedCallback(itemsChangeCallback);
    }

    //处理数据集变化
    protected void onChanged(final ObservableArrayList<M> newItems) {
        if (SystemUtil.isMainThread()){
            int oldCount = data.size();
            data.clear();
            data.addAll(newItems);
            int currentCount = data.size();
            if (oldCount == currentCount){
                notifyItemRangeChanged(0,oldCount);
            }else if (oldCount<currentCount){
                notifyItemRangeChanged(0,oldCount);
                notifyItemRangeInserted(oldCount,currentCount-oldCount);
            }else {
                notifyItemRangeChanged(0,currentCount);
                notifyItemRangeRemoved(currentCount,oldCount-currentCount);
            }
        }else {
            handler.post(() -> {
                int oldCount = data.size();
                data.clear();
                data.addAll(newItems);
                int currentCount = data.size();
                if (oldCount == currentCount){
                    notifyItemRangeChanged(0,oldCount);
                }else if (oldCount<currentCount){
                    notifyItemRangeChanged(0,oldCount);
                    notifyItemRangeInserted(oldCount,currentCount-oldCount);
                }else {
                    notifyItemRangeChanged(0,currentCount);
                    notifyItemRangeRemoved(currentCount,oldCount-currentCount);
                }
            });
        }
    }

    protected void onItemRangeChanged(final ObservableArrayList<M> newItems, final int positionStart, final int itemCount) {
        if (SystemUtil.isMainThread()){
            for (int i = positionStart; i < positionStart+itemCount; i++) {
                data.set(i,newItems.get(i));
            }
            notifyItemRangeChanged(positionStart, itemCount);
        }else {
            handler.post(() -> {
                for (int i = positionStart; i < positionStart+itemCount; i++) {
                    data.set(i,newItems.get(i));
                }
                notifyItemRangeChanged(positionStart, itemCount);
            });
        }
    }

    protected void onItemRangeInserted(final ObservableArrayList<M> newItems, final int positionStart, final int itemCount) {
        if (SystemUtil.isMainThread()){
            for (int i = positionStart; i < positionStart+itemCount; i++) {
                data.add(i,newItems.get(i));
            }
            //更改数据后需要及时更新
            notifyItemRangeInserted(positionStart, itemCount);
        }else {
            handler.post(() -> {
                for (int i = positionStart; i < positionStart+itemCount; i++) {
                    data.add(i,newItems.get(i));
                }
                //更改数据后需要及时更新
                notifyItemRangeInserted(positionStart, itemCount);
            });
        }
    }

    protected void onItemRangeMoved(final ObservableArrayList<M> newItems,final int start,final int to,int count) {
        if (SystemUtil.isMainThread()){
            for (int i = 0; i < count; i++) {
                M e = data.get(start);
                data.remove(start);
                data.add(to+i,e);
                notifyItemMoved(start+i,to+i);
            }
        }else {
            handler.post(() -> {
                for (int i = 0; i < count; i++) {
                    M e = data.get(start);
                    data.remove(start);
                    data.add(to+i,e);
                    notifyItemMoved(start+i,to+i);
                }
            });
        }
    }

    protected void onItemRangeRemoved(final ObservableArrayList<M> newItems, final int positionStart, final int itemCount) {
        if (SystemUtil.isMainThread()){
            for (int i = positionStart+itemCount-1; i >= positionStart; i--) {
                data.remove(i);
            }
            notifyItemRangeRemoved(positionStart, itemCount);
        }else {
            handler.post(() -> {
                for (int i = positionStart+itemCount-1; i >= positionStart; i--) {
                    data.remove(i);
                }
                notifyItemRangeRemoved(positionStart, itemCount);
            });
        }
    }

    public void resetItems(ObservableArrayList<M> newItems) {
        this.items.removeOnListChangedCallback(itemsChangeCallback);
        this.items = newItems;
        this.items.addOnListChangedCallback(itemsChangeCallback);
        onChanged(newItems);
    }

    public static <T> void resetList(List<T> src, List<T> dest){
        for (int i = 0; i < src.size(); i++){
            if (i < dest.size()){
                dest.set(i,src.get(i));
            } else {
                dest.add(src.get(i));
            }
        }
        if (dest.size() > src.size()){
            for (int i = dest.size() - 1; i >= src.size(); i--) {
                dest.remove(i);
            }
        }
    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, M item, int position);

    protected void afterCreatingViewDataBinding(@NonNull ViewGroup parent, B binding, int viewType){}

    class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {
        @Override
        public void onChanged(ObservableArrayList<M> newItems) {
            BaseBindingAdapterOfRecyclerView.this.onChanged(newItems);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingAdapterOfRecyclerView.this.onItemRangeChanged(newItems, i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingAdapterOfRecyclerView.this.onItemRangeInserted(newItems, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> newItems, int start, int to, int count) {
            BaseBindingAdapterOfRecyclerView.this.onItemRangeMoved(newItems,start,to,count);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            BaseBindingAdapterOfRecyclerView.this.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }

    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
