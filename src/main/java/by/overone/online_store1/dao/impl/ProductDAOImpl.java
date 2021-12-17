package by.overone.online_store1.dao.impl;

import by.overone.online_store1.dao.ProductDAO;
import by.overone.online_store1.dao.connectionPool.ConnectionPool;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dto.AddProductDTO;
import by.overone.online_store1.model.Product;
import by.overone.online_store1.model.Role;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.util.constant.ConstantProduct;
import by.overone.online_store1.util.constant.ConstantUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProductDAOImpl implements ProductDAO {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    String dbUrl = resourceBundle.getString("dbUrl");
    String dbUser = resourceBundle.getString("dbUser");
    String dbPassword = resourceBundle.getString("dbPassword");


    ConnectionPool connectionPool = new ConnectionPool(dbUrl, dbUser, dbPassword, 5);
    Connection connection = null;


    private final static String ADD_PRODUCT_QUERY = "INSERT INTO products VALUE(0,?,?,?,?,?)";
    private final static String SELECT_COUNT = "SELECT product_count FROM products WHERE product_id=?";
    private final static String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE product_id=?";
    private final static String GET_PRODUCT_BY_STATUS_QUERY = "SELECT * FROM products WHERE product_status=?";


    @Override
    public AddProductDTO addProduct(AddProductDTO product) {
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setLong(3, product.getPrice());
            preparedStatement.setLong(4, product.getCount());
            preparedStatement.setString(5, Status.ACTIVE.toString());
            preparedStatement.executeUpdate();
        } catch (ConnectionFullPoolException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return product;
    }


    @Override
    public Product getProductById(long id) throws DAOException, UserDAONotFoundException {
        Product product;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            product = new Product();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(ConstantProduct.ID));
                product.setName(resultSet.getString(ConstantProduct.NAME));
                product.setDiscription(resultSet.getString(ConstantProduct.DESCRIPTION));
                product.setPrice(resultSet.getLong(ConstantProduct.PRICE));
                product.setCount(resultSet.getLong(ConstantProduct.COUNT));
                product.setStatus(Status.valueOf(resultSet.getString(ConstantProduct.STATUS).toUpperCase(Locale.ROOT)));
            } else {
                throw new UserDAONotFoundException("net product");
            }
        }catch (SQLException | ConnectionFullPoolException | ConnectionException e){
            throw new DAOException("not connection");
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return product;
    }

    @Override
    public List<Product> getProductsByStatus(Status status) throws DAOException {
        List<Product> products;
        try {
            try {
                connection = connectionPool.getConnection();
            } catch (ConnectionFullPoolException e) {
                e.printStackTrace();
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_STATUS_QUERY);
            preparedStatement.setString(1,status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            products = new ArrayList<>();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getLong(ConstantProduct.ID));
                product.setName(resultSet.getString(ConstantProduct.NAME));
                product.setDiscription(resultSet.getString(ConstantProduct.DESCRIPTION));
                product.setPrice(resultSet.getLong(ConstantProduct.PRICE));
                product.setCount(resultSet.getLong(ConstantProduct.COUNT));
                product.setStatus(Status.valueOf(resultSet.getString(ConstantProduct.STATUS).toUpperCase(Locale.ROOT)));
                products.add(product);
            }
        }catch (SQLException e){
            throw new DAOException("Not connection");
        }finally {
            if (connection!=null){
                try {
                    connectionPool.returnConnection(connection);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return products;
    }
}
