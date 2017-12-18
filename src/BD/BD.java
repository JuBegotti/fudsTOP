package BD;

/**
 * @author ju
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import ep_bd.UsuarioAtivo;
import java.awt.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BD {
    
    private static Connection connection = null;
    private static Statement statement = null;
	
    private final static String URL = "jdbc:mysql://localhost/epBD?useSSL=false";
    private final static String USUARIO = "root";
    private final static String SENHA = "54321";
    
    public BD(){
        getConnection();
    }
    
    private void getConnection(){ 
        try{  
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = (Connection) DriverManager.getConnection(URL, USUARIO, SENHA); 
            statement = (Statement) connection.createStatement();
            System.out.println("getConnection(): Conectado ao Banco de Dados");
        }
        catch (SQLException e){  
            throw new RuntimeException(e);  
        }
    }
    
    
    // USUARIO LOGIN E REGISTRO
    
    public static int pegaIdUsuario(String email) throws SQLException{
        String query = "SELECT * FROM epBD.usuario WHERE usuario_email = '"+email+"'";
        ResultSet resultSet = statement.executeQuery(query);
        int id = 0;
        if(resultSet.next())
            id = resultSet.getInt("usuario_id");
        return id;
    }
    
    public static void registraUsuario(String nome, String email, String senha, String endereco, String telefone, String cpf) throws SQLException{
        String query = "INSERT INTO epBD.usuario VALUES (NULL,'"+
                nome+"', '"+email+"', '"+senha+"', '"+endereco+"', '"+telefone+"', '"+cpf+"')";
        statement.executeUpdate(query);
        System.out.println("registraUsuario(): usuário registrado com sucesso");
    } 
    
    public static void registraCliente(String email) throws SQLException{
        int id = pegaIdUsuario(email);
        if(id!=0){
            String query1 = "INSERT INTO epBD.cliente VALUES (NULL,'"+id+"','0','0','0')";
            statement.executeUpdate(query1);
            System.out.println("registraCliente(): cliente registrado com sucesso");
        }
        else System.err.println("registraCliente(): usuario nao encontrado");
    }
    
    public static void registraComerciante(String email) throws SQLException{
        int id = pegaIdUsuario(email);
        if(id!=0){
            String query1 = "INSERT INTO epBD.comerciante VALUES (NULL,'"+id+"')";
            statement.executeUpdate(query1);
            System.out.println("registraComerciante(): comerciante registrado com sucesso!");
        }
        else System.err.println("registraComerciante(): usuario nao encontrado");
    }
    
    public static boolean existeUsuario(String email) throws SQLException{
        String query = "SELECT * FROM epBD.usuario WHERE usuario_email = '"+email+"';";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next())
            return true;
        return false;
    }
    
    public static boolean existeComerciante(int id) throws SQLException{
        String query = "SELECT * FROM epBD.comerciante WHERE usuario_usuario_id = '"+id+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next())
            return true;
        return false;
    }
    
    public static boolean existeCliente(int id) throws SQLException{
        String query = "SELECT * FROM epBD.cliente WHERE usuario_usuario_id = '"+id+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next())
            return true;
        return false;
    }
    
    public static boolean verificaUsuario(String email, String senha) throws SQLException{
        String query = "SELECT * FROM epBD.usuario WHERE usuario_email = '"+email+"'";
        ResultSet resultSet = statement.executeQuery(query);
        String nome, endereco, telefone, cpf, cartaoNum, cartaoCod, cartaoVal;
        int id;
        Usuario user = null;
        if(resultSet.next()){
            if(senha.equals(resultSet.getString("usuario_senha"))){
                id = resultSet.getInt("usuario_id");
                nome = resultSet.getString("usuario_nome");
                email = resultSet.getString("usuario_email");
                senha = resultSet.getString("usuario_senha");
                endereco = resultSet.getString("usuario_endereco");
                telefone = resultSet.getString("usuario_telefone");
                cpf = resultSet.getString("usuario_cpf");
                String query1 = "SELECT * FROM epBD.cliente WHERE usuario_usuario_id = '"+id+"'";
                ResultSet resultSet1 = statement.executeQuery(query1);
                if(resultSet1.next()){
                    cartaoNum = resultSet1.getString("cliente_cartao_num");
                    cartaoCod = resultSet1.getString("cliente_cartao_cod");
                    cartaoVal = resultSet1.getString("cliente_cartao_val");
                    user = new Usuario(id, nome, email, senha, endereco, telefone, cpf, cartaoNum, cartaoCod, cartaoVal);
                }
                else user = new Usuario(id, nome, email, senha, endereco, telefone, cpf,"","","");
                UsuarioAtivo.user = user;
                System.out.println("verificaUsuario(): usuario encontrado");
                return true;
            }
        }
        System.err.println("verificaUsuario(): usuario não encontrado");
        return false;
    }
    
    public static void atualizaUsuario(String nome, String endereco, String telefone, 
            String cpf, String email, String senha, String cartaoNum, String cartaoCod, String cartaoVal) throws SQLException{
        int id = pegaIdUsuario(email);
        if(id==0) {
            System.err.println("atualizaUsuario(): usuario nao encontrado");
            return;
        }
        if(existeCliente(id)){
            String query1 = "UPDATE epBD.cliente SET cliente_cartao_num = '"+cartaoNum+"', "
                    + "cliente_cartao_cod = '"+cartaoCod+"', cliente_cartao_val = '"+cartaoVal+"' WHERE usuario_usuario_id = '"+id+"'";
            statement.executeUpdate(query1);
        }
        String query2 = "UPDATE epBD.usuario SET usuario_nome = '"+nome+"', usuario_email = '"+email+"', "
                + "usuario_senha = '"+senha+"', usuario_endereco = '"+endereco+"', "
                + "usuario_telefone = '"+telefone+"', usuario_cpf = '"+cpf+"' WHERE usuario_id = '"+id+"'";
        statement.executeUpdate(query2);
        verificaUsuario(email,senha);
        System.out.println("atualizaUsuario(): usuario atualizado com sucesso");
    }
    
    
    
        // RESTAURANTES
    
    public static boolean registraRestaurante(String nome, String descricao, String cnpj, String email, String endereco, String telefone, int preco, int regiao) throws SQLException{
        if(checaRestaurante(nome)) {
            System.err.println("registraRestaurante(): restaurante ja existe");
            return false;
        }
        String query = "INSERT INTO epBD.estabelecimento VALUES (NULL,'"+
                1+"', '"+UsuarioAtivo.user.getId()+"', '"+nome+"', '"+descricao+"', '"+cnpj+"', '"+email+
                "', '"+telefone+"', '"+endereco+"', '"+preco+"', '"+regiao+"', '0')";
        statement.executeUpdate(query);
        System.out.println("registraRestaurante(): restaurante registrado com sucesso");
        return true;
    } 
    
    public static boolean deletaRestaurante(String nome) throws SQLException{
        if(!checaRestaurante(nome)) {
            System.err.println("deletaRestaurante(): restaurante não encontrado");
            return false;
        }
        String query = "DELETE FROM epBD.estabelecimento WHERE 'estabelecimento_nome'='"+nome+"'";
        statement.executeUpdate(query);
        System.out.println("deletaRestaurante(): restaurante deletado com sucesso");
        return true;
    }
    
    public static boolean checaRestaurante(String nome) throws SQLException{
        String query = "SELECT * FROM epBD.estabelecimento WHERE estabelecimento_nome = '"+nome+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next())
            return true;
        return false;
    }
    
    public static Restaurante existeRestaurante(String nomee, int idd) throws SQLException{
        Restaurante res = null;
        String query;
        if(idd==0) query = "SELECT * FROM epBD.estabelecimento WHERE estabelecimento_nome = '"+nomee+"'";
        else query = "SELECT * FROM epBD.estabelecimento WHERE estabelecimento_id = '"+idd+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            int p = resultSet.getInt("preco_preco_id");
                int r = resultSet.getInt("regiao_regiao_id");
                int id = resultSet.getInt("estabelecimento_id");
                String nome = resultSet.getString("estabelecimento_nome");
                String cnpj = resultSet.getString("estabelecimento_cnpj");
                String descricao = resultSet.getString("estabelecimento_descricao");
                String email = resultSet.getString("estabelecimento_email"); 
                String telefone = resultSet.getString("estabelecimento_telefone");
                String endereco = resultSet.getString("estabelecimento_endereco");
                float avaliacao = resultSet.getFloat("estabelecimento_avaliacao");
                List categorias = listaCategoriasRestaurantes(resultSet.getInt("estabelecimento_id"));
                String preco = "";
                if(p==1) preco = "Baixo";
                if(p==2) preco = "Médio";
                if(p==3) preco = "Alto";
                String regiao = "";
                if(p==1) regiao = "Norte";
                if(p==2) regiao = "Sul";
                if(p==3) regiao = "Leste";
                if(p==4) regiao = "Oeste";
                if(p==5) regiao = "Centro";
                res = new Restaurante(id, nome,cnpj,descricao,email,telefone,endereco,preco,regiao, avaliacao,categorias);
        }
        return res;
    }
    
    public static ArrayList<Restaurante> listaRestaurantes() throws SQLException{
        ArrayList<Restaurante> lista = new ArrayList<Restaurante>();
        int i = 1;
        while(true){
            String query = "SELECT * FROM epBD.estabelecimento WHERE estabelecimento_id = '"+i+"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                Restaurante res = existeRestaurante("",i);
                if (res == null)
                    break;
                lista.add(res);
            }
            else break;
            i++;
        }
        return lista;
    }
    
    public static ArrayList<Restaurante> listaCertosRestaurantes(String categoria, int id) throws SQLException{
        ArrayList<Restaurante> lista = new ArrayList<Restaurante>();
        List nomes = new List();
        int i = 1;
        if(categoria!=null){
            String query = "SELECT * FROM epBD.estabelecimento WHERE "+categoria+" = '"+id+"'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
                nomes.add(resultSet.getString("estabelecimento_nome"));
        }
        else{
            String query1 = "SELECT * FROM epBD.estabelecimento IN (SELECT * FROM epBD.categoria WHERE categoria_id = '"+id+"')";
            ResultSet resultSet1 = statement.executeQuery(query1);
            while(resultSet1.next())
                lista.add(existeRestaurante("",resultSet1.getInt("estabelecimento_id")));
        }
        for(int ii=0; ii<nomes.getItemCount(); ii++)
            lista.add(existeRestaurante(nomes.getItem(ii),0));
        return lista;
    }
    
        // CATEGORIA
    
    public static void registrarCategoria(String nome) throws SQLException{
        if(!existeCategoria(nome)){
            String query = "INSERT INTO epBD.categoria VALUES (NULL,'"+nome+"');";
            statement.executeUpdate(query);
        }
    }
    
    public static List listaCategorias() throws SQLException{
        String query1 = "SELECT * FROM epBD.categoria";
        ResultSet resultSet1 = statement.executeQuery(query1);
        List categorias = new List();
        while(resultSet1.next())
            categorias.add(resultSet1.getString("categoria_nome"));
        return categorias;
    }
    
    public static List listaCategoriasRestaurantes(int id) throws SQLException{
        List lista = new List();
        String query2 = "SELECT * FROM epBD.estabelecimento_has_categoria WHERE estabelecimento_idestabelecimento = '"+id+"'";
        List categorias = listaCategorias();
        ResultSet resultSet2 = statement.executeQuery(query2);
        while(resultSet2.next())
            lista.add(categorias.getItem(resultSet2.getInt("categoria_idcategoria")-1));
        return lista;
    }
    
    public static boolean existeCategoria(String nome) throws SQLException{
        String query = "SELECT * FROM epBD.categoria WHERE categoria_nome = '"+nome+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next())
            if(nome.equals(resultSet.getString("categoria_nome"))) 
                return true;
        return false;
    }
    
    
        // REGIAO
    
    public static List listarRegiao() throws SQLException {
        List lista = new List();
        String query = "SELECT * FROM epBD.regiao";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next())
            lista.add(resultSet.getString("regiao_nome"));
        return lista;
    }
    
    
        // PRECO
    
    public static List listarPreco() throws SQLException {
        List lista = new List();
        String query = "SELECT * FROM epBD.preco";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next())
            lista.add(resultSet.getString("preco_nome"));
        return lista;
    }
    
        // AVALIACOES
    
    public static void atualizaAvaliacoes(int id) throws SQLException{
        String queryAux = "SELECT AVG(avaliacao_nota) as nota FROM epBD.avaliacao WHERE estabelecimento_estabelecimento_id = '"+id+"'";
        ResultSet resultSet = statement.executeQuery(queryAux);
        if(resultSet.next()){
            float avaliacao = resultSet.getFloat("nota");
            String query = "UPDATE epBD.estabelecimento SET estabelecimento_avaliacao = '"+avaliacao+"' WHERE estabelecimento_id = '"+id+"'";
            statement.executeUpdate(query);
        }
    }
    
        
    
}
