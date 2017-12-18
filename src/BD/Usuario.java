/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

/**
 *
 * @author ju
 */
public class Usuario {

    private final int id;
    private final String nome;
    private final String email;
    private final String senha;
    private final String endereco;
    private final String telefone;
    private final String cpf;
    private final String cartaoNum;
    private final String cartaoCod;
    private final String cartaoVal;
    
    public Usuario(int id, String nome, String email, String senha, String endereco, 
            String telefone, String cpf, String cartaoNum, String cartaoCod, String cartaoVal){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cpf = cpf;
        this.cartaoNum = cartaoNum;
        this.cartaoCod = cartaoCod;
        this.cartaoVal = cartaoVal;
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return the cartaoNum
     */
    public String getCartaoNum() {
        return cartaoNum;
    }

    /**
     * @return the cartaoCod
     */
    public String getCartaoCod() {
        return cartaoCod;
    }

    /**
     * @return the cartaoVal
     */
    public String getCartaoVal() {
        return cartaoVal;
    }
}
