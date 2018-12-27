package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Templates {
    private int id;
    private String name;
    private String template;
    private Collection<AvailableParamsForTemplate> availableParamsForTemplatesById;
    private Collection<ItemParamsGroup> itemParamsGroupsById;

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

        if (id != templates.id) return false;
        if (name != null ? !name.equals(templates.name) : templates.name != null) return false;
        if (template != null ? !template.equals(templates.template) : templates.template != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        return result;
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
