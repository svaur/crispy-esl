package ru.mvp.rsreu.db.entity;

import javax.persistence.*;

/**
 * Created by Art on 30.09.2018.
 */
@Entity
@Table(name = "MERCHANDISE")
public class Merchandise {

    @Id
    @Column(name = "ARTICLE_NUMBER", length = 64)
    private String ArticleNumber;

    @Column(name = "NAME", length = 64)
    private String name;

    @Column(name = "QUANTITY")
    private int quantity;

    public Merchandise() {
    }

    public String getArticleNumber() {
        return ArticleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        ArticleNumber = articleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int result = ArticleNumber != null ? ArticleNumber.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Merchandise that = (Merchandise) o;

        if (quantity != that.quantity) return false;
        if (ArticleNumber != null ? !ArticleNumber.equals(that.ArticleNumber) : that.ArticleNumber != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }
}
