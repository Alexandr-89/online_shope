package by.overone.online_store1.dao;

import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dto.AddProductDTO;
import by.overone.online_store1.model.Product;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    AddProductDTO addProduct(AddProductDTO productRegistretionDTO) throws SQLException, ConnectionFullPoolException, ConnectionException;
    Product getProductById(long id) throws DAOException, UserDAONotFoundException;
    List<Product> getProductsByStatus(Status status) throws DAOException;
}
