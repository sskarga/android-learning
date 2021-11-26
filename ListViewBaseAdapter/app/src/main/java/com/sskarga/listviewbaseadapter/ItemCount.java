package com.sskarga.listviewbaseadapter;

public class ItemCount {
    private String title;
    private int count = 0;

    public ItemCount(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ItemCount{" +
                "title='" + title + '\'' +
                '}';
    }
}
