package apps.my.spotter.activities.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.UUID;

@IgnoreExtraProperties
public class Issue implements Serializable {

    public String issueId, type, county;
    public String street;
    public String city;
    public boolean mostImportantIssue;
    public int rating;
    public String info;

    public Issue() {}

    public Issue(String issueId, String type, String county, String street, String city, String info, int rating, boolean mostImportantIssue)
    {
        this.issueId = issueId;
        this.type = type;
        this.county = county;
        this.street = street;
        this.city = city;
        this.rating = rating;
        this.mostImportantIssue = mostImportantIssue;
        this.info = info;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isMostImportantIssue() {
        return mostImportantIssue;
    }

    public void setMostImportantIssue(boolean mostImportantIssue) {
        this.mostImportantIssue = mostImportantIssue;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "TYPE: "+type+". County: "+county+". "+ street + ", " + city + ", " + info
                + ", Issue Level:" + rating + ", Very Dangerous =" + mostImportantIssue;
    }
}


