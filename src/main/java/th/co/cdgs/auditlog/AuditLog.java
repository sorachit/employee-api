package th.co.cdgs.auditlog;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
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
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
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
