package Storage.Beans;

public class Utente {


  public Utente() {
  }

  public Utente(String email, String nome, String password, String tipo) {
    this.email = email;
    this.nome = nome;
    this.password = password;
    this.tipo = tipo;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  private String email;
  private String nome;
  private String password;
  private String tipo;

  public static final String ADMIN = "Admin";
  public static final String UFFICIO_CARRIERE = "UfficioCarriere";
  public static final String AZIENDA = "azienda";
  public static final String STUDENTE = "studente";

}
