package by.overone.online_store1.service.impl;

import by.overone.online_store1.dao.ProductDAO;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionException;
import by.overone.online_store1.dao.connectionPool.connectionException.ConnectionFullPoolException;
import by.overone.online_store1.dao.exception.DAOException;
import by.overone.online_store1.dao.exception.UserDAONotFoundException;
import by.overone.online_store1.dao.impl.ProductDAOImpl;
import by.overone.online_store1.dto.AddProductDTO;
import by.overone.online_store1.dto.ProductAllDTO;
import by.overone.online_store1.dto.ProductDTO;
import by.overone.online_store1.dto.UserDTO;
import by.overone.online_store1.model.Product;
import by.overone.online_store1.model.Status;
import by.overone.online_store1.model.User;
import by.overone.online_store1.service.ProductService;
import by.overone.online_store1.service.exception.ServiceException;
import by.overone.online_store1.service.exception.ServiceNotFounException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    public ProductDAO productDAO = new ProductDAOImpl();
    @Override
    public boolean addProduct(AddProductDTO product) throws ConnectionFullPoolException, SQLException, ConnectionException {
        productDAO.addProduct(product);
        return false;
    }

    @Override
    public ProductDTO getProductById(long id) throws ServiceException, ServiceNotFounException {
        ProductDTO productDTO = new ProductDTO();
        try {
            Product product = productDAO.getProductById(id);
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDiscription());
            productDTO.setPrice(product.getPrice());
            productDTO.setCount(product.getCount());
            productDTO.setStatus(product.getStatus());
        }catch (DAOException e) {
            throw new ServiceException("Not connection");
        } catch (UserDAONotFoundException ex) {
            throw new ServiceNotFounException("User with id "+id+" not found", ex);
        }
        return productDTO;
    }

    @Override
    public List<ProductAllDTO> getAllActiveProducts() throws ServiceException {
        List<ProductAllDTO> productDTOs;
        try {
            List<Product> products = productDAO.getProductsByStatus(Status.ACTIVE);
            productDTOs = products.stream()
                    .map(product -> new ProductAllDTO( product.getName(),
                            product.getPrice(), product.getCount()))
                    .collect(Collectors.toList());

        }catch (DAOException e){
            throw new ServiceException("Not connection");
        }
        return productDTOs;
    }
}
