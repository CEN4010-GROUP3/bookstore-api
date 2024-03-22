package edu.fiu.cen4010.group3.model;

public class Author {
    private String firstName;
    private String lastName;
    private String publisher;
    private String biography;

    public Author() {
    }

    public Author(String firstName, String lastName, String publisher, String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.publisher = publisher;
        this.biography = biography;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getBiography() {
        return biography;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Author [firstName=" + firstName + ", lastName=" + lastName + ", publisher=" + publisher + ", biography=" + biography  + "]";
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Author other = (Author) obj;
        if (biography == null) {
            if (other.biography != null)
                return false;
        } else if (!biography.equals(other.biography))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((biography == null) ? 0 : biography.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        return result;
    }
    
}
