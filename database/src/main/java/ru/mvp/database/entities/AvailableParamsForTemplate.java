package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "available_params_for_template", schema = "public", catalog = "eslbase")
public class AvailableParamsForTemplate {
    private int id;
    private Integer templateId;
    private int paramId;
    private Templates templatesByTemplateId;
    private DirectoryParams directoryParamsByParamId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "template_id")
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    @Basic
    @Column(name = "param_id")
    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableParamsForTemplate that = (AvailableParamsForTemplate) o;
        return id == that.id &&
                paramId == that.paramId &&
                Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateId, paramId);
    }

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    public Templates getTemplatesByTemplateId() {
        return templatesByTemplateId;
    }

    public void setTemplatesByTemplateId(Templates templatesByTemplateId) {
        this.templatesByTemplateId = templatesByTemplateId;
    }

    @ManyToOne
    @JoinColumn(name = "param_id", referencedColumnName = "id", nullable = false)
    public DirectoryParams getDirectoryParamsByParamId() {
        return directoryParamsByParamId;
    }

    public void setDirectoryParamsByParamId(DirectoryParams directoryParamsByParamId) {
        this.directoryParamsByParamId = directoryParamsByParamId;
    }
}
