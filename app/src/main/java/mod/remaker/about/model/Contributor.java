package mod.remaker.about.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contributor implements Parcelable {
    @SerializedName("modder_username")
    @Expose
    public String name;

    @SerializedName("modder_description")
    @Expose
    public String description;

    @SerializedName("modder_img")
    @Expose
    public String avatarUrl;

    @SerializedName("isMainModder")
    @Expose
    public boolean isMainModder;

    @SerializedName("isActive")
    @Expose
    public boolean isActive;

    public Contributor(String name, String description, String avatarUrl, boolean isMainModder, boolean isActive) {
        this.name = name;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.isMainModder = isMainModder;
        this.isActive = isActive;
    }

    protected Contributor(Parcel in) {
        name = in.readString();
        description = in.readString();
        avatarUrl = in.readString();
        isMainModder = in.readBoolean();
        isActive = in.readBoolean();
    }

    public static final Creator<Contributor> CREATOR = new Creator<Contributor>() {
        @Override
        public Contributor createFromParcel(Parcel in) {
            return new Contributor(in);
        }

        @Override
        public Contributor[] newArray(int size) {
            return new Contributor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(avatarUrl);
        dest.writeBoolean(isMainModder);
        dest.writeBoolean(isActive);
    }
}
