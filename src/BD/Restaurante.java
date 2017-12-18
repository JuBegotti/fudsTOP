/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.awt.List;

/**
 *
 * @author ju
 */
public class Restaurante {

    private final int id;
    private final String nome;
    private final String cnpj;
    private final String descricao;
    private final String email;
    private final String telefone;
    private final String endereco;
    private final String preco;
    private final String regiao;
    private final float avaliacao;
    private final List categoria;
    
    public Restaurante(int id, String nome, String cnpj, String descricao, String email, String telefone, 
            String endereco, String preco, String regiao, float avaliacao, List categorias){
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.preco = preco;
        this.regiao = regiao;
        this.avaliacao = avaliacao;
        this.categoria = categorias;
        
    }
    
    /**
     * @return the id
     */
    public int getId(){
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @return the preco
     */
    public String getPreco() {
        return preco;
    }

    /**
     * @return the regiao
     */
    public String getRegiao() {
        return regiao;
    }

    /**
     * @return the avaliacao
     */
    public float getAvaliacao() {
        return avaliacao;
    }

    /**
     * @return the categoria
     */
    public List getCategoria() {
        return categoria;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }
}
