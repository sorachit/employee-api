package th.co.cdgs.process;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "import_process")
public class ImportProcess {    
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "status")
    private ProcessStatus status;

    ImportProcess() {}

    public ImportProcess(String username, ProcessStatus status) {
        this.username = username;
        this.status = status;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }



    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }

}
