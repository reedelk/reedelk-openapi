package de.codecentric.reedelk.openapi.v3.model;

import de.codecentric.reedelk.openapi.OpenApiModel;

import java.util.Objects;

public class InfoObject implements OpenApiModel {

    private String title;
    private String description;
    private String termsOfService;
    private String version;
    private ContactObject contact;
    private LicenseObject license;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    public ContactObject getContact() {
        return contact;
    }

    public void setContact(ContactObject contact) {
        this.contact = contact;
    }

    public LicenseObject getLicense() {
        return license;
    }

    public void setLicense(LicenseObject license) {
        this.license = license;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoObject that = (InfoObject) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(termsOfService, that.termsOfService) &&
                Objects.equals(version, that.version) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(license, that.license);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, termsOfService, version, contact, license);
    }

    @Override
    public String toString() {
        return "InfoObject{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", termsOfService='" + termsOfService + '\'' +
                ", version='" + version + '\'' +
                ", contact=" + contact +
                ", license=" + license +
                '}';
    }

    public enum Properties {

        TITLE("title"),
        DESCRIPTION("description"),
        TERMS_OF_SERVICE("termsOfService"),
        VERSION("version"),
        CONTACT("contact"),
        LICENSE("license");

        private final String value;

        Properties(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }
}
