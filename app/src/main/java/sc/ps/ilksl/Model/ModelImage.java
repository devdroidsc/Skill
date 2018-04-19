package sc.ps.ilksl.Model;

public class ModelImage {

    private String Name,IdImage,NameProfile;
    private boolean onSelece;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdImage() {
        return IdImage;
    }

    public void setIdImage(String idImage) {
        IdImage = idImage;
    }

    public String getNameProfile() {
        return NameProfile;
    }

    public void setNameProfile(String nameProfile) {
        NameProfile = nameProfile;
    }

    public boolean isOnSelece() {
        return onSelece;
    }

    public void setOnSelece(boolean onSelece) {
        this.onSelece = onSelece;
    }
}
