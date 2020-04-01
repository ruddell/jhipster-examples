package tv.sportsbooth.replicator.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link tv.sportsbooth.replicator.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private Long id;

    private String name;


    private Long managerId;

    private String managerName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long employeeId) {
        this.managerId = employeeId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String employeeName) {
        this.managerName = employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", managerId=" + getManagerId() +
            ", managerName='" + getManagerName() + "'" +
            "}";
    }
}
