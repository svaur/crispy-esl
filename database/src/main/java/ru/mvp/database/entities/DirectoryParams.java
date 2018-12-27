package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "directory_params", schema = "public", catalog = "eslbase")
public class DirectoryParams {
    private int id;
    private String name;
    private int typeParam;
    private Collection<AvailableParamsForTemplate> availableParamsForTemplatesById;
    private Collection<ItemParams> itemParamsById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        if (id != that.id) return false;
        if (typeParam != that.typeParam) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + typeParam;
        return result;
    }

    @OneToMany(mappedBy = "directoryParamsByParamId")
    public Collection<AvailableParamsForTemplate> getAvailableParamsForTemplatesById() {
        return availableParamsForTemplatesById;
    }

    public void setAvailableParamsForTemplatesById(Collection<AvailableParamsForTemplate> availableParamsForTemplatesById) {
        this.availableParamsForTemplatesById = availableParamsForTemplatesById;
    }

    @OneToMany(mappedBy = "directoryParamsByParamId")
    public Collection<ItemParams> getItemParamsById() {
        return itemParamsById;
    }

    public void setItemParamsById(Collection<ItemParams> itemParamsById) {
        this.itemParamsById = itemParamsById;
    }
}
