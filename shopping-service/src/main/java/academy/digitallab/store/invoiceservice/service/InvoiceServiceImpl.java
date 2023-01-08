package academy.digitallab.store.invoiceservice.service;

import academy.digitallab.store.invoiceservice.client.CustomerClient;
import academy.digitallab.store.invoiceservice.client.ProductClient;
import academy.digitallab.store.invoiceservice.entities.Invoice;
import academy.digitallab.store.invoiceservice.entities.InvoiceItem;
import academy.digitallab.store.invoiceservice.models.Customer;
import academy.digitallab.store.invoiceservice.models.Product;
import academy.digitallab.store.invoiceservice.repositories.InvoiceItemRepository;
import academy.digitallab.store.invoiceservice.repositories.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemRepository invoiceItemRepository;

    // FEIGN CLIENT PART :

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;



    @Override
    public List<Invoice> findInvoiceAll() {
        return  invoiceRepository.findAll();
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");

        // FEIGN CLIENT PART :
        // Before persist the invoice we need to make sure that the product stock is updtaded.

        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * (-1));
        });



        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    // if we want to get an invoice from the DB , we need to make sure that the dynamic data is updated.
    // As you can see, a product stock usually is modified.
    // we need to update all the products of the invoice.

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice =  invoiceRepository.findById(id).orElse(null);
        if( invoice != null ){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);

            List<InvoiceItem> itemList = invoice.getItems().stream().map( invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(itemList);
        }
        return invoice;
    }
}