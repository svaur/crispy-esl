package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "directory_params", schema = "public", catalog = "eslbase")
public class DirectoryParams {
    private int id;
    private String name;
    private int typeParam;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type_param")
    public int getTypeParam() {
        return typeParam;
    }

    public void setTypeParam(int typeParam) {
        this.typeParam = typeParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectoryParams that = (DirectoryParams) o;
        return id == that.id &&
                typeParam == that.typeParam &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeParam);
    }
}
