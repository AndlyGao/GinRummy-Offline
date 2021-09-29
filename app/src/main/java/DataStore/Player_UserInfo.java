package DataStore;

public class Player_UserInfo {

    String UserId, Username, ProfilePictureString;
    int ProfilePicture;

	/*public Player_UserInfo(JSONObject UserInfo) {
        // TODO Auto-generated constructor stub
		try {
			setUserId(UserInfo.getString(Parameters._id));
			setUsername(UserInfo.getString(Parameters.User_Name));
			setProfilePicture(UserInfo.getString(Parameters.ProfilePicture));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(int profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getProfilePictureString() {
        return ProfilePictureString;
    }

    public void setProfilePictureString(String profilePictureString) {
        ProfilePictureString = profilePictureString;
    }
}
