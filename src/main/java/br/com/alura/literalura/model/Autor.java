package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nome;
    private int anoNasc;
    private int anoFalec;

    public Autor(){}

    public Autor(DadosAutor dados) {
        this.nome = dados.nome();
        this.anoNasc = dados.anoNasc();
        this.anoFalec = dados.anoFalec();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(int anoNasc) {
        this.anoNasc = anoNasc;
    }

    public int getAnoFalec() {
        return anoFalec;
    }

    public void setAnoFalec(int anoFalec) {
        this.anoFalec = anoFalec;
    }

    @Override
    public String toString() {
        return "\n-------------------------Autor-------------------------\n" +
                "Nome: " + nome + "\n" +
                "Ano Nascimento: " + anoNasc + "\n" +
                "Ano Falecimento: " + anoFalec + "\n" +
                "\n-------------------------------------------------------\n";
    }
}
