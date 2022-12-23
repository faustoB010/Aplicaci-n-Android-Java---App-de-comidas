package com.example.myapplication;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    ImageView imageView;
    TextView textnombre;
    TextView textprecio;
    TextView textdescrip;

    public ViewHolder(ImageView imageView, TextView textnombre, TextView textprecio, TextView textdescrip) {
        this.imageView = imageView;
        this.textnombre = textnombre;
        this.textprecio = textprecio;
        this.textdescrip = textdescrip;
    }

    public ViewHolder() {
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextnombre() {
        return textnombre;
    }

    public void setTextnombre(TextView textnombre) {
        this.textnombre = textnombre;
    }

    public TextView getTextprecio() {
        return textprecio;
    }

    public void setTextprecio(TextView textprecio) {
        this.textprecio = textprecio;
    }

    public TextView getTextdescrip() {
        return textdescrip;
    }

    public void setTextdescrip(TextView textdescrip) {
        this.textdescrip = textdescrip;
    }
}
