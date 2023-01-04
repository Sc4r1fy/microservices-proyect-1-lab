package academy.digitallab.store.product.ProductTests;

import academy.digitallab.store.product.models.Category;
import academy.digitallab.store.product.models.Product;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.service.ProductService;
import academy.digitallab.store.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductMockServiceTests {

    @Mock
    private ProductRepository productRepository;


    private ProductService productService;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);

        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category( Category.builder().id(1L).build())
                .price( Double.parseDouble("12.5"))
                .stock(5)
                .build();

        Mockito.when( productRepository.findById(1L)).thenReturn( Optional.of(computer) );
        Mockito.when( productRepository.save(computer)).thenReturn(computer);

    }


    @Test
    public void findProductByIdTest(){

        Product found = productService.getProduct(1L);
        Assertions.assertEquals(found.getName(),"computer");

    }


    @Test
    public void stockUpdateTest(){

        Product updatedProduct = productService.updateStock(1L, 10);
        Assertions.assertEquals( updatedProduct.getStock() , 10);
    }



}
