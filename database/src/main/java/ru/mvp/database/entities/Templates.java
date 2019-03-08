package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Templates {
    private int id;
    private String name;
    private String template;
    private Collection<AvailableParamsForTemplate> availableParamsForTemplatesById;
    private Collection<ItemParamsGroup> itemParamsGroupsById;

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
    @Column(name = "template")
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Templates templates = (Templates) o;
        return id == templates.id &&
                Objects.equals(name, templates.name) &&
                Objects.equals(template, templates.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, template);
    }

    @OneToMany(mappedBy = "templatesByTemplateId")
    public Collection<AvailableParamsForTemplate> getAvailableParamsForTemplatesById() {
        return availableParamsForTemplatesById;
    }

    public void setAvailableParamsForTemplatesById(Collection<AvailableParamsForTemplate> availableParamsForTemplatesById) {
        this.availableParamsForTemplatesById = availableParamsForTemplatesById;
    }

    @OneToMany(mappedBy = "templatesByTemplateId")
    public Collection<ItemParamsGroup> getItemParamsGroupsById() {
        return itemParamsGroupsById;
    }

    public void setItemParamsGroupsById(Collection<ItemParamsGroup> itemParamsGroupsById) {
        this.itemParamsGroupsById = itemParamsGroupsById;
    }
}
