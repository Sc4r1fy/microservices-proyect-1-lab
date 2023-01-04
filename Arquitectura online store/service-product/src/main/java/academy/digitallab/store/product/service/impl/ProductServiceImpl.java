package academy.digitallab.store.product.service.impl;

import academy.digitallab.store.product.models.Category;
import academy.digitallab.store.product.models.Product;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;


    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) {

        Product productDB = productRepository.findById(id).orElseThrow(null);
        if( productDB == null){
            return null;
        }

        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Integer quantity) {
        Product productDB = productRepository.findById(id).orElseThrow(null);
        if( productDB == null){
            return null;
        }

        productDB.setStock(quantity);
        return productRepository.save(productDB);
    }

    @Override
    public Product updateProduct(Product product) {

        Product productDB = productRepository.findById(product.getId()).orElseThrow(null);
        if( productDB == null){
            return null;
        }

        productDB.setName( product.getName() );
        product.setDescription( product.getDescription() );
        product.setCategory(product.getCategory() );
        product.setPrice( product.getPrice() );

        return productRepository.save(productDB);
    }


}
