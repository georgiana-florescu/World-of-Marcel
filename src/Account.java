import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class Account {
    Information information;
    LinkedList <Character> characters;
    int maps_completed;

    public Account() {
        characters = new LinkedList<>();
    }

    public boolean verifyEmailAndPassword(String email, String password) {
        if (!email.equals(information.getCredentials().getEmail())) {
            return false;
        }
        if (!password.equals(information.getCredentials().getPassword())) {
            return false;
        }
        return true;
    }

    public void setInformation(String name, String email, String password, String country, TreeSet<String> fav_games) {
        information = new Information.InformationBuilder()
                .name(name)
                .country(country)
                .favorite_games(fav_games)
                .credentials(email, password)
                .build();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void setMapsCompleted(int maps_completed) {
        this.maps_completed = maps_completed;
    }

    static class Information {
        private final String name;
        private final String country;
        private final Credentials credentials;
        private final TreeSet<String> favorite_games;

        private Information (InformationBuilder builder) {
            this.name = builder.name;
            this.country = builder.country;
            this.credentials = builder.credentials;
            this.favorite_games = builder.favorite_games;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public TreeSet<String> getFavorite_games() {
            return favorite_games;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public static class InformationBuilder {
            private String name = null;
            private String country;
            private Credentials credentials = null;
            private TreeSet<String> favorite_games;

            public InformationBuilder name(String name) {
                this.name = name;
                return this;
            }

            public InformationBuilder credentials(String email, String password) {
                this.credentials = new Credentials(email, password);
                return this;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public InformationBuilder favorite_games(TreeSet<String> favorite_games) {
                this.favorite_games = favorite_games;
                return this;
            }

            public Information build() {
                Information info = new Information(this);
                return info;
            }

            public void validateInformation() throws InformationIncompleteException{
                if (name == null) {
                    throw new InformationIncompleteException("Name field is empty");
                }
                if (credentials == null) {
                    throw new InformationIncompleteException("Email and password fields are empty");
                }
            }

        }

    }
}
