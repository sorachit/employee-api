package th.co.cdgs.auditlog;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class AuditLog {
    @Id
    private Date txDate;

    private String action;
    private String data;

    @PrePersist
    protected void onCreate() {
        this.txDate = new Date(System.currentTimeMillis());
    }

    public AuditLog() {
    }

    public AuditLog(String action , String data) {
        this.action = action;
        this.data = data;
    }

    public Date getTxDate() {
        return txDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
