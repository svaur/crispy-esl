package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "available_params_for_template", schema = "public", catalog = "eslbase")
public class AvailableParamsForTemplate {
    private int id;
    private Templates templatesByTemplateId;
    private DirectoryParams directoryParamsByParamId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableParamsForTemplate that = (AvailableParamsForTemplate) o;
        return id == that.id &&
                Objects.equals(templatesByTemplateId, that.templatesByTemplateId) &&
                Objects.equals(directoryParamsByParamId, that.directoryParamsByParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templatesByTemplateId, directoryParamsByParamId);
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
