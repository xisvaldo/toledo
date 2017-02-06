package br.com.ite.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo.borges on 30/01/2017.
 */
public class News implements Serializable {

    @SerializedName("ID")
    private String id;

    @SerializedName("Titulo")
    private String title;

    @SerializedName("Data")
    private String creationDate;

    @SerializedName("Texto")
    private String description;

    @SerializedName("Imagem")
    private String imageURL;

    @SerializedName("Data_Modificado")
    private String modifiedDate;

    @Expose(serialize = false, deserialize = false)
    private Bitmap image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
