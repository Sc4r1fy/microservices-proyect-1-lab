package academy.digitallab.store.product.service;

import academy.digitallab.store.product.models.Category;
import academy.digitallab.store.product.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> listAllProducts();

    public Product getProduct(Long id);

    public Product createProduct(Product product);

    public Product deleteProduct(Long id);

    public List<Product> findByCategory(Category category);

    public Product updateStock(Long id , Integer quantity);

    public Product updateProduct(Product product);

}
