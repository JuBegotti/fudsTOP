package BD;

/**
 * @author ju
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        System.out.println("Conectando ao Banco de Dados ");  
        try{  
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = (Connection) DriverManager.getConnection(URL, USUARIO, SENHA); 
            statement = (Statement) connection.createStatement();
        }
        catch (SQLException e){  
            throw new RuntimeException(e);  
        }
    }
    
    
    // USUARIO LOGIN E REGISTRO
    
    public static void registraUsuario(String nome, String email, String senha, String endereco, String telefone, String cpf) throws SQLException{
        String query = "INSERT INTO epBD.usuario VALUES (NULL,'"+
                nome+"', '"+email+"', '"+senha+"', '"+endereco+"', '"+telefone+"', '"+cpf+"')";
        statement.executeUpdate(query);
    } 
    
    public static boolean existeUsuario(String email) throws SQLException{
        boolean resp = false;
        String query = "SELECT * FROM epBD.usuario WHERE usuario_email = '"+email+"'";
        String emaill = null;
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            email = resultSet.getString("email");
            System.out.println(emaill);
        }
        if(emaill!=null) resp = true;
        return resp;
    }
    
    public static boolean verificaUsuario(String email, String senha) throws SQLException{
        String query = "SELECT * FROM epBD.usuario WHERE usuario_email = '"+email+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            if(senha.equals(resultSet.getString("usuario_senha")))
                return true;
        }
        return false;
    }
    
    
        // CATEGORIA
    
    public static void registrarCategoria(String nome) throws SQLException{
        if(!existeCategoria(nome)){
            String query = "INSERT INTO epBD.categoria VALUES (NULL,'"+nome+"');";
    	statement.executeUpdate(query);
        }
    }
    
    public static void listarCategorias() throws SQLException {
        String query = "SELECT * FROM epBD.categoria";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next())
            System.out.println("id: "+resultSet.getString("categoria_id")+" nome: "+resultSet.getString("categoria_nome"));
    }
    
    public static boolean existeCategoria(String nome) throws SQLException{
        String query = "SELECT * FROM epBD.categoria WHERE categoria_nome = '"+nome+"'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next())
            if(nome.equals(resultSet.getString("categoria_nome"))) return true;
        return false;
    }
    
    
        // REGIAO
    
    
    
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
    
        
    
    public static void listarRegiao() throws SQLException {
        String query = "SELECT * FROM epBD.regiao";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next())
            System.out.println("id: "+resultSet.getString("regiao_id")+" nome: "+resultSet.getString("regiao_nome"));
    }
}
