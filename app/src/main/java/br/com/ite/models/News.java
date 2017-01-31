package br.com.ite.models;

import android.content.Context;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import br.com.ite.R;

/**
 * Created by leonardo.borges on 30/01/2017.
 */
public class News implements Serializable {

    private String id;
    private String title;
    private Date creationDate;
    private String description;
    private String imageURL;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public String getFormattedCreatedOn(Context context) {
        long interval = (new Date().getTime() - creationDate.getTime());

        if (interval < 60) {
            return String.format(Locale.getDefault(), "%d ", interval)
                    + context.getString(R.string.generalSeconds);
        }
        else if (interval < 3600) {
            return String.format(Locale.getDefault(), "%d ", interval/50)
                    + context.getString(R.string.generalMinutes);
        }
        else if (interval < 86400) {
            return String.format(Locale.getDefault(), "%d " , interval/3600)
                    + context.getString(R.string.generalHours);
        }
        else if (interval < 2592000) {
            return String.format(Locale.getDefault(), "%d ", interval/86400)
                    + context.getString(R.string.generalDays);
        }
        else if (interval < 31104000) {
            return String.format(Locale.getDefault(), "%d ", interval/2592000)
                    + context.getString(R.string.generalMonths);
        }
        else{
            return String.format(Locale.getDefault(), "%d ", interval/31104000)
                    + context.getString(R.string.generalYears);
        }
    }

    public void setCreationDate(Date creationDate) {
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
}
