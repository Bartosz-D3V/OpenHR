package org.openhr.domain.process;

import java.io.Serializable;

public class TaskDefinition implements Serializable {
  private String id;
  private String name;
  private String processInstanceId;
  private String subjectAndApplicationId;

  public TaskDefinition() {
    super();
  }

  public TaskDefinition(final String id, final String name, final String processInstanceId) {
    this.id = id;
    this.name = name;
    this.processInstanceId = processInstanceId;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(final String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }
}
