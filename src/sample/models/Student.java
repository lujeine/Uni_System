package sample.models;

public class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private String city;
    private String country;

    private Student(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String city;
        private String country;

        public Student build() {
            if (firstName == null) {
                throw new NullPointerException("first name is required");
            }
            if (lastName == null) {
                throw new NullPointerException("last name is required");
            }
            if (email == null) {
                throw new NullPointerException("email is required");
            }

            Student student = new Student(id, firstName, lastName, email);

            student.setCity(city);
            student.setCountry(country);

            return student;
        }
        public void setId(int id) {
            this.id = id;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
