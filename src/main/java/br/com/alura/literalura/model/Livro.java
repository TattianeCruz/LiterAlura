package br.com.alura.literalura.model;


import jakarta.persistence.*;


@Entity(name="livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;
    private String language;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private int download_count;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.title();
        this.autor = new Autor(dadosLivro.dadosAutorList().get(0));
        this.language = dadosLivro.linguagemList().get(0);
        this.download_count = dadosLivro.downloadCount();

    }

    public Livro(String titulo, Autor autor, String language, int download_count) {
        this.titulo = titulo;
        this.autor = autor;
        this.language = language;
        this.download_count = download_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return "\n-------------------------Livro-------------------------\n" +
               "Titulo: " + titulo + "\n" +
               "Autor: " + autor.getNome() + "\n" +
               "Idioma: " + language + "\n" +
               "Número de Downloads: " + download_count + '\n' +
               "\n-------------------------------------------------------\n";
    }
}
