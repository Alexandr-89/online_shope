package by.overone.online_store1.service;

import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dto.*;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    boolean addProduct(AddProductDTO product) throws ConnectionFullPoolException, SQLException, ConnectionException;
    ProductDTO getProductById(long id) throws ServiceException, ServiceNotFounException;
    List<ProductAllDTO> getAllActiveProducts() throws ServiceException;
}
