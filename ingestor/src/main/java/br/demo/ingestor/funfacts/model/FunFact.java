package br.demo.ingestor.funfacts.model;

import javax.persistence.*;

@Entity
@Table(name = "fun_fact")
public class FunFact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fact;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunFact funFact)) return false;

        if (!id.equals(funFact.id)) return false;
        return this.fact.equals(funFact.fact);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + fact.hashCode();
        return result;
    }

}
