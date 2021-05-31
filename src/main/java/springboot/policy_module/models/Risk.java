package springboot.policy_module.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Boolean status;
    private String keyWords;
    private String regex;
    private String description;
    private Integer riskMatchCount;
    private String username;

    public Risk() {

    }

    public Risk(String title, Boolean status, String keyWords, String regex, String description, Integer riskMatchCount) {
        this.title = title;
        this.status = status;
        this.keyWords = keyWords;
        this.regex = regex;
        this.description = description;
        this.riskMatchCount = riskMatchCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRiskMatchCount() {
        return riskMatchCount;
    }

    public void setRiskMatchCount(Integer riskMatchCount) {
        this.riskMatchCount = riskMatchCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Risk)) return false;

        Risk risk = (Risk) o;

        return getId() != null ? getId().equals(risk.getId()) : risk.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Risk{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", keyWords='" + keyWords + '\'' +
                ", regex='" + regex + '\'' +
                ", description='" + description + '\'' +
                ", riskMatchCount=" + riskMatchCount +
                ", username='" + username + '\'' +
                '}';
    }
}

