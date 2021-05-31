package springboot.policy_module.models;

import javax.persistence.*;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;

    private String remedyType;
    private String remedyTime;

    private Boolean notifyUser;
    private Boolean notifyAdmin;
    private String adminEmail;
    private String adminEmailSubject;
    private String username;

    public Policy() {

    }

    public Policy(String policyName, String remedyType, String remedyTime, Boolean notifyUser, Boolean notifyAdmin, String adminEmail, String adminEmailSubject) {
        this.policyName = policyName;
        this.remedyType = remedyType;
        this.remedyTime = remedyTime;
        this.notifyUser = notifyUser;
        this.notifyAdmin = notifyAdmin;
        this.adminEmail = adminEmail;
        this.adminEmailSubject = adminEmailSubject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getRemedyType() {
        return remedyType;
    }

    public void setRemedyType(String remedyType) {
        this.remedyType = remedyType;
    }

    public String getRemedyTime() {
        return remedyTime;
    }

    public void setRemedyTime(String remedyTime) {
        this.remedyTime = remedyTime;
    }

    public Boolean getNotifyUser() {
        return notifyUser;
    }

    public void setNotifyUser(Boolean notifyUser) {
        this.notifyUser = notifyUser;
    }

    public Boolean getNotifyAdmin() {
        return notifyAdmin;
    }

    public void setNotifyAdmin(Boolean notifyAdmin) {
        this.notifyAdmin = notifyAdmin;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminEmailSubject() {
        return adminEmailSubject;
    }

    public void setAdminEmailSubject(String adminEmailSubject) {
        this.adminEmailSubject = adminEmailSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Policy policy = (Policy) o;

        return id != null ? id.equals(policy.id) : policy.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", policyName='" + policyName + '\'' +
                ", remedyType='" + remedyType + '\'' +
                ", remedyTime='" + remedyTime + '\'' +
                ", notifyUser=" + notifyUser +
                ", notifyAdmin=" + notifyAdmin +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminEmailSubject='" + adminEmailSubject + '\'' +
                ", createdBy=" + username +
                '}';
    }
}
