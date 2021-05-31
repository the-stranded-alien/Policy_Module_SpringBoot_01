package springboot.policy_module.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table()
public class PolicyRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long policyId;

    @NotNull
    private Long riskId;

    @NotNull
    private String username;

    public PolicyRisk() {

    }

    public PolicyRisk(@NotNull Long policyId, @NotNull Long riskId) {
        this.policyId = policyId;
        this.riskId = riskId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getRiskId() {
        return riskId;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolicyRisk that = (PolicyRisk) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PolicyRisk{" +
                "id=" + id +
                ", policyId=" + policyId +
                ", riskId=" + riskId +
                ", username='" + username + '\'' +
                '}';
    }
}
