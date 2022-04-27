package gui.client.requests;

public class AuthRequest implements GenericRequest {
    private String login;
    private String password;


    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getType() {
        return "auth";
    }
}
