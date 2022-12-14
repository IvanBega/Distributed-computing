package jms.common;

import java.io.Serializable;
import java.time.LocalDate;

public class Worker implements Serializable {
    private int id;

    private int groupId;

    private final String name;
    private final LocalDate birthDate;
    private float salary;
    private boolean hasBonus;

    public Worker(int id, int groupId, String name, LocalDate birthDate, boolean hasBonus) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.birthDate = birthDate;
        this.hasBonus = hasBonus;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public boolean hasScholarship() {
        return hasBonus;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", salary=" + salary +
                ", hasBonus=" + hasBonus +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }
}