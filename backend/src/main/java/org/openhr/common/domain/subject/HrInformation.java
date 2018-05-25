package org.openhr.common.domain.subject;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "HR_INFORMATION")
public class HrInformation implements Serializable {
  @Id
  @Column(name = "HR_INFORMATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long hrInformationId;

  @Column(name = "ALLOWANCE")
  @Max(value = 40)
  private long allowance;

  @Column(name = "USED_ALLOWANCE")
  private long usedAllowance;

  public HrInformation() {
    super();
  }

  public HrInformation(final long allowance) {
    this.allowance = allowance;
  }

  public long getHrInformationId() {
    return hrInformationId;
  }

  public void setHrInformationId(final long hrInformationId) {
    this.hrInformationId = hrInformationId;
  }

  public long getAllowance() {
    return allowance;
  }

  public void setAllowance(final long allowance) {
    this.allowance = allowance;
  }

  public long getUsedAllowance() {
    return usedAllowance;
  }

  public void setUsedAllowance(final long usedAllowance) {
    this.usedAllowance = usedAllowance;
  }
}
