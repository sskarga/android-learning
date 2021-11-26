package com.sskarga.listviewbaseadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sskarga.listviewbaseadapter.databinding.ItemListBinding;

import java.util.List;

interface ItemAdapterListener {
    View.OnClickListener onClickAdd(BaseAdapter ctx);
    View.OnClickListener onClickSubstract(BaseAdapter ctx);
}

class ItemAdapterListenerImp implements ItemAdapterListener {

    @Override
    public View.OnClickListener onClickAdd(BaseAdapter ctx) {
        return view -> {
            if (view.getTag() == null) return;

            ItemCount item = (ItemCount) view.getTag();
            item.setCount(item.getCount() + 1);

            ctx.notifyDataSetChanged();
        };
    }

    @Override
    public View.OnClickListener onClickSubstract(BaseAdapter ctx) {
        return view -> {
            if (view.getTag() == null) return;

            ItemCount item = (ItemCount) view.getTag();

            int count = item.getCount() - 1;
            if (count >= 0) item.setCount(count);

            ctx.notifyDataSetChanged();
        };
    }
}

public class ItemBaseAdapter extends BaseAdapter {

    private List<ItemCount> items;
    private ItemAdapterListener adapterListener;

    public ItemBaseAdapter(List<ItemCount> item, ItemAdapterListener adapterListener) {
        this.items = item;
        this.adapterListener = adapterListener;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemListBinding binding = (view == null) ?
                ItemListBinding.inflate(LayoutInflater.from(viewGroup.getContext())) : ItemListBinding.bind(view);

        ItemCount item = (ItemCount) this.getItem(i);

        binding.textViewTitle.setText(item.getTitle());
        binding.textViewCount.setText(String.valueOf(item.getCount()));

        binding.imageViewAdd.setTag(item);
        binding.imageViewAdd.setOnClickListener(adapterListener.onClickAdd(this));

        binding.imageViewSubstract.setTag(item);
        binding.imageViewSubstract.setOnClickListener(adapterListener.onClickSubstract(this));

        return binding.getRoot();
    }

}
