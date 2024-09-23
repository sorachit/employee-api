package th.co.cdgs.auditlog;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @Column(name = "tx_date")
    private Date txDate;
    @Column(name = "action")
    private String action;
    @Column(name = "data")
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
